package RISCV32I

import chisel3._
import chisel3.tester._
import chisel3.iotesters.PeekPokeTester
import org.scalatest._
import scala.util.Random

class RegFileSpec extends FreeSpec with ChiselScalatestTester {
    var operand1 = (Random.nextInt(200))
    var operand2 = (Random.nextInt(200))
    "RegFile should provide proper result in sequence of data input and output" in {
        test(new RegFile(adressWidth = 5, blockWidth = 32)) { dut =>
            dut.io.wr_en.poke(true.B)
            dut.io.write_adr.poke(1.U)
            dut.io.data_in.poke(29.U)
            dut.io.read_adr1.poke(0.U)
            dut.io.read_adr2.poke(1.U)
            dut.io.data_out1.expect(0.U)
            dut.io.data_out2.expect(0.U)

            dut.clock.step(1)

            dut.io.wr_en.poke(false.B)
            dut.io.write_adr.poke(1.U)
            dut.io.data_in.poke(15.U)
            dut.io.read_adr1.poke(0.U)
            dut.io.read_adr2.poke(1.U)
            dut.io.data_out1.expect(0.U)
            dut.io.data_out2.expect(29.U)

            dut.clock.step(1)

            dut.io.wr_en.poke(true.B)
            dut.io.write_adr.poke(5.U)
            dut.io.data_in.poke(7.U)
            dut.io.read_adr1.poke(5.U)
            dut.io.read_adr2.poke(1.U)
            dut.io.data_out1.expect(0.U)
            dut.io.data_out2.expect(29.U)

            dut.clock.step(1)

            dut.io.wr_en.poke(false.B)
            dut.io.write_adr.poke(1.U)
            dut.io.data_in.poke(15.U)
            dut.io.read_adr1.poke(5.U)
            dut.io.read_adr2.poke(1.U)
            dut.io.data_out1.expect(7.U)
            dut.io.data_out2.expect(29.U)
        }
    }
}