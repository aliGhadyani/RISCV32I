package RISCV32I

import chisel3._
import chisel3.util._

class Func extends Bundle {
    val func3 = UInt(3.W)
    val func7 = UInt(7.W)
}

class EXSig extends Bundle {
    val A_slc = UInt(2.W)
    val B_slc = UInt(2.W)
    val dst_slc = UInt(2.W)
}

class MSig extends Bundle {
    val wr_en = Bool()
}

class WBSig extends Bundle {
    val wr_en = Bool()
}