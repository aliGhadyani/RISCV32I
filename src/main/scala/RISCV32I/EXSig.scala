package RISCV32I

import chisel3._
import chisel3.util._

class EXSig extends Bundle {
    val slc_A   = UInt(2.W)
    val slc_B   = UInt(2.W)
    val alu_func= UInt(3.W)
    val alu_f4  = Bool()
}
