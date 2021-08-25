package RISCV32I

import chisel3._
import chisel3.util._

class ALU(dataWidth: Int) extends Module {
    val io = IO(new Bundle{
        val in_A = Input(SInt(dataWidth.W))
        val in_B = Input(SInt(dataWidth.W))
        val in_op = Input(UInt(3.W))
        val out_res1 = Output(SInt(dataWidth.W))
    })

    io.out_res1 := 0.S
    switch(io.in_op) {
        is(0.U) { io.out_res1 := io.in_A + io.in_B }
        is(1.U) { io.out_res1 := io.in_A - io.in_B }
        is(2.U) { io.out_res1 := io.in_A & io.in_B }
        is(3.U) { io.out_res1 := io.in_A | io.in_B }
        is(4.U) { io.out_res1 := io.in_A ^ io.in_B }
        is(5.U) { io.out_res1 := ~ io.in_A }
        is(6.U) { io.out_res1 := - io.in_A }
        is(7.U) { io.out_res1 := io.in_A - io.in_B }
    }
}
