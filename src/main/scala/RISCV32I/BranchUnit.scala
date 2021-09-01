package RISCV32I

import chisel3._
import chisel3.util._

class BranchUnit extends Module {
    val io = IO(new Bundle{
        val in_enable   = Input(Bool())
        val in_func3    = Input(UInt(3.W))
        val in_A        = Input(SInt(32.W))
        val in_B        = Input(SInt(32.W))

        val out_branch  = Output(Bool())

    })

    io.out_branch := false.B
    
    when(io.in_enable){
        switch(io.in_func3) {
            is(0.U) { io.out_branch := io.in_A === io.in_B }
            is(1.U) { io.out_branch := io.in_A =/= io.in_B }
            is(4.U) { io.out_branch := io.in_A < io.in_B }
            is(5.U) { io.out_branch := io.in_A >= io.in_B }
            is(6.U) { io.out_branch := io.in_A.asUInt() < io.in_B.asUInt() }
            is(7.U) { io.out_branch := io.in_A.asUInt() >= io.in_B.asUInt() }
        }
    }

    
}