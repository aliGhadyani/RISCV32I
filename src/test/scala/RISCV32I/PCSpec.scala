package RISCV32I

import chisel3._
import chisel3.tester._
import org.scalatest._
import scala.util.Random

/* class PCSpec extends FreeSpec with ChiselScalatestTester {
    "Program Counter should work properly" in {
        test(new PCR) { dut =>
            dut.io.in_pause.poke(false.B)
            dut.io.in_npc.poke(4.U)
            dut.io.out_pc.poke(4.U)

            dut.io.in_pause.poke(true.B)
            dut.io.in_npc.poke(4.U)
            dut.io.out_pc.poke(0.U)
        }
    }
} */