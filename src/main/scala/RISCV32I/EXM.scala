package RISCV32I

import chisel3._
import chisel3.util._

class EXM(data_width: Int, adr_width: Int) extends Module {
    val io = IO(new Bundle {
        val in_pause = Input(Bool())

        val in_M        = Input(new MSig())
        val in_WB       = Input(new WBSig())
        val in_alu_res  = Input(UInt(data_width.W))
        val in_rs2      = Input(UInt(data_width.W))
        val in_Rd       = Input(UInt(adr_width.W))
    })
}