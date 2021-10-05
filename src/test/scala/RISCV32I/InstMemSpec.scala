package RISCV32I

import chisel3._
import chisel3.tester._
import org.scalatest._

class InstMemSpec extends FreeSpec with ChiselScalatestTester {
    "InstMem functionality" in {
        test(new InstMem()) { dut =>
            dut.io.in_adr.poke(0.U)
            println("[test01] - running test")
            dut.io.out_inst.expect("b0000_0000_0000_0000_0011_00001_0110111".U)

            dut.io.in_adr.poke(4.U)
            println("[test02] - running test")
            dut.io.out_inst.expect("b0000_0000_0000_0000_0001_00110_0010111".U)

            dut.io.in_adr.poke(8.U)
            println("[test03] - running test")
            dut.io.out_inst.expect("b0000_0010_1101_00001_000_00001_0010011".U)

            dut.io.in_adr.poke(12.U)
            println("[test04] - running test")
            dut.io.out_inst.expect("b0000_0001_1111_00000_000_00010_0010011".U)

            dut.io.in_adr.poke(16.U)
            println("[test05] - running test")
            dut.io.out_inst.expect("b0000000_00010_00001_000_00011_0110011".U)

            dut.io.in_adr.poke(20.U)
            println("[test06] - running test")
            dut.io.out_inst.expect("b0000000_00010_00001_010_00100_0110011".U)

            dut.io.in_adr.poke(24.U)
            println("[test07] - running test")
            dut.io.out_inst.expect("b000_0000_00100_00000_001_10000_1100011".U)

            dut.io.in_adr.poke(28.U)
            println("[test08] - running test")
            dut.io.out_inst.expect("b000_0000_00001_00000_010_00000_0100011".U)

            dut.io.in_adr.poke(32.U)
            println("[test09] - running test")
            dut.io.out_inst.expect("b000_0000_00010_00000_010_00100_0100011".U)

            dut.io.in_adr.poke(36.U)
            println("[test10] - running test")
            dut.io.out_inst.expect("b000_0000_00100_00000_000_01100_0010011".U)

            dut.io.in_adr.poke(40.U)
            println("[test11] - running test")
            dut.io.out_inst.expect("b000_0000_00001_00000_010_00100_0100011".U)

            dut.io.in_adr.poke(44.U)
            println("[test12] - running test")
            dut.io.out_inst.expect("b000_0000_00010_00000_010_00000_0100011".U)

            dut.io.in_adr.poke(48.U)
            println("[test13] - running test")
            dut.io.out_inst.expect("b000_0000_00011_00000_010_01000_0100011".U)

            dut.io.in_adr.poke(52.U)
            println("[test14] - running test")
            dut.io.out_inst.expect("b0000_0000_0000_00000_010_00101_0000011".U)
            
            dut.io.in_adr.poke(56.U)
            println("[test15] - running test")
            dut.io.out_inst.expect("b0000_0000_0000_0000_0000_00111_1101111".U)

            /* for(i <- 0 until Program.code.length*4) {
                dut.io.in_adr.poke(i.U)
                printf("[test%d] - running test", i.U)
                dut.io.out_inst.expect(Program.code(i))
            } */
        }
    }
}