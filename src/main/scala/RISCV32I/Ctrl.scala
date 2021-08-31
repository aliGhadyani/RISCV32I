package RISCV32I

import chisel3._
import chisel3.util._

class Ctrl extends Bundle {
    val b_enable    = Bool()
    val pc_rori     = Bool()
    val jump        = Bool()
    val slc_imm     = UInt(3.W)
    val sig_M       = new MSig()
    val sig_EX      = new EXSig()
    val sig_WB      = new WBSig()
}

object initSig {
    val m_sig = new MSig() 
    m_sig.wr_en     := false.B
    m_sig.slc_d     := 0.U

    val wb_sig = new WBSig()
    wb_sig.wr_en    := false.B

    val ex_sig = new EXSig()
    ex_sig.slc_A    := 0.U
    ex_sig.slc_B    := 0.U
    ex_sig.alu_func := 0.U
    ex_sig.alu_f4   := false.B

    val c   = new Ctrl()
    c.b_enable  := false.B
    c.jump      := false.B
    c.pc_rori   := false.B
    c.slc_imm   := 0.U
    c.sig_EX    := ex_sig
    c.sig_M     := m_sig
    c.sig_WB    := wb_sig

}
