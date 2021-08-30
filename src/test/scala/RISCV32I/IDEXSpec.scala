package RISCV32I

import chisel3._
import chisel3.tester._
import org.scalatest._

class IDEXSpec extends FreeSpec with ChiselScalatestTester {
    "IF/ID register should work properly" in {
        test(new IDEX) { dut =>
            dut.io.in_pause.poke(false.B)
            dut.io.in_func3.poke(1.U)
            dut.io.in_pc.poke(5.U)
            dut.io.in_A.poke(6.U)
            dut.io.in_B.poke(7.U)
            dut.io.in_I.poke(8.U)
            dut.io.in_RD.poke(2.U)
            dut.io.in_Rs1.poke(3.U)
            dut.io.in_Rs2.poke(4.U)
            dut.io.out_func3.expect(1.U)
            dut.io.out_pc.expect(5.U)
            dut.io.out_A.expect(6.U)
            dut.io.out_B.expect(7.U)
            dut.io.out_I.expect(8.U)
            dut.io.out_RD.expect(2.U)
            dut.io.out_Rs1.expect(3.U)
            dut.io.out_Rs2.expect(4.U)

            dut.io.in_pause.poke(true.B)
            dut.io.in_pc.poke(5.U)
            dut.io.in_A.poke(6.U)
            dut.io.in_B.poke(7.U)
            dut.io.in_I.poke(8.U)
            dut.io.in_RD.poke(2.U)
            dut.io.in_Rs1.poke(3.U)
            dut.io.in_Rs2.poke(4.U)
            dut.io.out_pc.expect(0.U)
            dut.io.out_A.expect(0.U)
            dut.io.out_B.expect(0.U)
            dut.io.out_I.expect(0.U)
            dut.io.out_RD.expect(0.U)
            dut.io.out_Rs1.expect(0.U)
            dut.io.out_Rs2.expect(0.U)


        
    }
}