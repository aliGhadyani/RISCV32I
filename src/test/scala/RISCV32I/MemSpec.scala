package RISCV32I

import chisel3._
import chisel3.tester._
import org.scalatest._

class MemSpec extends FreeSpec with ChiselScalatestTester {
    val a = 32.U
    "Mem should provid proper functionality" in {
        test(new Memory(block_number = 1024)) { dut =>
            dut.io.in_adr.poke(12.U)
            dut.io.in_data.poke(24.U)
            dut.io.in_wr_en.poke(false.B)
            println("[test]   - after initialization all blocks most contain zero")
            println("[result] - block12 contains " + dut.io.out_data.peek().toString())
            dut.io.out_data.expect(0.U)

            println(a.S)
        }
    }
}