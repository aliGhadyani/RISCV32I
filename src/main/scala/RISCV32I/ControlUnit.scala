package RISCV32I

import chisel3._
import chisel3.util.HasBlackBoxResource

class ControlUnit extends BlackBox with HasBlackBoxResource {
    val io = IO(new Bundle{
        val in_inst = Input(UInt(32.W))
        val out_ctrl= Output(UInt(18.W))
    })
    addResource("/ControlUnit.v")
    /* io.out_ctrl(0)       // pc_rori
    io.out_ctrl(1)          // b_enable
    io.out_ctrl(4, 2)       // imm_slc
    io.out_ctrl(5)          // jump
    io.out_ctrl(6)          // M wr en
    io.out_ctrl(8, 7)       // M d_slc
    io.out_ctrl(9)          // WB wr en
    io.out_ctrl(11, 10)     // EX A slc
    io.out_ctrl(13, 12)     // EX B slc
    io.out_ctrl(14)         // EX ALU f4
    io.out_ctrl(17, 15)     // EX ALU func */
}