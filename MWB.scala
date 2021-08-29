package RISCV32I

import chisel3._
import chisel3.util._

class MWB(data_width: Int) extends Module {
    val io = IO(new Bundle {
        val in_pause = Input(Bool())

        val in_WB       = Input(new WBSig())
        val in_data     = Input(UInt(data_width))
        val in_Rd       = Input(UInt(adr_width))
        val in_alu      = Input(UInt(data_width))

        val out_WB      = Output(new WBSig())
        val out_data    = Output(UInt(data_width))
        val out_Rd      = Output(UInt(adr_width))
        val out_alu     = Output(UInt(data_width))
            
    })

    val wb_sig = RegInit(new WBSig())

    val data = RegInit(0.U(data_width))
    val rd   = RegInit(0.U(adr_width))
    val alu  = RegInit(0.U(data_width))

    io.out_WB := wb_sig
    io.out_data := data
    io.out_Rd := rd 
    io.out_alu := alu

    when(io.in_pause){

    } .otherwise {
        wb_sig := io.in_WB
        data := io.in_data
        rd := io.in_Rd
        alu := io.in_alu
    }

}