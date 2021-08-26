package RISCV32I

import chisel3._
import chisel3.util._

class CtrlUnit(instructionWidth: Int, functionWidth: Int) extends Module {
    val io = IO(new Bundle{
        val inst = Input(UInt(instructionWidth.W))
        val func = Input(UInt(functionWidth.W))

        val M = Output(new Bundle{
            val MEM_wr = Bool()
            val MEM_rd = Bool()
        })

        val EX = Output(new Bundle{
            val ALU_src1 = UInt(2.W)
            val ALU_src2 = UInt(2.W)
            val ALU_op = UInt(3.W)
        })

        val WB = Output(new Bundle{
            val reg_wr = Bool()
        })
    })

    
}