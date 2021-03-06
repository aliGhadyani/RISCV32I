package RISCV32I

import chisel3._
import chisel3.util._

class MWB extends Module {
    val io = IO(new Bundle {
        val in_pause    = Input(Bool())
        val in_WB       = Input(Bool())
        val in_data     = Input(UInt(32.W))
        val in_Rd       = Input(UInt(5.W))

        
        val out_WB      = Output(Bool())
        val out_data    = Output(UInt(32.W))
        val out_Rd      = Output(UInt(5.W))
    })
    val wb_sig  = RegInit(false.B)
    val data    = RegInit(0.U(32.W))
    val rd      = RegInit(0.U(5.W))

    io.out_WB       := wb_sig
    io.out_data     := data
    io.out_Rd       := rd
    
    when(io.in_pause) {
        wb_sig  := wb_sig
        data    := data
        rd      := rd
    } .otherwise {
        wb_sig  := io.in_WB
        data    := io.in_data
        rd      := io.in_Rd
    }
}