package RISCV32I

import chisel3._
import chisel3.util._ 
import scala.annotation.switch

class Memory(LENGTH: Int, WORD_WIDTH: Int, STRB_WIDTH: Int) extends Module {
    val io = IO(new Bundle {
        val in_wr_en    = Input(Bool())
        val in_rd_en    = Input(Bool())
        val in_strb     = Input(UInt(STRB_WIDTH.W))
        val in_data     = Input(UInt(WORD_WIDTH.W))
        val in_addr_wr  = Input(UInt(WORD_WIDTH.W))
        val in_addr_rd  = Input(UInt(WORD_WIDTH.W))
        val out_data    = Output(UInt(WORD_WIDTH.W))
    })
    val in_data = Wire(Vec(STRB_WIDTH, UInt((WORD_WIDTH/STRB_WIDTH).W)))
    for(i <- 0 until STRB_WIDTH) {
        in_data(i)    := io.in_data(((WORD_WIDTH/STRB_WIDTH)*(i+1))-1, (WORD_WIDTH/STRB_WIDTH)*i)
    }
    val memory  = SyncReadMem(LENGTH, Vec(STRB_WIDTH, UInt((WORD_WIDTH/STRB_WIDTH).W)))
    when(io.in_wr_en) {
        memory.write(io.in_addr_wr(WORD_WIDTH-1, 2), in_data, io.in_strb)
    }
    io.out_data     := memory.read(io.in_addr_rd, io.in_rd_en)
}