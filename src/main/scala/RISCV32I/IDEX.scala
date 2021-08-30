package RISCV32I

import chisel3._
import chisel3.util._

class IDEX extends Module {
    val io = IO(new Bundle {
        val in_pause= Input(Bool())
        val in_f4   = Input(UInt(2.W))
        val in_func3= Input(UInt(3.W))
        val in_WB   = Input(WBSig)
        val in_M    = Input(MSig)
        val in_EX   = Input(EXSig)
        val in_pc   = Input(UInt(32.W))
        val in_A    = Input(UInt(32.W))
        val in_B    = Input(UInt(32.W))
        val in_I    = Input(UInt(32.W))
        val in_Rd   = Input(UInt(5.W))
        val in_Rs1  = Input(UInt(5.W))
        val in_Rs2  = Input(UInt(5.W))
        
        val out_f4      = Output(UInt(2.W))
        val out_func3   = Output(UInt(3.W))
        val out_WB      = Output(WBSig)
        val out_M       = Output(MSig)
        val out_EX      = Output(EXSig)
        val out_pc      = Output(UInt(32.W))
        val out_A       = Output(UInt(32.W))
        val out_B       = Output(UInt(32.W))
        val out_I       = Output(UInt(32.W))
        val out_Rd      = Output(UInt(5.W))
        val out_Rs1     = Output(UInt(5.W))
        val out_Rs2     = Output(UInt(5.W))
    })

    val f4      = RegInit(UInt(2.W))
    val func3   = RegInit(UInt(3.W))
    val pc      = RegInit(0.U(32.W))
    val a       = RegInit(0.U(32.W))
    val b       = RegInit(0.U(32.W))
    val i       = RegInit(0.U(32.W))
    val rd      = RegInit(0.U(5.W))
    val rs1     = RegInit(0.U(5.W))
    val rs2     = RegInit(0.U(5.W))
    val m_sig   = RegInit(MSig)
    val wb_sig  = RegInit(WBSig)
    val ex_sig  = RegInit(EXSig)
    
    io.out_f4   := f4
    io.out_func3:= func3
    io.out_WB   := wb_sig
    io.out_M    := m_sig
    io.out_EX   := ex_sig
    io.out_pc   := pc
    io.out_A    := a
    io.out_B    := b
    io.out_I    := i
    io.out_Rd   := rd
    io.out_Rs1  := rs1
    io.out_Rs2  := rs2

    when(io.in_pause) {
        f4      := f4
        func3   := func3
        pc      := pc
        a       := a
        b       := b
        i       := i
        rd      := rd
        rs1     := rs1
        rs2     := rs2
        m_sig   := m_sig
        wb_sig  := wb_sig
        ex_sig  := ex_sig
    } .otherwise {
        f4      := f4
        func3   := io.in_func3
        pc      := io.in_pc
        a       := io.in_A 
        b       := io.in_B
        i       := io.in_I
        rd      := io.in_Rd
        rs1     := io.in_Rs1
        rs2     := io.in_Rs2
        m_sig   := io.in_M
        wb_sig  := io.in_WB
        ex_sig  := io.in_EX
    }

}