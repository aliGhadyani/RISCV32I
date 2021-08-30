package RISCV32I

import chisel3._
import chisel3.util._

class EXM extends Module {
    val io = IO(new Bundle {
        val in_pause    = Input(Bool())
        val in_func     = Input(UInt(3.W))
        val in_M        = Input(MSig)
        val in_WB       = Input(WBSig)
        val in_alu_res  = Input(UInt(32.W))
        val in_Rs2_val  = Input(UInt(32.W))
        val in_Rd       = Input(UInt(5.W))

        val out_func    = Output(UInt(3.W))
        val out_M       = Input(MSig)
        val out_WB      = Input(WBSig)
        val out_alu_res = Input(UInt(32.W))
        val out_Rs2_val = Input(UInt(32.W))
        val out_Rd      = Input(UInt(5.W))
    })

    val func    = RegInit(UInt(3.W))
    val m_sig   = RegInit(MSig)
    val wb_sig  = RegInit(WBSig)
    val alu_res = RegInit(UInt(32.W))
    val rs2_val = RegInit(UInt(32.W))
    val rd      = RegInit(UInt(32.W))

    io.out_func     := func
    io.out_M        := m_sig
    io.out_WB       := wb_sig
    io.out_alu_res  := alu_res
    io.out_Rs2_val  := rs2_val
    io.out_Rd       := rd

    when(io.in_pause) {
        func    := func
        m_sig   := m_sig
        wb_sig  := wb_sig
        alu_res := alu_res
        rs2_val := rs2_val
        rd      := rd
    } .otherwise {
        func    := io.in_func
        m_sig   := io.in_M
        wb_sig  := io.in_WB
        alu_res := io.in_alu_res
        rs2_val := io.in_Rs2_val
        rd      := io.in_Rd
    }
}