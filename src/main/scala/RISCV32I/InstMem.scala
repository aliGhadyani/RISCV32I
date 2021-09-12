package RISCV32I

import chisel3._
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
