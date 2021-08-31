package RISCV32I

import chisel3._
import chisel3.util._

class WBSig extends Bundle {
    val wr_en   = Bool()
}
