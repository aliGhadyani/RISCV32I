package RISCV32I

import chisel3._
import chisel3.util._

class IDEX(data_width: Int, adr_width: Int) extends Module {
    val io = IO(new Bundle {
        val in_pause= Input(Bool())

        val in_func = Input(new Func())
        val in_WB   = Input(new WBSig())
        val in_M    = Input(new MSig())
        val in_EX   = Input(new EXSig())
        val in_pc   = Input(UInt(data_width.W))
        val in_A    = Input(UInt(data_width.W))
        val in_B    = Input(UInt(data_width.W))
        val in_I    = Input(UInt(data_width.W))
        val in_Rd   = Input(UInt(adr_width.W))

        val out_func= Output(new Func())
        val out_WB  = Output(new WBSig())
        val out_M   = Output(new MSig())
        val out_EX  = Output(new EXSig())
        val out_pc  = Output(UInt(data_width.W))
        val out_A   = Output(UInt(data_width.W))
        val out_B   = Output(UInt(data_width.W))
        val out_I   = Output(UInt(data_width.W))
        val out_Rd  = Output(UInt(adr_width.W))
    })
    val func    = RegInit(new Func())
    val pc      = RegInit(0.U(data_width.W))
    val a       = RegInit(0.U(data_width.W))
    val b       = RegInit(0.U(data_width.W))
    val i       = RegInit(0.U(data_width.W))
    val rd      = RegInit(0.U(data_width.W))
    val m_sig   = RegInit(new MSig())
    val wb_sig  = RegInit(new WBSig())
    val ex_sig  = RegInit(new EXSig())

    
    io.out_func:= func
    io.out_WB  := wb_sig
    io.out_M   := m_sig
    io.out_EX  := ex_sig
    io.out_pc  := pc
    io.out_A   := a
    io.out_B   := b
    io.out_I   := i
    io.out_Rd  := rd

    when(io.in_pause) {
        // keeps curren values
    } .otherwise {
        func   := io.in_func
        pc     := io.in_pc
        a      := io.in_A 
        b      := io.in_B
        i      := io.in_I
        rd     := io.in_Rd
        m_sig  := io.in_M
        wb_sig := io.in_WB
        ex_sig := io.in_EX
    }

}