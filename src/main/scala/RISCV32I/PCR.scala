package RISCV32I

import chisel3._
import chisel3.util._

class PCR(data_width: Int) extends Module {
    val io = IO(new Bundle {
        val in_pause = Input(Bool())
        val in_npc = Input(UInt(data_width.W))
        val out_pc = Output(UInt(data_width.W))
    })
    val pc = RegInit(0.U(data_width.W))

    io.out_pc := pc

    when(io.in_pause) {
        pc := pc
    } .otherwise {
        pc := io.in_npc
    }
}