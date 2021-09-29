package RISCV32I

import chisel3._
import chisel3.util._
import scala.annotation.switch

class AXIL_MASTER extends Module {
    val io = IO(new Bundle{
        // write address channel
        val M_AXI_AWVALID = Output(Bool())
        val M_AXI_AWREADY = Input(Bool())
        val M_AXI_AWADDR  = Output(UInt(32.W))
        val M_AXI_AWPROT  = Output(UInt(3.W))

        // write data channel
        val M_AXI_WVALID  = Output(Bool())
        val M_AXI_WREADY  = Input(Bool())
        val M_AXI_WDATA   = Output(UInt(32.W))
        val M_AXI_WSTRB   = Output(UInt(4.W))

        // write response channel
        val M_AXI_BVALID  = Input(Bool())
        val M_AXI_BREADY  = Output(Bool())
        val M_AXI_BRESP   = Input(UInt(2.W))

        // read address channel
        val M_AXI_ARVALID = Output(Bool())
        val M_AXI_ARREADY = Input(Bool())
        val M_AXI_ARADDR  = Output(UInt(32.W))
        val M_AXI_ARPROT  = Output(UInt(3.W))

        // read data channel
        val M_AXI_RVALID  = Input(Bool())
        val M_AXI_RREADY  = Output(Bool())
        val M_AXI_RDATA   = Input(UInt(32.W))
        val M_AXI_RRESP   = Input(UInt(2.W))

        // unit ports
        val in_rd_en    = Input(Bool())
        val in_wr_en    = Input(Bool())
        val in_strb     = Input(UInt(4.W))
        val in_adr      = Input(UInt(32.W))
        val in_data     = Input(UInt(32.W))
        val out_data    = Output(UInt(32.W))
        val out_pause   = Output(Bool())
    })

    val wr_state    = RegInit(0.U(2.W))
    val rd_state    = RegInit(0.U(2.W))

    io.M_AXI_WDATA    := io.in_data
    io.M_AXI_WSTRB    := io.in_strb
    io.M_AXI_AWADDR   := io.in_adr
    io.M_AXI_ARADDR   := io.in_adr
    io.out_data := io.M_AXI_RDATA
    io.M_AXI_ARPROT   := 0.U

    switch(wr_state) {
        is(0.U) {
            when(io.in_wr_en) {
                io.M_AXI_AWVALID  := true.B
                io.M_AXI_WVALID   := true.B
                io.M_AXI_BREADY   := true.B
                io.out_pause:= true.B
                wr_state    := 1.U
            } .otherwise {
                io.M_AXI_AWVALID  := false.B
                io.M_AXI_WVALID   := false.B
                io.M_AXI_BREADY   := false.B
                io.out_pause:= false.B
                wr_state    := 0.U
            }
        }
        is(1.U) {
            when(io.M_AXI_WREADY & io.M_AXI_AWREADY & io.M_AXI_BVALID) {
                io.out_pause:= false.B
                wr_state    := 0.U
            } .otherwise {
                io.out_pause:= true.B
                wr_state    := 1.U
            }
        }
        is(2.U) {
            wr_state    := 0.U
        }
        is(3.U) {
            wr_state    := 0.U
        }
    }

    switch(rd_state) {
        is(0.U) {
            when(io.in_rd_en) {
                io.M_AXI_ARVALID  := true.B
                io.M_AXI_RREADY   := true.B
                io.out_pause:= true.B
                rd_state    := 1.U
            } .otherwise {
                io.M_AXI_ARVALID  := false.B
                io.M_AXI_RREADY   := false.B
                io.out_pause:= false.B
                rd_state    := 0.U
            }
        }
        is(1.U) {
            when(io.M_AXI_RVALID & io.M_AXI_ARREADY) {
                io.out_pause:= false.B
                rd_state    := 0.U
            } .otherwise {
                io.out_pause:= true.B
                rd_state    := 0.U
            }
        }
        is(2.U) {
            rd_state    := 0.U
        }
        is(3.U) {
            rd_state    := 0.U
        }
    }
} 