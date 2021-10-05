package RISCV32I

import chisel3._
import chisel3.util._

class Interconnect(MASTERS: Int, SLAVES: Int, DATA_BUS_SIZE: Int, ADDR_BUS_SIZE: Int) extends Module {
    val io = IO(new Bundle{
        // masters channel
        // write address channel
        val M_AXI_AWVALID = Vec(MASTERS, Input(Bool()))
        val M_AXI_AWREADY = Vec(MASTERS, Output(Bool()))
        val M_AXI_AWADDR  = Vec(MASTERS, Input(UInt(ADDR_BUS_SIZE.W)))
        val M_AXI_AWPROT  = Vec(MASTERS, Input(UInt(3.W)))

        // write data channel
        val M_AXI_WVALID  = Vec(MASTERS, Input(Bool()))
        val M_AXI_WREADY  = Vec(MASTERS, Output(Bool()))
        val M_AXI_WDATA   = Vec(MASTERS, Input(UInt(DATA_BUS_SIZE.W)))
        val M_AXI_WSTRB   = Vec(MASTERS, Input(UInt((DATA_BUS_SIZE/8).W)))

        // write response channel
        val M_AXI_BVALID  = Vec(MASTERS, Output(Bool()))
        val M_AXI_BREADY  = Vec(MASTERS, Input(Bool()))
        val M_AXI_BRESP   = Vec(MASTERS, Output(UInt(2.W)))

        // read address channel
        val M_AXI_ARVALID = Vec(MASTERS, Input(Bool()))
        val M_AXI_ARREADY = Vec(MASTERS, Output(Bool()))
        val M_AXI_ARADDR  = Vec(MASTERS, Input(UInt(ADDR_BUS_SIZE.W)))
        val M_AXI_ARPROT  = Vec(MASTERS, Input(UInt(3.W)))

        // read data channel
        val M_AXI_RVALID  = Vec(MASTERS, Output(Bool()))
        val M_AXI_RREADY  = Vec(MASTERS, Input(Bool()))
        val M_AXI_RDATA   = Vec(MASTERS, Output(UInt(DATA_BUS_SIZE.W)))
        val M_AXI_RRESP   = Vec(MASTERS, Output(UInt(2.W)))
        // end masters channel

        // slaves channel
        // write address channel
        val S_AXI_AWVALID = Vec(SLAVES, Output(Bool()))
        val S_AXI_AWREADY = Vec(SLAVES, Input(Bool()))
        val S_AXI_AWADDR  = Vec(SLAVES, Output(UInt(ADDR_BUS_SIZE.W)))
        val S_AXI_AWPROT  = Vec(SLAVES, Output(UInt(3.W)))

        // write data channel
        val S_AXI_WVALID  = Vec(SLAVES, Output(Bool()))
        val S_AXI_WREADY  = Vec(SLAVES, Input(Bool()))
        val S_AXI_WDATA   = Vec(SLAVES, Output(UInt(DATA_BUS_SIZE.W)))
        val S_AXI_WSTRB   = Vec(SLAVES, Output(UInt((DATA_BUS_SIZE/8).W)))

        // write response channel
        val S_AXI_BVALID  = Vec(SLAVES, Input(Bool()))
        val S_AXI_BREADY  = Vec(SLAVES, Output(Bool()))
        val S_AXI_BRESP   = Vec(SLAVES, Input(UInt(2.W)))

        // read address channel
        val S_AXI_ARVALID = Vec(SLAVES, Output(Bool()))
        val S_AXI_ARREADY = Vec(SLAVES, Input(Bool()))
        val S_AXI_ARADDR  = Vec(SLAVES, Output(UInt(ADDR_BUS_SIZE.W)))
        val S_AXI_ARPROT  = Vec(SLAVES, Output(UInt(3.W)))

        // read data channel
        val S_AXI_RVALID  = Vec(SLAVES, Input(Bool()))
        val S_AXI_RREADY  = Vec(SLAVES, Output(Bool()))
        val S_AXI_RDATA   = Vec(SLAVES, Input(UInt(DATA_BUS_SIZE.W)))
        val S_AXI_RRESP   = Vec(SLAVES, Input(UInt(2.W)))
        // end slaves channel
    })
    val arbuiter_rd     = RegInit(1.U(log2Ceil(MASTERS).W))
    val arbuiter_wr     = RegInit(0.U(log2Ceil(MASTERS).W))
    val units_stat_wr   = Vec(SLAVES, Wire(Bool()))
    val units_stat_rd   = Vec(SLAVES, Wire(Bool()))

    for(i <- 0 until SLAVES) {
        // read address channel
        io.M_AXI_ARREADY(arbuiter_rd + i.U) := io.S_AXI_ARREADY(i)
        io.S_AXI_ARVALID(i)                 := io.M_AXI_ARVALID(arbuiter_rd + i.U)
        io.S_AXI_ARADDR(i)                  := io.M_AXI_ARADDR(arbuiter_rd + i.U)
        io.S_AXI_ARPROT(i)                  := io.M_AXI_ARPROT(arbuiter_rd + i.U)

        // read data channel
        io.S_AXI_RREADY(i)                  := io.M_AXI_RREADY(arbuiter_rd + i.U)
        io.M_AXI_RVALID(arbuiter_rd + i.U)  := io.S_AXI_RVALID(i)
        io.M_AXI_RDATA(arbuiter_rd + i.U)   := io.S_AXI_RDATA(i)
        io.M_AXI_RRESP(arbuiter_rd + i.U)   := io.S_AXI_RRESP(i)

        // write address channel
        io.M_AXI_AWREADY(arbuiter_wr + i.U) := io.S_AXI_AWREADY(i)
        io.S_AXI_AWVALID(i)                 := io.M_AXI_AWVALID(arbuiter_wr + i.U)
        io.S_AXI_AWADDR(i)                  := io.M_AXI_AWADDR(arbuiter_wr + i.U)
        io.S_AXI_AWPROT(i)                  := io.M_AXI_AWPROT(arbuiter_wr + i.U)

        // write data channel
        io.M_AXI_WREADY(arbuiter_wr + i.U)  := io.S_AXI_WREADY(i)
        io.S_AXI_WVALID(i)                  := io.M_AXI_WVALID(arbuiter_wr + i.U)
        io.S_AXI_WDATA(i)                   := io.M_AXI_WDATA(arbuiter_wr + i.U)
        io.S_AXI_WSTRB(i)                   := io.M_AXI_WSTRB(arbuiter_wr + i.U)

        // write response channel
        io.S_AXI_BREADY(i)                  := io.M_AXI_BREADY(arbuiter_wr + i.U)
        io.M_AXI_BVALID(arbuiter_wr + i.U)  := io.S_AXI_BVALID(i)
        io.M_AXI_BRESP(arbuiter_wr + i.U)   := io.S_AXI_BRESP(i)
    }

    for(i <- 0 until SLAVES) {
        units_stat_rd(i)    := ~((io.S_AXI_ARREADY(i) ^ io.M_AXI_ARVALID(arbuiter_rd + i.U)) | (io.M_AXI_RREADY(arbuiter_rd + i.U) ^ io.S_AXI_RVALID(i)))
        units_stat_wr(i)    := ~((io.S_AXI_BVALID(i) ^ io.M_AXI_BREADY(arbuiter_wr + i.U)) | (io.S_AXI_WREADY(i) ^ io.M_AXI_WVALID(arbuiter_wr + i.U)) | (io.M_AXI_AWVALID(arbuiter_wr + i.U) ^ io.S_AXI_AWREADY(i)))
    }

    when(units_stat_rd.asUInt().andR()) {
        arbuiter_rd     := arbuiter_rd + 1.U
    }
    when(units_stat_wr.asUInt().andR()) {
        arbuiter_wr     := arbuiter_wr + 1.U
    }
}