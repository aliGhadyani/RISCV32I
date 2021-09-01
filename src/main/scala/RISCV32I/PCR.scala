package RISCV32I

import chisel3._
import chisel3.util._

class PCR extends Module {
    val io = IO(new Bundle {
        val in_pause= Input(Bool())
        val in_npc  = Input(UInt(32.W))
        val out_pc  = Output(UInt(32.W))
    })
    val pc = RegInit(0.U(32.W))

    io.out_pc   := pc

    when(io.in_pause) {
        pc  := pc
    } .otherwise {
        pc  := io.in_npc
    }
}