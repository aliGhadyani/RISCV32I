package RISCV32I

import chisel3._
import chisel3.tester._
import org.scalatest._
import scala.util.Random


class ALUSpec extends FreeSpec with ChiselScalatestTester {
    var operand1 = 746
    var operand2 = 4
    "ALU should provide proper result regarding the operations and operands" in {
        test(new ALU(data_width = 32)) { dut =>
            dut.io.in_A.poke(operand1.S)
            dut.io.in_B.poke(operand2.S)
            dut.io.in_op.poke(0.U)
            dut.io.in_op2.poke(false.B)
            println("[test]   - test add operation with " + operand1.toString() + " + " + operand2.toString())
            println("[result] - " + dut.io.out_res.peek().toString() + " expected " + (operand1 + operand2).toString())
            dut.io.out_res.expect((operand1 + operand2).S)
        
            dut.io.in_A.poke(operand1.S)
            dut.io.in_B.poke(operand2.S)
            dut.io.in_op.poke(0.U)
            dut.io.in_op2.poke(true.B)
            println("[test]   - test subtract operation with " + operand1.toString() + " - " + operand2.toString())
            println("[result] - " + dut.io.out_res.peek().toString() + " expected " + (operand1 - operand2).toString())
            dut.io.out_res.expect((operand1 - operand2).S)
        
            dut.io.in_A.poke(operand1.S)
            dut.io.in_B.poke(operand2.S)
            dut.io.in_op.poke(1.U)
            dut.io.in_op2.poke(false.B)
            println("[test]   - test logical shift left operation with " + operand1.toString() + " << " + operand2.asUInt().toString())
            println("[result] - " + dut.io.out_res.peek().toString() + " expected " + (operand1 << operand2).toString())
            dut.io.out_res.expect((operand1 << operand2).S)
        
            dut.io.in_A.poke(operand1.S)
            dut.io.in_B.poke(operand2.S)
            dut.io.in_op.poke(2.U)
            dut.io.in_op2.poke(false.B)
            println("[test]   - test compare(<) operation with " + operand1.toString() + " < " + operand2.toString())
            println("[result] - " + dut.io.out_res.peek().toString() + " expected " + (operand1 < operand2).toString())
            dut.io.out_res.expect((operand1.U < operand2.U).asSInt())
        
            dut.io.in_A.poke(operand1.S)
            dut.io.in_B.poke(operand2.S)
            dut.io.in_op.poke(3.U)
            dut.io.in_op2.poke(false.B)
            println("[test]   - test compare(<) operation as UInt with " + operand1.asUInt().toString() + " < " + operand2.asUInt().toString())
            println("[result] - " + dut.io.out_res.peek().toString() + " expected " + (operand1.asUInt() < operand2.asUInt()))
            dut.io.out_res.expect((operand1.asUInt() < operand2.asUInt()).asSInt())
        
            dut.io.in_A.poke(operand1.S)
            dut.io.in_B.poke(operand2.S)
            dut.io.in_op.poke(4.U)
            dut.io.in_op2.poke(false.B)
            println("[test]   - test logical xor(^) operation with " + operand1.toString() + " ^ " + operand2.toString())
            println("[result] - " + dut.io.out_res.peek().toString() + " expected " + (operand1 ^ operand2).toString())
            dut.io.out_res.expect((operand1 ^ operand2).asSInt())
        
            dut.io.in_A.poke(operand1.S)
            dut.io.in_B.poke(operand2.S)
            dut.io.in_op.poke(5.U)
            dut.io.in_op2.poke(false.B)
            println("[test]   - test shift right logical operation with -" + operand1.asUInt().toString() + " >> " + operand2.asUInt())
            println("[result] - " + dut.io.out_res.peek().toString() + " expected " + (operand1.asUInt() >> operand2.asUInt()).toString())
            dut.io.out_res.expect((operand1.asUInt() >> operand2.asUInt()).asSInt())

            dut.io.in_A.poke(operand1.S)
            dut.io.in_B.poke(operand2.S)
            dut.io.in_op.poke(5.U)
            dut.io.in_op2.poke(true.B)
            println("[test]   - test shift right arithmatical operation with -" + operand1.toString() + " >> " + operand2.asUInt())
            println("[result] - " + dut.io.out_res.peek().toString() + " expected " + (operand1.S >> operand2.asUInt()).toString())
            dut.io.out_res.expect((operand1.S >> operand2.asUInt()).asSInt())

            dut.io.in_A.poke(operand1.S)
            dut.io.in_B.poke(operand2.S)
            dut.io.in_op.poke(6.U)
            dut.io.in_op2.poke(false.B)
            println("[test]   - test logical or(|) operation with " + operand1.toString() + " | " + operand2.toString())
            println("[result] - " + dut.io.out_res.peek().toString() + " expected " + (operand1 | operand2).toString())
            dut.io.out_res.expect((operand1 | operand2).S)

            dut.io.in_A.poke(operand1.S)
            dut.io.in_B.poke(operand2.S)
            dut.io.in_op.poke(7.U)
            dut.io.in_op2.poke(false.B)
            println("[test]   - test logical and(&) operation with " + operand1.toString() + " & " + operand2.toString())
            println("[result] - " + dut.io.out_res.peek().toString() + " expected " + (operand1 & operand2).toString())
            dut.io.out_res.expect((operand1 & operand2).S)
        }
    }
}