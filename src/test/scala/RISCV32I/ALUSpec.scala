package RISCV32I

import chisel3._
import chisel3.tester._
import org.scalatest._
import scala.util.Random

class ALUSpec extends FreeSpec with ChiselScalatestTester {
    var operand1 = Random.nextInt(200)
    var operand2 = Random.nextInt(100)
    "ALU should provide proper result regarding the operations and operands" in {
        test(new ALU()) { dut =>
            dut.io.in_A.poke(operand1.S)
            dut.io.in_B.poke(operand2.S)
            dut.io.in_op.poke(0.U)
            dut.io.in_op2.poke(false.B)
            println("[test01] - test add operation with " + operand1.toString() + " + " + operand2.toString())
            println("[result] - " + dut.io.out_res.peek().toString() + " expected " + (operand1 + operand2).toString())
            dut.io.out_res.expect((operand1 + operand2).S)
        
            dut.io.in_A.poke(operand1.S)
            dut.io.in_B.poke(operand2.S)
            dut.io.in_op.poke(0.U)
            dut.io.in_op2.poke(true.B)
            println("[test02] - test subtract operation with " + operand1.toString() + " - " + operand2.toString())
            println("[result] - " + dut.io.out_res.peek().toString() + " expected " + (operand1 - operand2).toString())
            dut.io.out_res.expect((operand1 - operand2).S)
        
            dut.io.in_A.poke(operand1.S)
            dut.io.in_B.poke(operand2.S)
            dut.io.in_op.poke(1.U)
            dut.io.in_op2.poke(false.B)
            println("[test03] - test logical shift left operation with " + operand1.toString() + " << " + operand2.toString())
            println("[result] - " + dut.io.out_res.peek().toString() + " expected " + (operand1 << operand2).toString())
            dut.io.out_res.expect((operand1 << operand2).S)
        
            dut.io.in_A.poke(operand1.S)
            dut.io.in_B.poke(operand2.S)
            dut.io.in_op.poke(3.U)
            dut.io.in_op2.poke(false.B)
            println("[test04] - test compare(<) operation with " + operand1.toString() + " < " + operand2.toString())
            println("[result] - " + dut.io.out_res.peek().toString() + " expected " + (operand1 < operand2).toString())
            if(operand1 < operand2) {
                dut.io.out_res.expect(1.S)
            } else {
                dut.io.out_res.expect(0.S)
            }
            
        
            dut.io.in_A.poke(operand1.S)
            dut.io.in_B.poke(operand2.S)
            dut.io.in_op.poke(3.U)
            dut.io.in_op2.poke(false.B)
            println("[test05] - test compare(<) operation as UInt with " + operand1.toLong.toString() + " < " + operand2.toLong.toString())
            println("[result] - " + dut.io.out_res.peek().toString() + " expected " + (operand1.toLong < operand2.toLong).toString())
            if(operand1.toLong < operand2.toLong) {
                dut.io.out_res.expect(1.S)
            } else {
                dut.io.out_res.expect(0.S)
            }
        
            dut.io.in_A.poke(operand1.S)
            dut.io.in_B.poke(operand2.S)
            dut.io.in_op.poke(4.U)
            dut.io.in_op2.poke(false.B)
            println("[test06] - test logical xor(^) operation with " + operand1.toString() + " ^ " + operand2.toString())
            println("[result] - " + dut.io.out_res.peek().toString() + " expected " + (operand1 ^ operand2).toString())
            dut.io.out_res.expect((operand1 ^ operand2).S)
        
            dut.io.in_A.poke(operand1.S)
            dut.io.in_B.poke(operand2.S)
            dut.io.in_op.poke(5.U)
            dut.io.in_op2.poke(false.B)
            println("[test07] - test shift right logical operation with " + operand1.toString() + " >> " + operand2.toString())
            println("[result] - " + dut.io.out_res.peek().toString() + " expected " + (operand1 >> operand2).toString())
            dut.io.out_res.expect((operand1 >> operand2).asSInt())

            dut.io.in_A.poke(operand1.S)
            dut.io.in_B.poke(operand2.S)
            dut.io.in_op.poke(5.U)
            dut.io.in_op2.poke(true.B)
            println("[test08] - test shift right arithmatical operation with " + operand1.toString() + " >> " + operand2.toString())
            println("[result] - " + dut.io.out_res.peek().toString() + " expected " + (operand1 >> operand2).toString())
            dut.io.out_res.expect((operand1 >> operand2).asSInt())

            dut.io.in_A.poke(operand1.S)
            dut.io.in_B.poke(operand2.S)
            dut.io.in_op.poke(6.U)
            dut.io.in_op2.poke(false.B)
            println("[test09] - test logical or(|) operation with " + operand1.toString() + " | " + operand2.toString())
            println("[result] - " + dut.io.out_res.peek().toString() + " expected " + (operand1 | operand2).toString())
            dut.io.out_res.expect((operand1 | operand2).S)

            dut.io.in_A.poke(operand1.S)
            dut.io.in_B.poke(operand2.S)
            dut.io.in_op.poke(7.U)
            dut.io.in_op2.poke(false.B)
            println("[test10] - test logical and(&) operation with " + operand1.toString() + " & " + operand2.toString())
            println("[result] - " + dut.io.out_res.peek().toString() + " expected " + (operand1 & operand2).toString())
            dut.io.out_res.expect((operand1 & operand2).S(32.W))
        }
    }
}