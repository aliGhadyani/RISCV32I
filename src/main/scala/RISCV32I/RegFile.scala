package RISCV32I

import chisel3._
import chisel3.util._ 

class RegFile(blockNumber: Int, blockWidth: Int) extends Module {
    val io = IO(new Bundle{
        // write enable signal input
        val wr_en = Input(Bool())

        // read adresses input
        val read_adr1 = Input(UInt(log2Ceil(blockNumber).W))
        val read_adr2 = Input(UInt(log2Ceil(blockNumber).W))

        // write adress input
        val write_adr = Input(UInt(log2Ceil(blockNumber).W))

        // write data input
        val data_in = Input(UInt(blockWidth.W))

        // read data output
        val data_out1 = Output(UInt(blockWidth.W))
        val data_out2 = Output(UInt(blockWidth.W))
    })
    val initVec = Seq.fill(blockNumber) {0.U(blockWidth.W)}
    val regFile = RegInit(VecInit(initVec))

    io.data_out1 := regFile(io.read_adr1)
    io.data_out2 := regFile(io.read_adr2)

    when(io.wr_en) {
        regFile(io.write_adr) := io.data_in
    }
}