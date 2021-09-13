package RISCV32I

import chisel3._
import chisel3.tester._
import org.scalatest._

/*  class IDEXSpec extends FreeSpec with ChiselScalatestTester {
    "ID/EX register should work properly" in {
        test(new IDEX) { dut =>
            dut.io.in_pause.poke(false.B)
            dut.io.in_func3.poke(2.U)
            dut.io.in_pc.poke(1.U)
            dut.io.in_A.poke(6.U)
            dut.io.in_B.poke(7.U)
            dut.io.in_I.poke(8.U)
            dut.io.in_Rd.poke(2.U)
            dut.io.in_Rs1.poke(3.U)
            dut.io.in_Rs2.poke(4.U)
            dut.io.out_func3.expect(2.U)
            dut.io.out_pc.expect(1.U)
            dut.io.out_A.expect(6.U)
            dut.io.out_B.expect(7.U)
            dut.io.out_I.expect(8.U)
            dut.io.out_Rd.expect(2.U)
            dut.io.out_Rs1.expect(3.U)
            dut.io.out_Rs2.expect(4.U)

            dut.io.in_pause.poke(true.B)
            dut.io.in_pc.poke(1.U)
            dut.io.in_A.poke(6.U)
            dut.io.in_B.poke(7.U)
            dut.io.in_I.poke(8.U)
            dut.io.in_Rd.poke(2.U)
            dut.io.in_Rs1.poke(3.U)
            dut.io.in_Rs2.poke(4.U)
            dut.io.out_pc.expect(0.U)
            dut.io.out_A.expect(0.U)
            dut.io.out_B.expect(0.U)
            dut.io.out_I.expect(0.U)
            dut.io.out_Rd.expect(0.U)
            dut.io.out_Rs1.expect(0.U)
            dut.io.out_Rs2.expect(0.U)

            dut.io.in_pause.poke(false.B)
            dut.io.in_func3.poke(2.U)
            dut.io.in_pc.poke(10.U)
            dut.io.in_A.poke(16.U)
            dut.io.in_B.poke(17.U)
            dut.io.in_I.poke(18.U)
            dut.io.in_Rd.poke(12.U)
            dut.io.in_Rs1.poke(13.U)
            dut.io.in_Rs2.poke(14.U)
            dut.io.out_func3.expect(2.U)
            dut.io.out_pc.expect(10.U)
            dut.io.out_A.expect(16.U)
            dut.io.out_B.expect(17.U)
            dut.io.out_I.expect(18.U)
            dut.io.out_Rd.expect(12.U)
            dut.io.out_Rs1.expect(13.U)
            dut.io.out_Rs2.expect(14.U)

            dut.io.in_pause.poke(true.B)
            dut.io.in_pc.poke(10.U)
            dut.io.in_A.poke(16.U)
            dut.io.in_B.poke(17.U)
            dut.io.in_I.poke(18.U)
            dut.io.in_Rd.poke(12.U)
            dut.io.in_Rs1.poke(13.U)
            dut.io.in_Rs2.poke(14.U)
            dut.io.out_pc.expect(0.U)
            dut.io.out_A.expect(0.U)
            dut.io.out_B.expect(0.U)
            dut.io.out_I.expect(0.U)
            dut.io.out_Rd.expect(0.U)
            dut.io.out_Rs1.expect(0.U)
            dut.io.out_Rs2.expect(0.U)
        }    

    }
}  */