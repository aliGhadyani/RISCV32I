package RISCV32I

import chisel3._
import chisel3.util._
import scala.annotation.switch

class ControlUnit extends Module {
    val io = IO(new Bundle{
        val in_inst = Input(UInt(32.W))
        val out_ctrl= Output(new Ctrl())
    })
    
    io.out_ctrl.pc_rori         := false.B
    io.out_ctrl.b_enable        := false.B
    io.out_ctrl.slc_imm         := 0.U
    io.out_ctrl.jump            := false.B
    io.out_ctrl.sig_M.wr_en     := false.B
    io.out_ctrl.sig_M.slc_d     := 1.U
    io.out_ctrl.sig_WB.wr_en    := false.B
    io.out_ctrl.sig_EX.slc_A    := 0.U
    io.out_ctrl.sig_EX.slc_B    := 0.U
    io.out_ctrl.sig_EX.alu_f4   := false.B
    io.out_ctrl.sig_EX.alu_func := "b000".U

    switch(io.in_inst) {
        is("b0110111".U) {              // LUI
            io.out_ctrl.b_enable        := false.B
            io.out_ctrl.slc_imm         := 0.U
            io.out_ctrl.jump            := false.B

            io.out_ctrl.sig_M.wr_en     := false.B
            io.out_ctrl.sig_M.slc_d     := 1.U

            io.out_ctrl.sig_WB.wr_en    := true.B

            io.out_ctrl.sig_EX.slc_A    := 2.U
            io.out_ctrl.sig_EX.slc_B    := 1.U
            io.out_ctrl.sig_EX.alu_f4   := false.B
            io.out_ctrl.sig_EX.alu_func := "b000".U
        }
        is("b0010111".U) {              // AUIPC
            io.out_ctrl.b_enable        := false.B
            io.out_ctrl.slc_imm         := 0.U
            io.out_ctrl.jump            := false.B

            io.out_ctrl.sig_M.wr_en     := false.B
            io.out_ctrl.sig_M.slc_d     := 1.U

            io.out_ctrl.sig_WB.wr_en    := true.B

            io.out_ctrl.sig_EX.slc_A    := 1.U
            io.out_ctrl.sig_EX.slc_B    := 1.U
            io.out_ctrl.sig_EX.alu_f4   := false.B
            io.out_ctrl.sig_EX.alu_func := "b000".U
        }
        is("b1101111".U) {              // JAL
            io.out_ctrl.b_enable        := false.B
            io.out_ctrl.slc_imm         := 5.U
            io.out_ctrl.jump            := true.B

            io.out_ctrl.sig_M.wr_en     := false.B
            io.out_ctrl.sig_M.slc_d     := 0.U

            io.out_ctrl.sig_WB.wr_en    := true.B

            io.out_ctrl.sig_EX.slc_A    := 1.U
            io.out_ctrl.sig_EX.slc_B    := 2.U
            io.out_ctrl.sig_EX.alu_f4   := false.B
            io.out_ctrl.sig_EX.alu_func := "b000".U
        }
        is("b1100111".U) {              // JALR
            io.out_ctrl.b_enable        := false.B
            io.out_ctrl.slc_imm         := 0.U
            io.out_ctrl.jump            := true.B
            io.out_ctrl.pc_rori         := true.B

            io.out_ctrl.sig_M.wr_en     := false.B
            io.out_ctrl.sig_M.slc_d     := 0.U

            io.out_ctrl.sig_WB.wr_en    := false.B

            io.out_ctrl.sig_EX.slc_A    := 1.U
            io.out_ctrl.sig_EX.slc_B    := 2.U
            io.out_ctrl.sig_EX.alu_f4   := false.B
            io.out_ctrl.sig_EX.alu_func := "b000".U
        }
        is("b1100011".U) {              // BRANCH
            io.out_ctrl.b_enable        := true.B
            io.out_ctrl.slc_imm         := 3.U
            io.out_ctrl.jump            := false.B

            io.out_ctrl.sig_M.wr_en     := false.B
            io.out_ctrl.sig_M.slc_d     := 1.U

            io.out_ctrl.sig_WB.wr_en    := false.B

            io.out_ctrl.sig_EX.slc_A    := 1.U
            io.out_ctrl.sig_EX.slc_B    := 1.U
            io.out_ctrl.sig_EX.alu_f4   := false.B
            io.out_ctrl.sig_EX.alu_func := "b000".U
        }
        is("b0000011".U) {              // LOAD
            io.out_ctrl.b_enable        := false.B
            io.out_ctrl.slc_imm         := 1.U
            io.out_ctrl.jump            := false.B

            io.out_ctrl.sig_M.wr_en     := false.B
            io.out_ctrl.sig_M.slc_d     := 0.U

            io.out_ctrl.sig_WB.wr_en    := true.B

            io.out_ctrl.sig_EX.slc_A    := 0.U
            io.out_ctrl.sig_EX.slc_B    := 1.U
            io.out_ctrl.sig_EX.alu_f4   := false.B
            io.out_ctrl.sig_EX.alu_func := "b000".U
        }
        is("b0100011".U) {              // STORE
            io.out_ctrl.b_enable        := false.B
            io.out_ctrl.slc_imm         := 4.U
            io.out_ctrl.jump            := false.B

            io.out_ctrl.sig_M.wr_en     := true.B
            io.out_ctrl.sig_M.slc_d     := 0.U

            io.out_ctrl.sig_WB.wr_en    := false.B

            io.out_ctrl.sig_EX.slc_A    := 0.U
            io.out_ctrl.sig_EX.slc_B    := 1.U
            io.out_ctrl.sig_EX.alu_f4   := false.B
            io.out_ctrl.sig_EX.alu_func := "b000".U
        }
        is("b0010011".U) {              //OP-IMM
            io.out_ctrl.b_enable        := false.B
            when(io.in_inst(14, 12) === "b011".U) {
                io.out_ctrl.slc_imm     := 2.U
            } .otherwise {
                io.out_ctrl.slc_imm     := 1.U
            }
            io.out_ctrl.jump            := false.B

            io.out_ctrl.sig_M.wr_en     := false.B
            io.out_ctrl.sig_M.slc_d     := 1.U

            io.out_ctrl.sig_WB.wr_en    := true.B

            io.out_ctrl.sig_EX.slc_A    := 0.U
            io.out_ctrl.sig_EX.slc_B    := 1.U
            when(io.in_inst(14, 12) === "b101".U & io.in_inst(31, 25) === "b0100000".U) {
                io.out_ctrl.sig_EX.alu_f4   := true.B
            } .otherwise {
                io.out_ctrl.sig_EX.alu_f4   := false.B
            }
            io.out_ctrl.sig_EX.alu_func := io.in_inst(14, 12)
        }
        is("b0110011".U) {              // OP
            io.out_ctrl.b_enable        := false.B
            io.out_ctrl.slc_imm         := 3.U
            io.out_ctrl.jump            := false.B

            io.out_ctrl.sig_M.wr_en     := false.B
            io.out_ctrl.sig_M.slc_d     := 1.U

            io.out_ctrl.sig_WB.wr_en    := true.B

            io.out_ctrl.sig_EX.slc_A    := 0.U
            io.out_ctrl.sig_EX.slc_B    := 0.U
            when(io.in_inst(31, 25) === "b0100000".U) {
                io.out_ctrl.sig_EX.alu_f4   := true.B
            } .otherwise {
                io.out_ctrl.sig_EX.alu_f4   := false.B
            }
            io.out_ctrl.sig_EX.alu_func := io.in_inst(14, 12)
        }
        is("b0001111".U) {              // MISC-MEM
            io.out_ctrl.b_enable        := false.B
            io.out_ctrl.slc_imm         := 0.U
            io.out_ctrl.jump            := false.B

            io.out_ctrl.sig_M.wr_en     := false.B
            io.out_ctrl.sig_M.slc_d     := 0.U

            io.out_ctrl.sig_WB.wr_en    := false.B

            io.out_ctrl.sig_EX.slc_A    := 0.U
            io.out_ctrl.sig_EX.slc_B    := 0.U
            io.out_ctrl.sig_EX.alu_f4   := false.B
            io.out_ctrl.sig_EX.alu_func := "b000".U
        }
        is("b1110011".U) {              // SYSTEM
            io.out_ctrl.b_enable        := false.B
            io.out_ctrl.slc_imm         := 0.U
            io.out_ctrl.jump            := false.B

            io.out_ctrl.sig_M.wr_en     := false.B
            io.out_ctrl.sig_M.slc_d     := 0.U

            io.out_ctrl.sig_WB.wr_en    := false.B

            io.out_ctrl.sig_EX.slc_A    := 0.U
            io.out_ctrl.sig_EX.slc_B    := 0.U
            io.out_ctrl.sig_EX.alu_f4   := false.B
            io.out_ctrl.sig_EX.alu_func := "b000".U
        }
    }
}