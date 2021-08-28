package RISCV32I

import chisel3._
import chisel3.util._

class  IFID(data_width: Int, inst_width: Int) extends Module {
    val io = IO(new Bundle {
        val in_pause = Input(Bool())
        val in_stall = Input(Bool())
        val in_kill = Input(Bool())
        val in_pc = Input(UInt(data_width.W))
        val in_inst = Input(UInt(inst_width.W))
        val in_nop = Input(UInt(inst_width.W))

        val out_pc = Output(UInt(data_width.W))
        val out_inst = Output(UInt(inst_width.W))
    })
    val pc = RegInit(0.U(data_width.W))
    val inst = RegInit(io.in_nop)

    io.out_pc := pc

    when(io.in_kill) {
        io.out_inst := io.in_nop
    } .otherwise {
        io.out_inst := inst
    }

    when(io.in_pause) {
        // keeps current values
    } .otherwise {
        when(io.in_stall) {
            inst := io.in_nop
        } .otherwise {
            inst := io.in_inst
        }
        pc_p4 := io.in_pc_p4
    }
    
}