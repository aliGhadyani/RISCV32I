package RISCV32I

import chisel3._
import chisel3.util._

object EXSig extends Bundle {
    val A_slc = UInt(2.W)
    val B_slc = UInt(2.W)
    val dst_slc = UInt(2.W)
}

object MSig extends Bundle {
    val wr_en = Bool()
}

object WBSig extends Bundle {
    val wr_en = Bool()
}