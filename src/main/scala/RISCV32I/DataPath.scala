package RISCV32I

import chisel3._
import chisel3.util._

class DataPath(dataWidth: Int) extends Module {
    val io = IO(new Bundle{
        val start = Input(Bool())
    })

    val pcr = Module(new PCR(data_width = dataWidth))
    val alu = Module(new ALU(data_width = dataWidth))
    val rf  = Module(new RegFile(data_width = dataWidth, block_number = 32, adr_width = 5))
    val mem = Module(new Memory(block_width = dataWidth, memSize = 1024, adr_width = 32))
    val im  = Module(new Memory(block_width = dataWidth, memSize = 1024, adr_width = 32))
}