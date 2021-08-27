package RISCV32I

import chisel3._
import chisel3.util._

class DataPath extends Module {
    val io = IO(new Bundle{
        val start = Input(Bool())
    })
}