package RISCV32I

import chisel3._
import chisel3.tester._
import org.scalatest._

 class MWBSpec extends FreeSpec with ChiselScalatestTester {
    "M/WB register should work properly" in {
        test(new MWB) { dut =>
            
            dut.io.in_pause.poke(true.B)
            dut.io.in_data.poke(8.U)
            dut.io.in_Rd.poke(2.U)
            dut.io.out_data.expect(0.U)
            dut.io.out_Rd.expect(0.U)

            dut.clock.step(1)

            dut.io.in_pause.poke(false.B)
            dut.io.in_data.poke(8.U)
            dut.io.in_Rd.poke(2.U)
            dut.io.out_data.expect(8.U)
            dut.io.out_Rd.expect(2.U)

            dut.clock.step(1)

            dut.io.in_pause.poke(true.B)
            dut.io.in_data.poke(5.U)
            dut.io.in_Rd.poke(1.U)
            dut.io.out_data.expect(8.U)
            dut.io.out_Rd.expect(2.U) 

            dut.clock.step(1)

            dut.io.in_pause.poke(false.B)
            dut.io.in_data.poke(5.U)
            dut.io.in_Rd.poke(1.U)
            dut.io.out_data.expect(5.U)
            dut.io.out_Rd.expect(1.U)

        }
    }
} 
