package RISCV32I

import chisel3._
import chisel3.tester._
import org.scalatest._

 class IFIDSpec extends FreeSpec with ChiselScalatestTester {
    "IF/ID register should work properly" in {
        test(new IFID) { dut =>
            dut.io.in_kill.poke(true.B) 
            dut.io.in_nop.poke(4.U)
            dut.io.out_inst.expect(4.U)

            dut.clock.step(1)

            dut.io.in_kill.poke(false.B)
            dut.io.in_pause.poke(true.B)
            dut.io.in_nop.poke(3.U)
            dut.io.in_inst.poke(5.U)
            dut.io.out_inst.expect(0.U)
            dut.io.out_pc.expect(0.U)

            dut.clock.step(1)

            dut.io.in_kill.poke(false.B)
            dut.io.in_pause.poke(false.B)
            dut.io.in_stall.poke(true.B)
            dut.io.in_nop.poke(3.U)
            dut.io.in_inst.poke(5.U)
            dut.io.out_inst.expect(3.U)

            dut.clock.step(1)

            dut.io.in_kill.poke(false.B)
            dut.io.in_pause.poke(false.B)
            dut.io.in_stall.poke(false.B)
            dut.io.in_nop.poke(3.U)
            dut.io.in_inst.poke(5.U)
            dut.io.out_inst.expect(5.U)

            dut.clock.step(1)

            dut.io.in_kill.poke(false.B)
            dut.io.in_pause.poke(false.B)
            dut.io.in_stall.poke(false.B)
            dut.io.in_pc.poke(1.U)
            dut.io.out_pc.expect(1.U)

        }
    }

}
 