package RISCV32I

import chisel3._
import chisel3.util._

class Cluster(CORES: Int, MEMORIES: Int, MEMORY_SIZE: Int) extends Module {
    val io  = IO(new Bundle{
    })
    val intercon    = Module(new Interconnect(MASTERS = CORES, SLAVES = MEMORIES, DATA_BUS_SIZE = 32, ADDR_BUS_SIZE = 32))
    val memories    = for(i <- 0 until MEMORIES) yield {
        val memory  = Module(new Memory(LENGTH = MEMORY_SIZE, WORD_WIDTH = 32, STRB_WIDTH = 4))
        val slave   = Module(new AXIL_SLAVE())
    }

    val cores = for(i <- 0 until CORES) yield {
        val core    = Module(new Core(IDX = i))
        val master  = Module(new AXIL_MASTER())
        // read address channel
        master.io.M_AXI_ARREADY         := intercon.io.M_AXI_ARREADY(i)
        intercon.io.M_AXI_ARVALID(i)    := master.io.M_AXI_ARVALID
        intercon.io.M_AXI_ARADDR(i)     := master.io.M_AXI_ARADDR
        intercon.io.M_AXI_ARPROT(i)     := master.io.M_AXI_ARPROT

        // read data channel
        intercon.io.M_AXI_RREADY(i)     := master.io.M_AXI_RREADY
        master.io.M_AXI_RVALID          := intercon.io.M_AXI_RVALID(i)
        master.io.M_AXI_RDATA           := intercon.io.M_AXI_RDATA(i)
        master.io.M_AXI_RRESP           := intercon.io.M_AXI_RRESP(i)

        // write address channel
        master.io.M_AXI_AWREADY         := intercon.io.M_AXI_AWREADY(i)
        intercon.io.M_AXI_AWVALID(i)    := master.io.M_AXI_AWVALID
        intercon.io.M_AXI_AWADDR(i)     := master.io.M_AXI_AWADDR
        intercon.io.M_AXI_AWPROT(i)     := master.io.M_AXI_AWPROT

        // write data channel
        master.io.M_AXI_WREADY          := intercon.io.M_AXI_WREADY(i)
        intercon.io.M_AXI_WVALID(i)     := master.io.M_AXI_WVALID
        intercon.io.M_AXI_WDATA(i)      := master.io.M_AXI_WDATA
        intercon.io.M_AXI_WSTRB(i)      := master.io.M_AXI_WSTRB
        
        // write response channel
        intercon.io.M_AXI_BREADY(i)     := master.io.M_AXI_BREADY
        master.io.M_AXI_BVALID          := intercon.io.M_AXI_BVALID(i)
        master.io.M_AXI_BRESP           := intercon.io.M_AXI_BRESP(i)

        // unit channel
        master.io.in_rd_en  := core.io.out_rd_en
        master.io.in_wr_en  := core.io.out_wr_en
        master.io.in_strb   := core.io.out_strb
        master.io.in_adr    := core.io.out_adr
        master.io.in_data   := core.io.out_data
        core.io.in_data     := master.io.out_data
        core.io.in_pause    := master.io.out_pause
    }
}