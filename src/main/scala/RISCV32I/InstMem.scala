package RISCV32I

import chisel3._
import chisel3.util._
import chisel3.util.experimental.loadMemoryFromFile

class InstMem extends Module {
    val io  = IO(new Bundle{
        val in_adr  = Input(UInt(32.W))
        val out_inst= Output(UInt(32.W))
    })
    val mem = Mem(1024, UInt(32.W))
    loadMemoryFromFile(mem, "C:/Users/Ghadyani/OneDrive/Desktop/im.hex")
    io.out_inst := mem(io.in_adr)
}