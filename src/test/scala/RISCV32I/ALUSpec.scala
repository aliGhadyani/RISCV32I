package RISCV32I

import chisel3._
import chisel3.tester._
import org.scalatest._
import scala.util.Random


class ALUSpec extends FreeSpec with ChiselScalatestTester {
    var operand1 = (Random.nextInt(200))
    var operand2 = (Random.nextInt(200))
    "ALU should provide proper result regarding the operations and operands" in {
        test(new ALU(dataWidth = 32)) { dut =>
            dut.io.in_A.poke(operand1.S)
            dut.io.in_B.poke(operand2.S)
            dut.io.in_op.poke(0.U)
            println("[test]   - test add operation with " + operand1.toString() + " + " + operand2.toString())
            println("[result] - " + dut.io.out_res1.peek().toString() + " expected " + (operand1 + operand2).toString())
            dut.io.out_res1.expect((operand1 + operand2).S)
        
            dut.io.in_A.poke(operand1.S)
            dut.io.in_B.poke(operand2.S)
            dut.io.in_op.poke(1.U)
            println("[test]   - test subtract operation with " + operand1.toString() + " - " + operand2.toString())
            println("[result] - " + dut.io.out_res1.peek().toString() + " expected " + (operand1 - operand2).toString())
            dut.io.out_res1.expect((operand1 - operand2).S)
        
            dut.io.in_A.poke(operand1.S)
            dut.io.in_B.poke(operand2.S)
            dut.io.in_op.poke(2.U)
            println("[test]   - test logical and(&) operation with " + operand1.toString() + " & " + operand2.toString())
            println("[result] - " + dut.io.out_res1.peek().toString() + " expected " + (operand1 & operand2).toString())
            dut.io.out_res1.expect((operand1 & operand2).S)
        
            dut.io.in_A.poke(operand1.S)
            dut.io.in_B.poke(operand2.S)
            dut.io.in_op.poke(3.U)
            println("[test]   - test logical or(|) operation with " + operand1.toString() + " | " + operand2.toString())
            println("[result] - " + dut.io.out_res1.peek().toString() + " expected " + (operand1 | operand2).toString())
            dut.io.out_res1.expect((operand1 | operand2).S)
        
            dut.io.in_A.poke(operand1.S)
            dut.io.in_B.poke(operand2.S)
            dut.io.in_op.poke(4.U)
            println("[test]   - test logical xor operation with " + operand1.toString() + " ^ " + operand2.toString())
            println("[result] - " + dut.io.out_res1.peek().toString() + " expected " + (operand1 ^ operand2).toString())
            dut.io.out_res1.expect((operand1 ^ operand2).S)
        
            dut.io.in_A.poke(operand1.S)
            dut.io.in_B.poke(operand2.S)
            dut.io.in_op.poke(5.U)
            println("[test]   - test logical not(~) operation with ~" + operand1.toString())
            println("[result] - " + dut.io.out_res1.peek().toString() + " expected " + (~operand1).toString())
            dut.io.out_res1.expect((-1 - operand1).S)
        
            dut.io.in_A.poke(operand1.S)
            dut.io.in_B.poke(operand2.S)
            dut.io.in_op.poke(6.U)
            println("[test]   - test 2's complement operation with -" + operand1.toString())
            println("[result] - " + dut.io.out_res1.peek().toString() + " expected " + (-operand1).toString())
            dut.io.out_res1.expect((0 - operand1).S)
        }
    }
}