package RISCV32I

import chisel3._
import chisel3.util._
import scala.annotation.switch

class ControlUnit extends Module {
    val io = IO(new Bundle{
        val in_inst = Input(UInt(32.W))
        val out_ctrl= Output(UInt(18.W))
    })
    
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
    io.out_ctrl := "b000_0_00_00_0_01_0_0_000_0_0".U

    switch(io.in_inst) {
        is("b0110111".U) {              // LUI
            /* io.out_ctrl(1)   := false.B
            io.out_ctrl(4, 2)   := 0.U
            io.out_ctrl(5)      := false.B
            io.out_ctrl(6)      := false.B
            io.out_ctrl(8, 7)   := 1.U
            io.out_ctrl(9)      := true.B
            io.out_ctrl(11, 10) := 2.U
            io.out_ctrl(13, 12) := 1.U
            io.out_ctrl(14)     := false.B
            io.out_ctrl(17, 15) := "b000".U */
            io.out_ctrl := "b000_0_01_10_1_01_0_0_000_0_0".U
        }
        is("b0010111".U) {              // AUIPC
            /* io.out_ctrl(1)   := false.B
            io.out_ctrl(4, 2)   := 0.U
            io.out_ctrl(5)      := false.B
            io.out_ctrl(6)      := false.B
            io.out_ctrl(8, 7)   := 1.U
            io.out_ctrl(9)      := true.B
            io.out_ctrl(11, 10) := 1.U
            io.out_ctrl(13, 12) := 1.U
            io.out_ctrl(14)     := false.B
            io.out_ctrl(17, 15) := "b000".U */
            io.out_ctrl := "b000_0_01_01_1_01_0_0_000_0_0".U
        }
        is("b1101111".U) {              // JAL
            /* io.out_ctrl(1)   := false.B
            io.out_ctrl(4, 2)   := 5.U
            io.out_ctrl(5)      := true.B
            io.out_ctrl(6)      := false.B
            io.out_ctrl(8, 7)   := 0.U
            io.out_ctrl(9)      := true.B
            io.out_ctrl(11, 10) := 1.U
            io.out_ctrl(13, 12) := 2.U
            io.out_ctrl(14)     := false.B
            io.out_ctrl(17, 15) := "b000".U */
            io.out_ctrl := "b000_0_10_01_1_00_0_1_101_0_0".U
        }
        is("b1100111".U) {              // JALR
            /* io.out_ctrl(1)   := false.B
            io.out_ctrl(4, 2)   := 0.U
            io.out_ctrl(5)      := true.B
            io.out_ctrl(0)      := true.B
            io.out_ctrl(6)      := false.B
            io.out_ctrl(8, 7)   := 0.U
            io.out_ctrl(9)      := false.B
            io.out_ctrl(11, 10) := 1.U
            io.out_ctrl(13, 12) := 2.U
            io.out_ctrl(14)     := false.B
            io.out_ctrl(17, 15) := "b000".U */
            io.out_ctrl := "b000_0_10_01_0_00_0_1_000_0_1".U
        }
        is("b1100011".U) {              // BRANCH
            /* io.out_ctrl(1)   := true.B
            io.out_ctrl(4, 2)   := 3.U
            io.out_ctrl(5)      := false.B
            io.out_ctrl(6)      := false.B
            io.out_ctrl(8, 7)   := 1.U
            io.out_ctrl(9)      := false.B
            io.out_ctrl(11, 10) := 1.U
            io.out_ctrl(13, 12) := 1.U
            io.out_ctrl(14)     := false.B
            io.out_ctrl(17, 15) := "b000".U */
            io.out_ctrl := "b000_0_01_01_0_01_0_0_011_1_0".U
        }
        is("b0000011".U) {              // LOAD
            /* io.out_ctrl(1)   := false.B
            io.out_ctrl(4, 2)   := 1.U
            io.out_ctrl(5)      := false.B
            io.out_ctrl(6)      := false.B
            io.out_ctrl(8, 7)   := 0.U
            io.out_ctrl(9)      := true.B
            io.out_ctrl(11, 10) := 0.U
            io.out_ctrl(13, 12) := 1.U
            io.out_ctrl(14)     := false.B
            io.out_ctrl(17, 15) := "b000".U */
            io.out_ctrl := "b000_0_01_00_1_00_0_0_001_0_0".U
        }
        is("b0100011".U) {              // STORE
            /* io.out_ctrl(1)   := false.B
            io.out_ctrl(4, 2)   := 4.U
            io.out_ctrl(5)      := false.B
            io.out_ctrl(6)      := true.B
            io.out_ctrl(8, 7)   := 0.U
            io.out_ctrl(9)      := false.B
            io.out_ctrl(11, 10) := 0.U
            io.out_ctrl(13, 12) := 1.U
            io.out_ctrl(14)     := false.B
            io.out_ctrl(17, 15) := "b000".U */
            io.out_ctrl := "b000_0_01_00_0_00_1_0_100_0_0".U
        }
        is("b0010011".U) {              //OP-IMM
            /* io.out_ctrl(1)      := false.B
            when(io.in_inst(14, 12) === "b011".U) {
                io.out_ctrl(4, 2)   := 2.U
            } .otherwise {
                io.out_ctrl(4, 2)   := 1.U
            }
            io.out_ctrl(5)      := false.B

            io.out_ctrl(6)      := false.B
            io.out_ctrl(8, 7)   := 1.U

            io.out_ctrl(9)      := true.B

            io.out_ctrl(11, 10) := 0.U
            io.out_ctrl(13, 12) := 1.U
            when(io.in_inst(14, 12) === "b101".U & io.in_inst(31, 25) === "b0100000".U) {
                io.out_ctrl(14) := true.B
            } .otherwise {
                io.out_ctrl(14) := false.B
            }
            io.out_ctrl(17, 15) := io.in_inst(14, 12) */
            when(io.in_inst(14, 12) === "b011".U) {
                io.out_ctrl := Cat(io.in_inst(14, 12), "b0_01_00_1_01_0_0_010_0_0".U)
            } .otherwise {
                when((io.in_inst(14, 12) === "b101".U) & (io.in_inst(31, 25) === "b0100000".U)) {
                    io.out_ctrl := Cat(io.in_inst(14, 12), "b1_01_00_1_01_0_0_001_0_0".U)
                } .otherwise {
                    io.out_ctrl := Cat(io.in_inst(14, 12), "b0_01_00_1_01_0_0_001_0_0".U)
                }
            }
        }
        is("b0110011".U) {              // OP
            /* io.out_ctrl(1)   := false.B
            io.out_ctrl(4, 2)   := 3.U
            io.out_ctrl(5)      := false.B
            io.out_ctrl(6)      := false.B
            io.out_ctrl(8, 7)   := 1.U
            io.out_ctrl(9)      := true.B
            io.out_ctrl(11, 10) := 0.U
            io.out_ctrl(13, 12) := 0.U
            when(io.in_inst(31, 25) === "b0100000".U) {
                io.out_ctrl(14) := true.B
            } .otherwise {
                io.out_ctrl(14) := false.B
            }
            io.out_ctrl(17, 15) := io.in_inst(14, 12) */
            when(io.in_inst(31, 25) === "b0100000".U) {
                io.out_ctrl := Cat(io.in_inst(14, 12), "b1_00_00_1_01_0_0_011_0_0".U)
            } .otherwise {
                io.out_ctrl := Cat(io.in_inst(14, 12), "b0_00_00_1_01_0_0_011_0_0".U)
            }
        }
        is("b0001111".U) {              // MISC-MEM
            /* io.out_ctrl(1)   := false.B
            io.out_ctrl(4, 2)   := 0.U
            io.out_ctrl(5)      := false.B
            io.out_ctrl(6)      := false.B
            io.out_ctrl(8, 7)   := 0.U
            io.out_ctrl(9)      := false.B
            io.out_ctrl(11, 10) := 0.U
            io.out_ctrl(13, 12) := 0.U
            io.out_ctrl(14)     := false.B
            io.out_ctrl(17, 15) := "b000".U */
            io.out_ctrl := "b000_0_00_00_0_01_0_0_000_0_0".U
        }
        is("b1110011".U) {              // SYSTEM
            /* io.out_ctrl(1)   := false.B
            io.out_ctrl(4, 2)   := 0.U
            io.out_ctrl(5)      := false.B

            io.out_ctrl(6)      := false.B
            io.out_ctrl(8, 7)   := 0.U

            io.out_ctrl(9)      := false.B

            io.out_ctrl(11, 10) := 0.U
            io.out_ctrl(13, 12) := 0.U
            io.out_ctrl(14)     := false.B
            io.out_ctrl(17, 15) := "b000".U */
            io.out_ctrl := "b000_0_00_00_0_01_0_0_000_0_0".U
        }
    }
}