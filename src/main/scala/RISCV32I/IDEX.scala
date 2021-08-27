package RISCV32I

import chisel3._
import chisel3.util._

class IDEX(data_width: Int, adr_width: Int) extends Module {
    val io = IO(new Bundle {
        val in_func3 = Input(UInt(3.W))
        val in_func7 = Input(UInt(7.W))
        val in_WB = Input(new Bundle{
            val wr = Bool()
        })
        val in_M = Input(new Bundle{
            val rd = Bool()
            val wr = Bool()
        })
        val in_EX = Input(new Bundle{
            val A_slc = UInt(2.W)
            val B_slc = UInt(2.W)
            val dst_slc = UInt(2.W)
        })
        val in_A = Input(UInt(data_width.W))
        val in_B = Input(UInt(data_width.W))
        val in_I = Input(UInt(data_width.W))
        val in_rd = Input(UInt(adr_width.W))
    })

    val pc = RegInit(0.U(data_width.W))
    val A = RegInit(0.U(data_width.W))
    val B = RegInit(0.U(data_width.W))
    val I = RegInit(0.U(data_width.W))


}