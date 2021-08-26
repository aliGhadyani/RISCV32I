package RISCV32I

import chisel3._
import chisel3.util._

class ForwardingUnit extends Module {
    val io = IO(new Bundle{
        val M_reg_wr = Input(Bool())
        val M_reg_dst = Input(UInt(5.W))

        val WB_reg_wr = Input(Bool())
        val WB_reg_dst = Input(UInt(5.W))

        val EX_A_adr = Input(UInt(5.W))
        val EX_B_adr = Input(UInt(5.W))

        val slc_A = Output(UInt(2.W))
        val slc_B = Output(UInt(2.W))
    })

    when(io.M_reg_wr & (io.M_reg_dst === io.EX_A_adr)) {
        io.slc_A := 1.U
    } .elsewhen(io.WB_reg_wr & (io.WB_reg_dst === io.EX_A_adr)) {
        io.slc_A := 2.U
    } .otherwise {
        io.slc_A := 0.U
    }

    when(io.M_reg_wr & (io.M_reg_dst === io.EX_B_adr)) {
        io.slc_B := 1.U
    } .elsewhen(io.WB_reg_wr & (io.WB_reg_dst === io.EX_B_adr)) {
        io.slc_B := 2.U
    } .otherwise {
        io.slc_B := 0.U
    }
}