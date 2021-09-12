package RISCV32I

import chisel3._
<<<<<<< HEAD
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
=======
import chisel3.util.experimental.loadMemoryFromFile

class InstMem extends Module{
	val io = IO(new Bundle{
		val in = Input(UInt(10.W))
		val out = Output(UInt(32.W))
	})

	val mem = Mem(1024, UInt(32.W))
	io.out := mem(io.in)
	loadMemoryFromFile(mem, "C:/Users/Ghadyani/OneDrive/Desktop/im.bin")
}
>>>>>>> 1080df5e3cd127f1c8145f94eb6a8fa57b0f586b
