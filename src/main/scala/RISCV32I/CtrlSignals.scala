package RISCV32I

import chisel3._
import chisel3.util._

class EXSig extends Bundle {
    val slc_A   = UInt(2.W)
    val slc_B   = UInt(2.W)
    val alu_func= UInt(3.W)
    val alu_f4  = Bool()
}

class MSig extends Bundle {
    val wr_en   = Bool()
    val slc_d   = UInt(2.W)
}

class WBSig extends Bundle {
    val wr_en   = Bool()
}

object initSig {
    val m_sig = new MSig() 
    m_sig.wr_en     := false.B
    m_sig.slc_dst   := 0.U

    val wb_sig = new WBSig()
    wb_sig.wr_en    := false.B

    val ex_sig = new EXSig()
    ex_sig.slc_A    := 0.U
    ex_sig.slc_B    := 0.U
    ex_sig.alu_func := 0.U
    ex_sig.alu_f4   := false.B
}

class Ctrl extends Bundle {
    val b_enable    = Bool()
    val pc_rori     = Bool()
    val jump        = Bool()
    val slc_imm     = UInt(3.W)
    val sig_M       = new MSig()
    val sig_EX      = new EXSig()
    val sig_WB      = new WBSig()
}
        