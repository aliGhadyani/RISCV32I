package RISCV32I

import chisel3._
import chisel3.util._

class HazardUnit extends Module {
    val io = IO(new Bundle {
        val pc_src = Input(Bool())
        val IF_en = Output(Bool())
    })
}