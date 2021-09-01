package RISCV32I

import chisel3._
import chisel3.util._

class Proccessor extends Module {
    val io  = IO(new Bundle{
        val in_inst     = Input(UInt(32.W))
        val in_inst_wr  = Input(Bool())
    })

    val dp  = Module(new DataPath())
    val cu  = Module(new ControlUnit())

    cu.io.in_inst       := dp.io.out_inst
    dp.io.in_ctrl       := cu.io.out_ctrl
    dp.io.in_inst       := io.in_inst
    dp.io.in_inst_wr    := io.in_inst_wr
    dp.io.in_pause      := io.in_inst_wr

    dp.im.memFile(0.U)    := "b00000000011100000000111110010011".U
    dp.im.memFile(1.U)    := "b00000000000000000000000000010011".U
    dp.im.memFile(2.U)    := "b00000000000000000000000000010011".U
    dp.im.memFile(3.U)    := "b00000000000000000000000000010011".U
    dp.im.memFile(4.U)    := "b00000000000000000000000000010011".U
    dp.im.memFile(5.U)    := "b00000000000000000000000000010011".U
    dp.im.memFile(6.U)    := "b00000000000000000000000000010011".U
    dp.im.memFile(7.U)    := "b00000000000000000000000000010011".U
}