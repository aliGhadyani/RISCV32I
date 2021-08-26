package RISCV32I

import chisel3._
import chisel3.tester._
import chisel3.iotesters.PeekPokeTester
import org.scalatest._
import scala.util.Random

class FWDUSpec extends FreeSpec with ChiselScalatestTester {
    "Forwarding Unit should provide proper functionality" in {
        test(new ForwardingUnit) { dut =>
            dut.io.M_reg_wr.poke(false.B)
            dut.io.M_reg_dst.poke(5.U)
            dut.io.WB_reg_wr.poke(false.B)
            dut.io.WB_reg_dst.poke(7.U)
            dut.io.EX_A_adr.poke(5.U)
            dut.io.EX_B_adr.poke(7.U)
            dut.io.slc_A.expect(0.U)
            dut.io.slc_B.expect(0.U)

            dut.io.M_reg_wr.poke(true.B)
            dut.io.M_reg_dst.poke(5.U)
            dut.io.WB_reg_wr.poke(false.B)
            dut.io.WB_reg_dst.poke(7.U)
            dut.io.EX_A_adr.poke(5.U)
            dut.io.EX_B_adr.poke(7.U)
            dut.io.slc_A.expect(1.U)
            dut.io.slc_B.expect(0.U)

            dut.io.M_reg_wr.poke(true.B)
            dut.io.M_reg_dst.poke(3.U)
            dut.io.WB_reg_wr.poke(false.B)
            dut.io.WB_reg_dst.poke(7.U)
            dut.io.EX_A_adr.poke(5.U)
            dut.io.EX_B_adr.poke(7.U)
            dut.io.slc_A.expect(0.U)
            dut.io.slc_B.expect(0.U)

            dut.io.M_reg_wr.poke(false.B)
            dut.io.M_reg_dst.poke(5.U)
            dut.io.WB_reg_wr.poke(true.B)
            dut.io.WB_reg_dst.poke(7.U)
            dut.io.EX_A_adr.poke(5.U)
            dut.io.EX_B_adr.poke(7.U)
            dut.io.slc_A.expect(0.U)
            dut.io.slc_B.expect(2.U)

            dut.io.M_reg_wr.poke(true.B)
            dut.io.M_reg_dst.poke(7.U)
            dut.io.WB_reg_wr.poke(true.B)
            dut.io.WB_reg_dst.poke(7.U)
            dut.io.EX_A_adr.poke(5.U)
            dut.io.EX_B_adr.poke(7.U)
            dut.io.slc_A.expect(0.U)
            dut.io.slc_B.expect(1.U)
        }
    }
}