package RISCV32I

import chisel3._
import chisel3.util._
import scala.annotation.switch

class Core extends Module {
      val io      = IO(new Bundle{
            val start   = Input(Bool())
            val pc      = Output(UInt(32.W))
            val rf_in   = Output(UInt(32.W))
            val rf_adr  = Output(UInt(5.W))
            val mem_in  = Output(UInt(32.W))
            val mem_adr = Output(UInt(32.W))
      })
      val pcr     = Module(new PCR())                             // program counter
      val im      = Module(new InstMem())                         // instruction memory
      val rf      = Module(new RegFile())                         // Registeer File
      val alu     = Module(new ALU())                             // ALU
      val bu      = Module(new BranchUnit())                      // branch unit
      val cu      = Module(new ControlUnit())                     // Control Unit
      val mem     = Module(new Memory(bytes = 1024))              // Data Memory

      im.io.in_adr      := pcr.io.out_pc(11,0) 
      val inst    = Wire(UInt(32.W))
      inst              := im.io.out_inst

      cu.io.in_inst     := inst

      val imm     = Wire(UInt(32.W))
      imm   := 0.U
      switch(cu.io.out_ctrl(10, 8)) {
            is(0.U) { imm := Cat(Fill(20, inst(31)), inst(31, 20)) }                            // I-type
            is(1.U) { imm := Cat(Fill(20, "b0".U), inst(31, 20)) }                              // I-type unsigned
            is(2.U) { imm := Cat(Fill(20, inst(31)), inst(31, 25), inst(11, 7)) }               // S-type
            is(3.U) { imm := Cat(Fill(21, inst(31)), inst(7), inst(30, 25), inst(11, 8)) }      // B-type
            is(4.U) { imm := Cat(Fill(21, "b0".U), inst(7), inst(30, 25), inst(11, 8)) }        // B-type unsigned
            is(5.U) { imm := Cat(inst(31, 12), Fill(12, "b0".U)) }                              // U-type 
            is(6.U) { imm := Cat(Fill(13, inst(31)), inst(19, 12), inst(20), inst(30, 21)) }    // J-type
            is(7.U) { imm := 0.U}                                                               // default-reserved
      }

      val wb_data = Wire(UInt(32.W))
      wb_data     := 0.U
      when(cu.io.out_ctrl(16)) {
            wb_data     := mem.io.out_w
      } .otherwise {
            wb_data     := alu.io.out_res.asUInt()
      }

      when((bu.io.out_branch & cu.io.out_ctrl(12)) | cu.io.out_ctrl(11)) {
            when(cu.io.out_ctrl(13)) {
                  pcr.io.in_npc     := imm + rf.io.data_out1
            } .otherwise {
                  pcr.io.in_npc     := imm + pcr.io.out_pc
            }
      }.otherwise {
            pcr.io.in_npc     := pcr.io.out_pc + 4.U
      }

      pcr.io.in_pause   := false.B

      rf.io.read_adr1   := inst(19, 15)
      rf.io.read_adr2   := inst(24, 20)
      rf.io.write_adr   := inst(11, 7)
      rf.io.wr_en       := cu.io.out_ctrl(15)
      rf.io.data_in     := wb_data

      alu.io.in_op      := cu.io.out_ctrl(2, 0)
      alu.io.in_op2     := cu.io.out_ctrl(3)
      alu.io.in_A       := 0.S
      switch(cu.io.out_ctrl(5, 4)) {
            is(0.U) { alu.io.in_A   := rf.io.data_out1.asSInt() }
            is(1.U) { alu.io.in_A   := pcr.io.out_pc.asSInt() }
            is(2.U) { alu.io.in_A   := 0.S }
            is(3.U) { alu.io.in_A   := 0.S }
      }
      alu.io.in_B       := 0.S
      switch(cu.io.out_ctrl(7, 6)) {
            is(0.U) { alu.io.in_A   := rf.io.data_out2.asSInt() }
            is(1.U) { alu.io.in_A   := imm.asSInt() }
            is(2.U) { alu.io.in_A   := 4.S }
            is(3.U) { alu.io.in_A   := 0.S }
      }
      
      bu.io.in_enable   := cu.io.out_ctrl(12)
      bu.io.in_func3    := inst(14, 12)
      bu.io.in_A        := rf.io.data_out1.asSInt()
      bu.io.in_B        := rf.io.data_out2.asSInt()

      mem.io.in_func    := inst(14, 12)
      mem.io.in_adr     := alu.io.out_res.asUInt()
      mem.io.in_w       := rf.io.data_out2
      mem.io.in_d       := 0.U
      mem.io.in_wr_en   := cu.io.out_ctrl(14)

      io.rf_adr   := inst(11, 7)
      io.rf_in    := wb_data
      io.mem_in   := rf.io.data_out2
      io.mem_adr  := alu.io.out_res.asUInt()
      io.pc       := pcr.io.out_pc
}