package RISCV32I

import chisel3._
import chisel3.util._

class ALU extends Module {
    val io = IO(new Bundle {
        val in_A    = Input(SInt(32.W))
        val in_B    = Input(SInt(32.W))
        val in_op   = Input(UInt(3.W))
        val in_op2  = Input(Bool())
        val out_res = Output(SInt(32.W))
    })

    io.out_res := 0.S

    switch(io.in_op) {
        is(0.U) { 
            when(io.in_op2) {
                io.out_res  := io.in_A - io.in_B
            } .otherwise {
                io.out_res  := io.in_A + io.in_B
            }
        }
        is(1.U) { 
            io.out_res  := (io.in_A << io.in_B(4, 0)).asSInt()
        }
        is(2.U) { 
            when(io.in_A < io.in_B) {
                io.out_res  := 1.S 
            } .otherwise {
                io.out_res  := 0.S
            }
        }
        is(3.U) { 
            when(io.in_A.asUInt() < io.in_B.asUInt()) {
                io.out_res  := 1.S 
            } .otherwise {
                io.out_res  := 0.S
            }
        }
        is(4.U) { 
            io.out_res  := io.in_A ^ io.in_B 
        }
        is(5.U) {
            when(io.in_op2) {
                io.out_res  := Cat(io.in_A(31), io.in_A >> io.in_B(4, 0)).asSInt()
            } .otherwise {
                io.out_res  := Cat("b0".U, io.in_A.asUInt() >> io.in_B(4, 0)).asSInt()
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
