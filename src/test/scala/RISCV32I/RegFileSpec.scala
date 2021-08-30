package RISCV32I

import chisel3._
import chisel3.tester._
import org.scalatest._

/* class RegFileSpec extends FreeSpec with ChiselScalatestTester {
    "RegFile should provide proper result in sequence of data input and output" in {
        test(new RegFile) { dut =>
            dut.io.wr_en.poke(true.B)
            dut.io.write_adr.poke(1.U)
            dut.io.data_in.poke(29.U)
            dut.io.read_adr1.poke(0.U)
            dut.io.read_adr2.poke(2.U)
            println("[test]   - after initialization all registers most contain zero")
            println("[result] - reg2 contains " + dut.io.data_out2.peek().toString())
            dut.io.data_out1.expect(0.U)
            dut.io.data_out2.expect(0.U)

            dut.clock.step(1)

            dut.io.wr_en.poke(false.B)
            dut.io.write_adr.poke(1.U)
            dut.io.data_in.poke(15.U)
            dut.io.read_adr1.poke(0.U)
            dut.io.read_adr2.poke(1.U)
            println("[test]   - in past clock 29 pushed in reg1 and in this clock most save")
            println("[result] - reg1 contains " + dut.io.data_out2.peek().toString())
            dut.io.data_out1.expect(0.U)
            dut.io.data_out2.expect(29.U)

            dut.clock.step(1)

            dut.io.wr_en.poke(true.B)
            dut.io.write_adr.poke(5.U)
            dut.io.data_in.poke(7.U)
            dut.io.read_adr1.poke(5.U)
            dut.io.read_adr2.poke(1.U)
            println("[test]   - in past clock wr was unable so reg1 most contain 29 instead of 15")
            println("[result] - reg1 contains " + dut.io.data_out2.peek().toString())
            dut.io.data_out1.expect(0.U)
            dut.io.data_out2.expect(29.U)

            dut.clock.step(1)

            dut.io.wr_en.poke(false.B)
            dut.io.write_adr.poke(1.U)
            dut.io.data_in.poke(15.U)
            dut.io.read_adr1.poke(5.U)
            dut.io.read_adr2.poke(1.U)
            println("[test]   - in past clock 7 pushed in reg5")
            println("[result] - reg5 contains " + dut.io.data_out2.peek().toString())
            dut.io.data_out1.expect(7.U)
            dut.io.data_out2.expect(29.U)
        }
    }
} */