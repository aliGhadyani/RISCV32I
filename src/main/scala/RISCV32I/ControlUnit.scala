package RISCV32I

import chisel3._
import chisel3.util._
import scala.annotation.switch

class CtrlUnit(instructionWidth: Int, functionWidth: Int) extends Module {
    val io = IO(new Bundle{
        val in_inst = Input(UInt(instructionWidth.W))
        val out_ctrl= Output(Ctrl)
    })
    
    switch(io.in_inst) {
        is("b0110111".U) {

        }
        is("b0010111".U) {

        }
        is("b1101111".U) {

        }
        is("b1100111".U) {

        }
        is("b1100011".U) {
            io.out_ctrl.b_enable:= true.B
            io.out_ctrl.func3   := io.in_inst(14, 12)
            io.out_ctrl.slc_imm := 3.U

            io.out_ctrl.sig_M.wr_en := false.B
            io.out_ctrl.sig_M.sld_d := 0.U

            io.out_ctrl.sig_WB.wr_en:= false.B

            io.out_ctrl.sig_EX.slc_A:= 1.U
            io.out_ctrl.sig_EX.slc_B:= 1.U
            io.out_ctrl.sig_EX.alu_f4  := false.B
            io.out_ctrl.sig_EX.alu_func:= "b000".U
        }
        is("b0000011".U) {

        }
        is("b0100011".U) {

        }
        is("b0010011".U) {

        }
        is("b0110011".U) {
            io.out_ctrl.alu_func:= io.in_inst(14, 12)
        }
        is("b0001111".U) {

        }
        is("b1110011".U) {
            
        }
    }
}