package RISCV32I

import chisel3._
import chisel3.util._
import chisel3.util.experimental.loadMemoryFromFile

class InstMem extends Module {
    val io  = IO(new Bundle{
        val in_adr  = Input(UInt(12.W))
        val out_inst= Output(UInt(32.W))
    })
    val init    = Seq(
                "b0000_0000_0000_0000_0011_00001_0110111".U,    // LUI R1, 3
                "b0000_0000_0000_0000_0001_00110_0010111".U,    // AUIPC R6, 1
                "b0000_0010_1101_00001_000_00001_0010011".U,    // ADDI R1, R1, 45
                "b0000_0001_1111_00000_000_00010_0010011".U,    // ADDI R2, R0, 31
                "b0000000_00010_00001_000_00011_0110011".U,     // ADD R3, R1, R2
                "b0000000_00010_00001_010_00100_0110011".U,     // SLT R4, R1, R2
                "b000_0000_00100_00000_001_10000_1100011".U,    // BNE R0, R4, 16
                "b000_0000_00001_00000_010_00000_0100011".U,    // SW R1, R0(0)
                "b000_0000_00010_00000_010_00100_0100011".U,    // SW R2, R0(4)
                "b000_0000_00100_00000_000_01100_0010011".U,    // BEQ R0, R4, 12
                "b000_0000_00001_00000_010_00100_0100011".U,    // SW R1, R0(4)
                "b000_0000_00010_00000_010_00000_0100011".U,    // SW R2, R0(0)
                "b000_0000_00011_00000_010_01000_0100011".U,    // SW R3, R0(8)
                "b0000_0000_0000_00000_010_00101_0000011".U,    // LW R5, R0(0)
                "b0000_0000_0000_0000_0000_00111_1101111".U     // JAL R7, 0
            )
    val memoery = RegInit(VecInit(init))
    io.out_inst := memoery(io.in_adr >> 2)
    /* val mem = Mem(1024, UInt(32.W))
    loadMemoryFromFile(mem, "/instructions.hex")
    io.out_inst := mem(io.in_adr) */
}
