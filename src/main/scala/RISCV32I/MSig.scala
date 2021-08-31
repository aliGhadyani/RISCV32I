package RISCV32I

import chisel3._
import chisel3.util._

class MSig extends Bundle {
    val wr_en   = Bool()
    val slc_d   = UInt(2.W)
}
