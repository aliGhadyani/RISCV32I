package RISCV32I

import chisel3._
import chisel3.tester._
import org.scalatest._
import scala.util.Random

class CoreSpec extends FreeSpec with ChiselScalatestTester {
    "Single Cycle Core functionality" in {
        test(new Core()) { dut =>
            dut.io.start.poke(true.B)
            dut.clock.step(6)
        }
    }
}