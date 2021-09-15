package RISCV32I

import chisel3._
import chisel3.tester._
import org.scalatest._
import scala.util.Random

class CoreSpec extends FreeSpec with ChiselScalatestTester {
    "Single Cycle Core functionality" in {
        test(new Core()) { dut =>
            dut.io.start.poke(true.B)
            println("[test01] - running instruction")               // LUI R1, 3
            dut.io.rf_adr.expect(1.U)
            dut.io.pc.expect(0.U)
            dut.io.ctrl.expect("b0_1_0_0_0_0_101_01_10_0_000".U)
            dut.io.mem_in.expect(0.U)
            dut.io.mem_adr.expect(12288.U)
            dut.io.rf_in.expect(12288.U)

            dut.clock.step(1)

            println("[test02] - running instruction")               // AUIPC R6, 1
            dut.io.rf_adr.expect(6.U)
            dut.io.pc.expect(4.U)
            dut.io.mem_in.expect(0.U)
            dut.io.mem_adr.expect(4100.U)
            dut.io.rf_in.expect(4100.U)

            dut.clock.step(1)

            println("[test03] - running instruction")               // ADDI R1, R1, 45
            dut.io.rf_adr.expect(1.U)
            dut.io.pc.expect(8.U)
            dut.io.mem_in.expect(0.U)
            dut.io.mem_adr.expect(12333.U)
            dut.io.rf_in.expect(12333.U)

            dut.clock.step(1)

            println("[test04] - running instruction")               // ADDI R2, R0, 31
            dut.io.rf_adr.expect(2.U)
            dut.io.pc.expect(12.U)
            dut.io.mem_in.expect(0.U)
            dut.io.mem_adr.expect(31.U)
            dut.io.rf_in.expect(31.U)

            dut.clock.step(1)

            println("[test05] - running instruction")               // ADD R3, R1, R2
            dut.io.rf_adr.expect(3.U)
            dut.io.pc.expect(16.U)
            dut.io.mem_in.expect(31.U)
            dut.io.mem_adr.expect(12364.U)
            dut.io.rf_in.expect(12364.U)

            dut.clock.step(1)

            println("[test06] - running instruction")               // SLT R4, R1, R2
            dut.io.rf_adr.expect(4.U)
            dut.io.pc.expect(20.U)
            dut.io.mem_in.expect(31.U)
            dut.io.mem_adr.expect(0.U)
            dut.io.rf_in.expect(0.U)

            dut.clock.step(1)

            println("[test07] - running instruction")               // BNE R0, R4, 16
            dut.io.rf_adr.expect(16.U)
            dut.io.pc.expect(24.U)
            dut.io.mem_in.expect(0.U)
            dut.io.mem_adr.expect(0.U)
            dut.io.rf_in.expect(0.U)

            dut.clock.step(1)

            println("[test08] - running instruction")               // SW R1, R0(0)
            dut.io.rf_adr.expect(0.U)
            dut.io.pc.expect(28.U)
            dut.io.mem_in.expect(12333.U)
            dut.io.mem_adr.expect(0.U)
            dut.io.rf_in.expect(0.U)

            dut.clock.step(1)

            println("[test09] - running instruction")               // SW R2, R0(4)
            dut.io.rf_adr.expect(4.U)
            dut.io.pc.expect(32.U)
            dut.io.mem_in.expect(31.U)
            dut.io.mem_adr.expect(4.U)
            dut.io.rf_in.expect(4.U)

            dut.clock.step(1)

            println("[test10] - running instruction")               // BEQ R0, R4, 12
            dut.io.rf_adr.expect(12.U)
            dut.io.pc.expect(36.U)
            dut.io.mem_in.expect(0.U)
            //dut.io.mem_adr.expect(0.U)
            //dut.io.rf_in.expect(0.U)

            dut.clock.step(1)

            println("[test11] - running instruction")               // SW R1, R0(4)
            dut.io.rf_adr.expect(4.U)
            dut.io.pc.expect(40.U)
            dut.io.mem_in.expect(12333.U)
            dut.io.mem_adr.expect(4.U)
            dut.io.rf_in.expect(4.U)

            dut.clock.step(1)

            println("[test12] - running instruction")               // SW R2, R0(0)
            dut.io.rf_adr.expect(0.U)
            dut.io.pc.expect(44.U)
            dut.io.mem_in.expect(31.U)
            dut.io.mem_adr.expect(0.U)
            dut.io.rf_in.expect(0.U)

            dut.clock.step(1)

            println("[test13] - running instruction")               // SW R3, R0(8)
            dut.io.rf_adr.expect(8.U)
            dut.io.pc.expect(48.U)
            dut.io.mem_in.expect(12364.U)
            dut.io.mem_adr.expect(8.U)
            dut.io.rf_in.expect(8.U)

            dut.clock.step(1)

            println("[test14] - running instruction")               // LW R5, R0(0)
            dut.io.rf_adr.expect(5.U)
            dut.io.pc.expect(52.U)
            dut.io.mem_in.expect(0.U)
            dut.io.mem_adr.expect(0.U)
            dut.io.rf_in.expect(31.U)

            dut.clock.step(1)

            println("[test15] - running instruction")               // JAL R7, 0
            dut.io.rf_adr.expect(7.U)
            dut.io.pc.expect(56.U)
            dut.io.mem_in.expect(0.U)
            dut.io.mem_adr.expect(60.U)
            dut.io.rf_in.expect(60.U)
        }
    }
}