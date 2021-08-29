package RISCV32I

import chisel3._
import chisel3.util._

class EXM(data_width: Int, adr_width: Int) extends Module {
    val io = IO(new Bundle {
        val in_pause = Input(Bool())

        val in_M        = Input(new MSig())
        val in_WB       = Input(new WBSig())
        val in_alu_res  = Input(UInt(data_width.W))
        val in_rs2      = Input(UInt(data_width.W))
        val in_Rd       = Input(UInt(adr_width.W))

        val out_M = Output(new MSig())
        val out_WB = Output(new WBSig())
        val out_alu_res = Output(UInt(data_width.W))
        val out_rs2 = Output(UInt(data_width.W))
        val out_Rd = Output(UInt(adr_width.W))

    })

    val m_sig = RegInit(new MSig())
    val wb_sig = RegInit(new WBSig())

    val alu_res = RegInit(0.U(data_width.))
    val rd = RegInit(0.U(adr_width.W))
    val rs2 = RegInit(0.U(data_width.W))


    io.out_WB := wb_sig
    io.out_M := m_sig
    
    io.out_alu_res := alu_res
    io.out_rs2 := rs2
    io.out_Rd := rd

    when(io.in_pause){

    } .otherwise {
        alu_res := io.in_alu_res
        rd := io.in_Rd
        rs2 := io.in_rs2
        wb_sig := io.in_WB
        m_sig := io.in_M

    }

    
}
