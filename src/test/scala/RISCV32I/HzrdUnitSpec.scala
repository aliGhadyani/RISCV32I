package RISCV32I

import chisel3._
import chisel3.tester._
import org.scalatest._

/*  class HzrdUnitSpec extends FreeSpec with ChiselScalatestTester {
    "Hazard Unit should work properly" in {
        test(new HazardUnit) { dut =>
           
            dut.io.in_branch.poke(true.B)
            dut.io.in_jump.poke(false.B)
            println("[test01] - only branch on")
            println("[result] - stall=" + dut.io.out_stall.peek().toString + " kill=" + dut.io.out_kill.peek().toString)
            dut.io.out_stall.expect(true.B)
            dut.io.out_kill.expect(true.B)

            dut.io.in_branch.poke(false.B)
            dut.io.in_jump.poke(true.B)
            println("[test02] - only jump on")
            println("[result] - stall=" + dut.io.out_stall.peek().toString + " kill=" + dut.io.out_kill.peek().toString)
            dut.io.out_stall.expect(true.B)
            dut.io.out_kill.expect(true.B)

            dut.io.in_branch.poke(false.B)
            dut.io.in_jump.poke(false.B)
            println("[test03] - no one on")
            println("[result] - stall=" + dut.io.out_stall.peek().toString + " kill=" + dut.io.out_kill.peek().toString)
            dut.io.out_stall.expect(false.B)
            dut.io.out_kill.expect(false.B)
        }
    }
}  */