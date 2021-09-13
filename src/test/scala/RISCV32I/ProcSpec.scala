package RISCV32I

import chisel3._
import chisel3.tester._
import org.scalatest._
import scala.util.Random

/* class ProcSpec extends FreeSpec with ChiselScalatestTester {
    var operand1 = Random.nextInt(200)
    var operand2 = Random.nextInt(100)
    "Proccessor functionality" in {
        test(new Proccessor()) { dut =>
            dut.io.in_inst_wr.poke(false.B)
            dut.io.in_inst.poke("b00000000000000000000000000010011".U)

            dut.clock.step(6)
            dut.dp.rf.regFile(31.U).expect(7.U)
        }
    }
} */