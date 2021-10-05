package RISCV32I

import chisel3._
import chisel3.tester._
import org.scalatest._

/*  class MemSpec extends FreeSpec with ChiselScalatestTester {
    val a = 32.U
    "Mem should provid proper functionality" in {
        test(new Memory(bytes = 1024)) { dut =>
            dut.io.in_adr.poke(12.U)
            dut.io.in_w.poke(24.U)
            dut.io.in_d.poke(0.U)
            dut.io.in_wr_en.poke(true.B)
            dut.io.in_func.poke(2.U)
            println("[test01] - after initialization all blocks most contain zero")
            println("[result] - block12 contains " + dut.io.out_d.peek().toString() + " " + dut.io.out_w.peek().toString())
            dut.io.out_w.expect(0.U)
            dut.io.out_d.expect(0.U)

            dut.clock.step(1)

            dut.io.in_adr.poke(12.U)
            dut.io.in_w.poke(24.U)
            dut.io.in_d.poke(0.U)
            dut.io.in_wr_en.poke(false.B)
            dut.io.in_func.poke(2.U)
            println("[test02] - in past clock 24 pushed in block 12")
            println("[result] - block12 contains " + dut.io.out_d.peek().toString() + " " + dut.io.out_w.peek().toString())
            dut.io.out_w.expect(24.U)
            dut.io.out_d.expect(0.U)

            dut.clock.step(1)

            dut.io.in_adr.poke(14.U)
            dut.io.in_w.poke(511.U)
            dut.io.in_d.poke(0.U)
            dut.io.in_wr_en.poke(true.B)
            dut.io.in_func.poke(0.U)
            println("[test03] - in past clock 24 pushed in block 12")
            println("[result] - block12 contains " + dut.io.out_d.peek().toString() + " " + dut.io.out_w.peek().toString())
            dut.io.out_w.expect(0.U)
            dut.io.out_d.expect(0.U)

            dut.clock.step(1)

            dut.io.in_adr.poke(14.U)
            dut.io.in_w.poke(24.U)
            dut.io.in_d.poke(0.U)
            dut.io.in_wr_en.poke(false.B)
            dut.io.in_func.poke(0.U)
            println("[test04] - in past clock 24 pushed in block 12")
            println("[result] - block12 contains " + dut.io.out_d.peek().toString() + " " + dut.io.out_w.peek().toString())
            dut.io.out_w.expect("b11111111111111111111111111111111".U)
            dut.io.out_d.expect("b11111111111111111111111111111111".U)

            dut.clock.step(1)
        }
    }
}  */