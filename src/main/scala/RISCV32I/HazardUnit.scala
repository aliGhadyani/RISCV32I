package RISCV32I

import chisel3._
import chisel3.util._

class HazardUnit extends Module {
    val io = IO(new Bundle {
        val in_branch   = Input(Bool())
        val in_jump     = Input(Bool())
        val out_stall   = Output(Bool())
        val out_kill    = Output(Bool())
    })
    when(io.in_branch | io.in_jump) {
        io.out_kill := true.B
        io.out_stall:= true.B
    } .otherwise {
        io.out_kill := false.B
        io.out_stall:= false.B
    }
}