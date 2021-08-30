package RISCV32I

import chisel3._
import chisel3.util._ 

class RegFile extends Module {
    val io = IO(new Bundle{
        // write enable signal input
        val wr_en = Input(Bool())

        // read adresses input
        val read_adr1 = Input(UInt(5.W))
        val read_adr2 = Input(UInt(5.W))

        // write adress input
        val write_adr = Input(UInt(5.W))

        // write data input
        val data_in = Input(UInt(32.W))

        // read data output
        val data_out1 = Output(UInt(32.W))
        val data_out2 = Output(UInt(32.W))
    })
    val initVec = Seq.fill(32) {0.U(32.W)}
    val regFile = RegInit(VecInit(initVec))

    io.data_out1 := regFile(io.read_adr1)
    io.data_out2 := regFile(io.read_adr2)

    when(io.wr_en) {
        regFile(io.write_adr) := io.data_in
    }
}