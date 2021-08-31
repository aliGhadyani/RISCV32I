package RISCV32I

import chisel3._
import chisel3.tester._
import org.scalatest._

 class EXMSpec extends FreeSpec with ChiselScalatestTester {
    "EX/M register should work properly" in {
        test(new EXM) { dut =>
            
            dut.io.in_pause.poke(true.B)
            dut.io.in_func3.poke(1.U)
            dut.io.in_alu_res.poke(2.U)
            dut.io.in_Rs2_val.poke(3.U)
            dut.io.in_Rd.poke(4.U)
            dut.io.out_func3.poke(0.U)
            dut.io.out_alu_res.poke(0.U)
            dut.io.out_Rs2_val.poke(0.U)
            dut.io.out_Rd.poke(0.U)

            dut.clock.step(1)

            dut.io.in_pause.poke(false.B)
            dut.io.in_func3.poke(1.U)
            dut.io.in_alu_res.poke(2.U)
            dut.io.in_Rs2_val.poke(3.U)
            dut.io.in_Rd.poke(4.U)
            dut.io.out_func3.poke(1.U)
            dut.io.out_alu_res.poke(2.U)
            dut.io.out_Rs2_val.poke(3.U)
            dut.io.out_Rd.poke(4.U)

            dut.clock.step(1)

            dut.io.in_pause.poke(true.B)
            dut.io.in_func3.poke(3.U)
            dut.io.in_alu_res.poke(12.U)
            dut.io.in_Rs2_val.poke(13.U)
            dut.io.in_Rd.poke(14.U)
            dut.io.out_func3.poke(1.U)
            dut.io.out_alu_res.poke(2.U)
            dut.io.out_Rs2_val.poke(3.U)
            dut.io.out_Rd.poke(4.U)

            dut.clock.step(1)

            dut.io.in_pause.poke(false.B)
            dut.io.in_func3.poke(3.U)
            dut.io.in_alu_res.poke(12.U)
            dut.io.in_Rs2_val.poke(13.U)
            dut.io.in_Rd.poke(14.U)
            dut.io.out_func3.poke(3.U)
            dut.io.out_alu_res.poke(12.U)
            dut.io.out_Rs2_val.poke(13.U)
            dut.io.out_Rd.poke(14.U)


        }
    }
} 