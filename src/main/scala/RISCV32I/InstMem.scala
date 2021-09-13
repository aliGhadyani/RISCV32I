package RISCV32I

import chisel3._
import chisel3.util._
import chisel3.util.experimental.loadMemoryFromFile

class InstMem extends Module {
    val io  = IO(new Bundle{
        val in_adr  = Input(UInt(12.W))
        val out_inst= Output(UInt(32.W))
    })
    val memory = Mem(1024, UInt(32.W))
    io.out_inst := memory(io.in_adr >> 2)  
    loadMemoryFromFile(memory, "C:/text.hex")
}
