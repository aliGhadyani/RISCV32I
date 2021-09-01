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