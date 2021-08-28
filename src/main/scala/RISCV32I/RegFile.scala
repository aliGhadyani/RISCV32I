package RISCV32I

import chisel3._
import chisel3.util._ 

class RegFile(block_number: Int, data_width: Int, adr_width: Int) extends Module {
    val io = IO(new Bundle{
        // write enable signal input
        val wr_en = Input(Bool())

        // read adresses input
        val read_adr1 = Input(UInt(adr_width.W))
        val read_adr2 = Input(UInt(adr_width.W))

        // write adress input
        val write_adr = Input(UInt(adr_width.W))

        // write data input
        val data_in = Input(UInt(data_width.W))

        // read data output
        val data_out1 = Output(UInt(data_width.W))
        val data_out2 = Output(UInt(data_width.W))
    })
    val initVec = Seq.fill(block_number) {0.U(data_width.W)}
    val regFile = RegInit(VecInit(initVec))

    io.data_out1 := regFile(io.read_adr1)
    io.data_out2 := regFile(io.read_adr2)

    when(io.wr_en) {
        regFile(io.write_adr) := io.data_in
    }
}