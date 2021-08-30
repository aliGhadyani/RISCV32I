package RISCV32I

import chisel3._
import chisel3.util._

object EXSig extends Bundle {
    val slc_A = UInt(2.W)
    val slc_B = UInt(2.W)
    val slc_dst = UInt(2.W)
}

object MSig extends Bundle {
    val wr_en = Bool()
    val slc_dst = UInt(2.W)
}

object WBSig extends Bundle {
    val wr_en = Bool()
}

object Ctrl extends Bundle {
    val jump    = Bool()
    val slc_imm = UInt(3.W)
    val f4      = UInt(2.W)
    val M       = MSig
    val EX      = EXSig
    val WB      = WBSig
}
        