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
}

object WBSig extends Bundle {
    val wr_en = Bool()
    val d_slc = Bool()
}