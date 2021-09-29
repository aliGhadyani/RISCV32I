package RISCV32I

import chisel3._
import chisel3.util._
import chisel3.util.experimental.loadMemoryFromFile

class InstMem extends Module {
    val io  = IO(new Bundle{
        val in_adr  = Input(UInt(12.W))
        val out_inst= Output(UInt(32.W))
    })
    val init    = Program.code
    val memoery = RegInit(VecInit(init))
    io.out_inst := memoery(io.in_adr >> 2)
    /* val mem = Mem(1024, UInt(32.W))
    loadMemoryFromFile(mem, "/instructions.hex")
    io.out_inst := mem(io.in_adr) */
}
