package RISCV32I

import chisel3._
import chisel3.util._

class Cluster(CORES: Int, MEMORIES: Int, MEMORY_SIZE: Int) extends Module {
    val io  = IO(new Bundle{
    })
    val cores       = Array(CORES, Module(new Core()))
    val memories    = Array(MEMORIES, Module(new Memory(LENGTH = MEMORY_SIZE, WORD_WIDTH = 32, STRB_WIDTH = 4)))
    val masters     = Array(CORES, Module(new AXIL_MASTER()))
    val slaves      = Array(MEMORIES, Module(new AXIL_SLAVE()))
    val intercon    = Module(new Interconnect(MASTERS = CORES, SLAVES = MEMORIES, DATA_BUS_SIZE = 32, ADDR_BUS_SIZE = 32))

    for(i <- 0 until CORES) {
        cores(i).idx    := i
        // read address channel
        masters(i).io.M_AXI_ARREADY     := intercon.io.M_AXI_ARREADY(i)
        intercon.io.M_AXI_ARVALID(i)    := masters(i).io.M_AXI_ARVALID
        intercon.io.M_AXI_ARADDR(i)     := masters(i).io.M_AXI_ARADDR
        intercon.io.M_AXI_ARPROT(i)     := masters(i).io.M_AXI_ARPROT

        // read data channel
        intercon.io.M_AXI_RREADY(i)     := masters(i).io.M_AXI_RREADY
        masters(i).io.M_AXI_RVALID      := intercon.io.M_AXI_RVALID(i)
        masters(i).io.M_AXI_RDATA       := intercon.io.M_AXI_RDATA(i)
        masters(i).io.M_AXI_RRESP       := intercon.io.M_AXI_RRESP(i)

        // write address channel
        masters(i).io.M_AXI_AWREADY     := intercon.io.M_AXI_AWREADY(i)
        intercon.io.M_AXI_AWVALID(i)    := masters(i).io.M_AXI_AWVALID
        intercon.io.M_AXI_AWADDR(i)     := masters(i).io.M_AXI_AWADDR
        intercon.io.M_AXI_AWPROT(i)     := masters(i).io.M_AXI_AWPROT

        // write data channel
        masters(i).io.M_AXI_WREADY      := intercon.io.M_AXI_WREADY(i)
        intercon.io.M_AXI_WVALID(i)     := masters(i).io.M_AXI_WVALID
        intercon.io.M_AXI_WDATA(i)      := masters(i).io.M_AXI_WDATA
        intercon.io.M_AXI_WSTRB(i)      := masters(i).io.M_AXI_WSTRB
        
        // write response channel
        intercon.io.M_AXI_BREADY(i)     := masters(i).io.M_AXI_BREADY
        masters(i).io.M_AXI_BVALID      := intercon.io.M_AXI_BVALID(i)
        masters(i).io.M_AXI_BRESP       := intercon.io.M_AXI_BRESP(i)

        // unit channel
        masters(i).io.in_rd_en  := cores(i).io.out_rd_en
        masters(i).io.in_wr_en  := cores(i).io.out_wr_en
        masters(i).io.in_strb   := cores(i).io.out_strb
        masters(i).io.in_adr    := cores(i).io.out_adr
        masters(i).io.in_data   := cores(i).io.out_data
        cores(i).io.in_data     := masters(i).io.out_data
        cores(i).io.in_pause    := masters(i).io.out_pause
    }
}