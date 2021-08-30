package RISCV32I

import chisel3._
import chisel3.util._

class ALU(data_width: Int) extends Module {
    val io = IO(new Bundle {
        val in_A    = Input(SInt(data_width.W))
        val in_B    = Input(SInt(data_width.W))
        val in_op   = Input(UInt(3.W))
        val in_op2  = Input(Bool())
        val out_res = Output(SInt(data_width.W))
    })

    switch(io.in_op) {
        is(0.U) { 
            when(io.in_op2) {
                io.out_res  := io.in_A - io.in_B
            } .otherwise {
                io.out_res  := io.in_A + io.in_B
            }
        }
        is(1.U) { 
            io.out_res  := io.in_A << io.in_B.asUInt()
        }
        is(2.U) { 
            io.out_res  := (io.in_A < io.in_B) 
        }
        is(3.U) { 
            io.out_res  := io.in_A.asUInt() < io.in_B.asUInt()
        }
        is(4.U) { 
            io.out_res  := io.in_A ^ io.in_B 
        }
        is(5.U) {
            when(io.in_op2) {
                io.out_res  := io.in_A >> io.in_B.asUInt()
            } .otherwise {
                io.out_res  := io.in_A.asUInt() >> io.in_B.asUInt()
            }
        }
        is(6.U) { 
            io.out_res  := io.in_A | io.in_B 
        }
        is(7.U) { 
            io.out_res  := io.in_A & io.in_B 
        }
    }
}
