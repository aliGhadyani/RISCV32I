package RISCV32I

import chisel3._
import chisel3.tester._
import chisel3.iotesters.PeekPokeTester
import org.scalatest._
import scala.util.Random

/*
class ALUSpec extends FreeSpec with ChiselScalatestTester {
    var operand1 = (Random.nextInt(200))
    var operand2 = (Random.nextInt(200))
    "add" + " ALU should provide proper result regarding the operations and operands" in {
        test(new ALU(dataWidth = 32)) { dut =>
            dut.io.in_A.poke(operand1.S)
            dut.io.in_B.poke(operand2.S)
            dut.io.in_op.poke(0.U)
            dut.io.out_res1.expect((operand1 + operand2).S)
        }
    }
    "subtract" + " ALU should provide proper result regarding the operations and operands" in {
        test(new ALU(dataWidth = 32)) { dut =>
            dut.io.in_A.poke(operand1.S)
            dut.io.in_B.poke(operand2.S)
            dut.io.in_op.poke(1.U)
            dut.io.out_res1.expect((operand1 - operand2).S)
        }
    }
    "logical and(&)" + " ALU should provide proper result regarding the operations and operands" in {
        test(new ALU(dataWidth = 32)) { dut =>
            dut.io.in_A.poke(operand1.S)
            dut.io.in_B.poke(operand2.S)
            dut.io.in_op.poke(2.U)
            dut.io.out_res1.expect((operand1 & operand2).S)
        }
    }
    "logical or(|)" + " ALU should provide proper result regarding the operations and operands" in {
        test(new ALU(dataWidth = 32)) { dut =>
            dut.io.in_A.poke(operand1.S)
            dut.io.in_B.poke(operand2.S)
            dut.io.in_op.poke(3.U)
            dut.io.out_res1.expect((operand1 | operand2).S)
        }
    }
    "logical xor(^)" + " ALU should provide proper result regarding the operations and operands" in {
        test(new ALU(dataWidth = 32)) { dut =>
            dut.io.in_A.poke(operand1.S)
            dut.io.in_B.poke(operand2.S)
            dut.io.in_op.poke(4.U)
            dut.io.out_res1.expect((operand1 ^ operand2).S)
        }
    }
    "logical not(~)" + " ALU should provide proper result regarding the operations and operands" in {
        test(new ALU(dataWidth = 32)) { dut =>
            dut.io.in_A.poke(operand1.S)
            dut.io.in_B.poke(operand2.S)
            dut.io.in_op.poke(5.U)
            dut.io.out_res1.expect((-1 - operand1).S)
        }
    }
    "2's complement" + " ALU should provide proper result regarding the operations and operands" in {
        test(new ALU(dataWidth = 32)) { dut =>
            dut.io.in_A.poke(operand1.S)
            dut.io.in_B.poke(operand2.S)
            dut.io.in_op.poke(6.U)
            dut.io.out_res1.expect((0 - operand1).S)
        }
    }
}
*/