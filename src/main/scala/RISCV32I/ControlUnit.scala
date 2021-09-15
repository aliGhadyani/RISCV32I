package RISCV32I

import chisel3._
import chisel3.util._

class ControlUnit extends Module {
    val io = IO(new Bundle{
        val in_inst = Input(UInt(32.W))
        val out_ctrl= Output(UInt(17.W))

    })

    /*
        ALU operation   3bit    2:0
        ALU f4          1bit    3
        A src slc       2bit    5:4
        B src slc       2bit    7:6
        Imm src slc     3bit    10:8
        Jump en.        1bit    11
        Branch en.      1bit    12
        PC or Reg       1bit    13
        mem. wr en      1bit    14
        Reg wr en       1bit    15
        wr data slc     1bit    16
    */
    io.out_ctrl := "b0_0_0_0_0_0_000_00_00_0_000".U

    switch(io.in_inst(6, 0)) {
        is("b0110111".U) {              // LUI
            io.out_ctrl := "b0_1_0_0_0_0_101_01_10_0_000".U
        }
        is("b0010111".U) {              // AUIPC
            io.out_ctrl := "b0_1_0_0_0_0_101_01_01_0_000".U
        }
        is("b1101111".U) {              // JAL
            io.out_ctrl := "b0_1_0_0_0_1_110_10_01_0_000".U
        }
        is("b1100111".U) {              // JALR
            io.out_ctrl := "b0_1_0_1_0_1_000_10_01_0_000".U
        }
        is("b1100011".U) {              // BRANCH
            io.out_ctrl := "b0_0_0_0_1_0_011_00_00_0_000".U
        }
        is("b0000011".U) {              // LOAD
            io.out_ctrl := "b1_1_0_0_0_0_000_01_00_0_000".U
        }
        is("b0100011".U) {              // STORE
            io.out_ctrl := "b0_0_1_0_0_0_010_01_00_0_000".U
        }
        is("b0010011".U) {              //OP-IMM
            when((io.in_inst(14, 12) === "b101".U) & (io.in_inst(31, 25) === "b0100000".U)) {
                io.out_ctrl := Cat("b0_1_0_0_0_0_000_01_00_1".U, io.in_inst(14, 12))
            } .otherwise {
                io.out_ctrl := Cat("b0_1_0_0_0_0_000_01_00_0".U, io.in_inst(14, 12))
            }
        }
        is("b0110011".U) {              // OP
            when(((io.in_inst(14, 12) === "b101".U) | (io.in_inst(14, 12) === "b000".U) )& (io.in_inst(31, 25) === "b0100000".U)) {
                io.out_ctrl := Cat("b0_1_0_0_0_0_000_00_00_1".U, io.in_inst(14, 12))
            } .otherwise {
                io.out_ctrl := Cat("b0_1_0_0_0_0_000_00_00_0".U, io.in_inst(14, 12))
            }
        }
        is("b0001111".U) {              // MISC-MEM
            io.out_ctrl := "b0_0_0_0_0_0_000_00_00_0_000".U
        }
        is("b1110011".U) {              // SYSTEM
            io.out_ctrl := "b0_0_0_0_0_0_000_00_00_0_000".U
        }
    }
} 