package RISCV32I

import chisel3._
import chisel3.util._

class  IFID extends Module {
    val io = IO(new Bundle {
        val in_pause= Input(Bool())
        val in_stall= Input(Bool())
        val in_kill = Input(Bool())
        val in_pc   = Input(UInt(32.W))
        val in_inst = Input(UInt(32.W))
        val in_nop  = Input(UInt(32.W))

        val out_pc  = Output(UInt(32.W))
        val out_inst= Output(UInt(32.W))
    })
    val pc  = RegInit(0.U(32.W))
    val inst= RegInit(io.in_nop)

    io.out_pc   := pc

    when(io.in_kill) {
        io.out_inst := io.in_nop
    } .otherwise {
        io.out_inst := inst
    }

    when(io.in_pause) {
        pc  := pc
        inst:= inst
    } .otherwise {
        when(io.in_stall) {
            inst    := io.in_nop
        } .otherwise {
            inst    := io.in_inst
        }
        pc  := io.in_pc
    }
    
}
// problem: out_inst and out_pc  always =0 ?