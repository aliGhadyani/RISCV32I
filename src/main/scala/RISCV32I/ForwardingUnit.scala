package RISCV32I

import chisel3._
import chisel3.util._

class ForwardingUnit extends Module {
    val io = IO(new Bundle{
        val in_M_reg_wr    = Input(Bool())
        val in_M_reg_dst   = Input(UInt(5.W))

        val in_WB_reg_wr   = Input(Bool())
        val in_WB_reg_dst  = Input(UInt(5.W))

        val in_EX_A_adr    = Input(UInt(5.W))
        val in_EX_B_adr    = Input(UInt(5.W))

        val out_slc_A       = Output(UInt(2.W))
        val out_slc_B       = Output(UInt(2.W))
    })

    when(io.in_M_reg_wr & (io.in_M_reg_dst === io.in_EX_A_adr)) {
        io.out_slc_A := 1.U
    } .elsewhen(io.in_WB_reg_wr & (io.in_WB_reg_dst === io.in_EX_A_adr)) {
        io.out_slc_A := 2.U
    } .otherwise {
        io.out_slc_A := 0.U
    }

    when(io.in_M_reg_wr & (io.in_M_reg_dst === io.in_EX_B_adr)) {
        io.out_slc_B := 1.U
    } .elsewhen(io.in_WB_reg_wr & (io.in_WB_reg_dst === io.in_EX_B_adr)) {
        io.out_slc_B := 2.U
    } .otherwise {
        io.out_slc_B := 0.U
    }
}