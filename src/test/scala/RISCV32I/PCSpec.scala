package RISCV32I

import chisel3._
import chisel3.tester._
import org.scalatest._
import scala.util.Random

/*  class PCSpec extends FreeSpec with ChiselScalatestTester {
    "Program Counter should work properly" in {
        test(new PCR()) { dut =>
            dut.io.in_pause.poke(false.B)
            dut.io.in_npc.poke(4.U)
            println("[test01] - after initialization register most contains zero")
            println("[result] - reg contains " + dut.io.out_pc.peek().toString())
            dut.io.out_pc.expect(0.U)

            dut.clock.step(1)

            dut.io.in_pause.poke(false.B)
            dut.io.in_npc.poke(121.U)
            println("[test02] - in past clock 4 pushed in reg")
            println("[result] - reg contains " + dut.io.out_pc.peek().toString())
            dut.io.out_pc.expect(4.U)

            dut.clock.step(1)

            dut.io.in_pause.poke(true.B)
            dut.io.in_npc.poke(91.U)
            println("[test03] - in past clock 121 pushed in reg")
            println("[result] - reg contains " + dut.io.out_pc.peek().toString())
            dut.io.out_pc.expect(121.U)

            dut.clock.step(1)

            dut.io.in_pause.poke(false.B)
            dut.io.in_npc.poke(91.U)
            println("[test04] - in past clock pause was on and 91 pushed")
            println("[result] - reg contains " + dut.io.out_pc.peek().toString())
            dut.io.out_pc.expect(121.U)
        }
    }
}  */