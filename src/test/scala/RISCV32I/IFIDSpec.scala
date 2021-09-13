package RISCV32I

import chisel3._
import chisel3.tester._
import org.scalatest._

/* class IFIDSpec extends FreeSpec with ChiselScalatestTester {
    "IF/ID register should work properly" in {
        test(new IFID) { dut =>
            dut.io.in_kill.poke(false.B)
            dut.io.in_stall.poke(false.B)
            dut.io.in_inst.poke("b00000000011100000000111110010011".U)
            dut.io.in_pc.poke(5.U)
            dut.io.in_nop.poke("b00000000000000000000000000010011".U)
            println("[test01] - after initialization all registers most contain zero")
            println("[result] - " + dut.io.out_inst.peek().toString() + " " + dut.io.out_pc.peek().toString())
            dut.io.out_inst.expect("b00000000000000000000000000000000".U)
            dut.io.out_pc.expect(0.U)

            dut.clock.step(1)

            dut.io.in_kill.poke(false.B)
            dut.io.in_stall.poke(false.B)
            dut.io.in_inst.poke("b01000000011100000000111110010011".U)
            dut.io.in_pc.poke(12.U)
            dut.io.in_nop.poke("b00000000000000000000000000010011".U)
            println("[test02] - test with custom inputs")
            println("[result] - " + dut.io.out_inst.peek().toString() + " " + dut.io.out_pc.peek().toString())
            dut.io.out_inst.expect("b00000000011100000000111110010011".U)
            dut.io.out_pc.expect(5.U)

            dut.clock.step(1)

            dut.io.in_kill.poke(true.B)
            dut.io.in_stall.poke(true.B)
            dut.io.in_inst.poke("b01000000011100000000111110010011".U)
            dut.io.in_pc.poke(16.U)
            dut.io.in_nop.poke("b00000000000000000000000000010011".U)
            println("[test03] - test with custom inputs")
            println("[result] - " + dut.io.out_inst.peek().toString() + " " + dut.io.out_pc.peek().toString())
            dut.io.out_inst.expect("b00000000000000000000000000010011".U)
            dut.io.out_pc.expect(12.U)

            dut.clock.step(1)
            
            dut.io.in_kill.poke(false.B)
            dut.io.in_stall.poke(false.B)
            dut.io.in_inst.poke("b01000000011100000000111110010011".U)
            dut.io.in_pc.poke(18.U)
            dut.io.in_nop.poke("b00000000000000000000000000010011".U)
            println("[test04] - test with custom inputs")
            println("[result] - " + dut.io.out_inst.peek().toString() + " " + dut.io.out_pc.peek().toString())
            dut.io.out_inst.expect("b00000000000000000000000000010011".U)
            dut.io.out_pc.expect(16.U)

            dut.clock.step(1)
        }
    }

} */