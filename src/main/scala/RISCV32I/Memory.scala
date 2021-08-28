package RISCV32I

import chisel3._
import chisel3.util._ 

class Memory(block_number: Int, block_width: Int, adr_width) extends Module {
    val io = IO(new Bundle {
        val in_adr = Input(UInt(adr_width.W))
        val in_data = Input(UInt(block_width.W))
        val in_wr_en = Input(Bool())
        val out_data = Output(UInt(block_width.W))
    })
    val memFile = Mem(block_number, UInt(block_width.W))

    io.out_data := memFile.read(io.in_adr)
    when(io.in_wr_en) {
        memFile.write(io.in_adr, io.in_data)
    }
}