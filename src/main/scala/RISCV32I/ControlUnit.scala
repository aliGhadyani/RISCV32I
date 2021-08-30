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

        }
        is("b0000011".U) {

        }
        is("b0100011".U) {

        }
        is("b0010011".U) {

        }
        is("b0110011".U) {

        }
        is("b0001111".U) {

        }
        is("b1110011".U) {
            
        }
    }
}