package RISCV32I

import chisel3._
import chisel3.util._

class MWB extends Module {
    val io = IO(new Bundle {
        val in_pause    = Input(Bool())
        val in_WB       = Input(WBSig)
        val in_mem_out  = Input(UInt(32.W))
        val in_alu_res  = Input(UInt(32.W))
        val in_Rd       = Input(UInt(5.W))

        
        val out_WB      = Output(WBSig)
        val out_mem_out = Output(UInt(32.W))
        val out_alu_res = Output(UInt(32.W))
        val out_Rd      = Output(UInt(5.W))
    })
    val wb_sig  = RegInit(WBSig)
    val mem_out = RegInit(UInt(32.W))
    val alu_res = RegInit(UInt(32.W))
    val rd      = RegInit(UInt(32.W))

    io.out_WB       := wb_sig
    io.out_mem_out  := mem_out
    io.out_alu_res  := alu_res
    io.out_Rd       := rd
    
    when(io.in_pause) {
        wb_sig  := wb_sig
        mem_out := mem_out
        alu_res := alu_res
        rd      := rd
    } .otherwise {
        wb_sig  := io.in_WB
        mem_out := io.in_mem_out
        alu_res := io.in_alu_res
        rd      := io.in_Rd
    }
}