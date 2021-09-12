package RISCV32I

import chisel3._
import chisel3.tester._
import org.scalatest._
import scala.util.Random

class ProcSpec extends FreeSpec with ChiselScalatestTester {
    "Proccessor functionality" in {
        test(new Core()) { dut =>
            dut.io.start.poke(true.B)
            dut.clock.step(6)
        }
    }
}