package RISCV32I

import chisel3._
import chisel3.util._

class AXIL_SLAVE extends Module {
    val io = IO(new Bundle{
        // write address channel
        val AWVALID = Output(Bool())
        val AWREADY = Input(Bool())
        val AWADDR  = Output(UInt(32.W))
        val AWPROT  = Output(UInt(3.W))

        // write data channel
        val WVALID  = Output(Bool())
        val WREADY  = Input(Bool())
        val WDATA   = Output(UInt(32.W))
        val WSTRB   = Output(UInt(4.W))

        // write response channel
        val BVALID  = Input(Bool())
        val BREADY  = Output(Bool())
        val BRESP   = Input(UInt(2.W))

        // read address channel
        val ARVALID = Output(Bool())
        val ARREADY = Input(Bool())
        val ARADDR  = Output(UInt(32.W))
        val ARPROT  = Output(UInt(3.W))

        // read data channel
        val RVALID  = Input(Bool())
        val RREADY  = Output(Bool())
        val RDATA   = Input(UInt(32.W))
        val RRESP   = Input(UInt(2.W))

        val in_M_rd = Input(Bool())
        val in_M_wr = Input(Bool())
        val in_data = Input(UInt(32.W))
        val in_adr  = Input(UInt(32.W))
        val out_pause   = Output(Bool())
        val out_data    = Output(UInt(32.W))
    })

    io.out_data := io.RDATA
    io.WDATA    := io.in_data
    io.ARADDR   := io.in_adr
    io.AWADDR   := io.in_adr

    when(io.in_M_rd) {
        io.out_pause    := true.B
        io.ARVALID      := true.B
        io.AWVALID      := false.B
        io.RREADY       := true.B
        io.WVALID       := false.B
        io.BREADY       := false.B
        when(io.RVALID & io.ARREADY) {
            io.out_pause    := false.B
        }
    } .elsewhen(io.in_M_wr) {
        io.out_pause    := false.B
        io.ARVALID      := false.B
        io.AWVALID      := true.B
        io.RREADY       := false.B
        io.WVALID       := true.B
        io.BREADY       := true.B
        when(io.WREADY & io.AWREADY & io.BVALID) {
            io.out_pause    := false.B
        }
    } .otherwise {
        io.out_pause    := false.B
        io.ARVALID      := false.B
        io.AWVALID      := false.B
        io.RREADY       := false.B
        io.WVALID       := false.B
        io.BREADY       := false.B
    }
}