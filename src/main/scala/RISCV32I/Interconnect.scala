package RISCV32I

import chisel3._
import chisel3.util._

class Interconnect(MASTERS: Int, SLAVES: Int, DATA_BUS_SIZE: Int, ADDR_BUS_SIZE: Int) extends Module {
    val io = IO(new Bundle{
        // masters channel
        // write address channel
        val M_AXI_AWVALID = Vector(MASTERS, Input(Bool()))
        val M_AXI_AWREADY = Vector(MASTERS, Output(Bool()))
        val M_AXI_AWADDR  = Vector(MASTERS, Input(UInt(ADDR_BUS_SIZE.W)))
        val M_AXI_AWPROT  = Vector(MASTERS, Input(UInt(3.W)))

        // write data channel
        val M_AXI_WVALID  = Vector(MASTERS, Input(Bool()))
        val M_AXI_WREADY  = Vector(MASTERS, Output(Bool()))
        val M_AXI_WDATA   = Vector(MASTERS, Input(UInt(DATA_BUS_SIZE.W)))
        val M_AXI_WSTRB   = Vector(MASTERS, Input(UInt((DATA_BUS_SIZE/8).W)))

        // write response channel
        val M_AXI_BVALID  = Vector(MASTERS, Output(Bool()))
        val M_AXI_BREADY  = Vector(MASTERS, Input(Bool()))
        val M_AXI_BRESP   = Vector(MASTERS, Output(UInt(2.W)))

        // read address channel
        val M_AXI_ARVALID = Vector(MASTERS, Input(Bool()))
        val M_AXI_ARREADY = Vector(MASTERS, Output(Bool()))
        val M_AXI_ARADDR  = Vector(MASTERS, Input(UInt(ADDR_BUS_SIZE.W)))
        val M_AXI_ARPROT  = Vector(MASTERS, Input(UInt(3.W)))

        // read data channel
        val M_AXI_RVALID  = Vector(MASTERS, Output(Bool()))
        val M_AXI_RREADY  = Vector(MASTERS, Input(Bool()))
        val M_AXI_RDATA   = Vector(MASTERS, Output(UInt(DATA_BUS_SIZE.W)))
        val M_AXI_RRESP   = Vector(MASTERS, Output(UInt(2.W)))
        // end masters channel

        // slaves channel
        // write address channel
        val S_AXI_AWVALID = Vector(SLAVES, Output(Bool()))
        val S_AXI_AWREADY = Vector(SLAVES, Input(Bool()))
        val S_AXI_AWADDR  = Vector(SLAVES, Output(UInt(ADDR_BUS_SIZE.W)))
        val S_AXI_AWPROT  = Vector(SLAVES, Output(UInt(3.W)))

        // write data channel
        val S_AXI_WVALID  = Vector(SLAVES, Output(Bool()))
        val S_AXI_WREADY  = Vector(SLAVES, Input(Bool()))
        val S_AXI_WDATA   = Vector(SLAVES, Output(UInt(DATA_BUS_SIZE.W)))
        val S_AXI_WSTRB   = Vector(SLAVES, Output(UInt((DATA_BUS_SIZE/8).W)))

        // write response channel
        val S_AXI_BVALID  = Vector(SLAVES, Input(Bool()))
        val S_AXI_BREADY  = Vector(SLAVES, Output(Bool()))
        val S_AXI_BRESP   = Vector(SLAVES, Input(UInt(2.W)))

        // read address channel
        val S_AXI_ARVALID = Vector(SLAVES, Output(Bool()))
        val S_AXI_ARREADY = Vector(SLAVES, Input(Bool()))
        val S_AXI_ARADDR  = Vector(SLAVES, Output(UInt(ADDR_BUS_SIZE.W)))
        val S_AXI_ARPROT  = Vector(SLAVES, Output(UInt(3.W)))

        // read data channel
        val S_AXI_RVALID  = Vector(SLAVES, Input(Bool()))
        val S_AXI_RREADY  = Vector(SLAVES, Output(Bool()))
        val S_AXI_RDATA   = Vector(SLAVES, Input(UInt(DATA_BUS_SIZE.W)))
        val S_AXI_RRESP   = Vector(SLAVES, Input(UInt(2.W)))
        // end slaves channel
    })
    val arbuiter    = RegInit(0.U(log2Ceil(MASTERS)))
}