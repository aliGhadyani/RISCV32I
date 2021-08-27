package RISCV32I

import chisel3._
import chisel3.util._ 

class Mem(memSize: Int, memBlockWidth: Int) extends Module {
    val io = IO(new Bundle {
        val in_adr = Input(UInt(log2Ceil(memSize).W))
        val in_data = Input(UInt(memBlockWidth.W))
        val in_wr_en = Input(Bool())
        val out_data = Output(UInt(memBlockWidth.W))
    })
    val initVec = Seq.fill(memSize) {0.U(memBlockWidth.W)}
    val memFile = RegInit(VecInit(initVec))

    io.out_data := memFile(io.in_adr)
    when(io.in_wr_en) {
        memFile(io.in_adr) := io.in_data
    }
}