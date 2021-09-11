package RISCV32I

import chisel3._
import chisel3.util._
import chisel3.experimental.loadMemoryFromFile
import chisel3.experimental.MemoryLoadFileType 

class Core extends Module {
    
      val pcr     = Module(new PCR())                             // program counter
      val im      = Mem(1024, UInt(32.W))                         // instruction memory
      val rf      = Module(new RegFile())                         // Registeer File
      val alu     = Module(new ALU())                             // ALU
      val bu      = Module(new BranchUnit())                      // branch unit
      val cu      = Module(new ControlUnit())                     // Control Unit
      val mem     = Mem(1024, Vec(4, UInt(8.W)))                  // Data Memory

      loadMemoryFromFile(im, "C:/Users/Ghadyani/OneDrive/Desktop/im.bin", hexOrBinary = MemoryLoadFileType.Binary)
      val inst    = Wire(UInt(32.W))
      inst              := im.read(pcr.io.out_pc)

      cu.io.in_inst     := inst

      val imm     = MuxLookup(cu.io.out_ctrl(10, 8), 0.U,
                              Array(0.U -> Cat(Fill(20, inst(31)), inst(31, 20)),                           // I-type
                                    1.U -> Cat(Fill(20, "b0".U), inst(31, 20)),                             // I-type unsigned
                                    2.U -> Cat(Fill(20, inst(31)), inst(31, 25), inst(11, 7)),              // S-type
                                    3.U -> Cat(Fill(21, inst(31)), inst(7), inst(30, 25), inst(11, 8)),     // B-type
                                    4.U -> Cat(Fill(21, "b0".U), inst(7), inst(30, 25), inst(11, 8)),       // B-type unsigned
                                    5.U -> Cat(inst(31, 12), Fill(12, "b0".U)),                             // U-type 
                                    6.U -> Cat(Fill(13, inst(31)), inst(19, 12), inst(20), inst(30, 21)),   // J-type
                                    7.U -> 0.U                                                              // default-reserved
                              ))

      when(cu.io.out_ctrl(14)) {
            switch(inst(14, 12)) {
                  is("b000".U) { mem.write(alu.io.out_res.asUInt(), rf.io.data_out2, "b0001".U) }     // STORE byte
                  is("b001".U) { mem.write(alu.io.out_res.asUInt(), rf.io.data_out2, "b0011".U) }     // STORE half-word
                  is("b010".U) { mem.write(alu.io.out_res.asUInt(), rf.io.data_out2, "b1111".U) }     // STORE word
            }
      }
      val mem_out = MuxLookup(inst(14, 12), 0.U,
                              Array(
                                    0.U -> Cat(Fill(24, mem.read(alu.io.out_res.asUInt())(7)), mem.read(alu.io.out_res.asUInt())(7, 0)),        // LOAD byte
                                    1.U -> Cat(Fill(16, mem.read(alu.io.out_res.asUInt())(15)), mem.read(alu.io.out_res.asUInt())(15, 0)),      // LOAD half-word
                                    2.U -> mem.read(alu.io.out_res.asUInt()),                                                                   // LOAD word
                                    4.U -> Cat(Fill(24, "b0".U), mem.read(alu.io.out_res.asUInt())(7, 0)),                                      // LOAD byte unsigned
                                    5.U -> Cat(Fill(16, "b0".U), mem.read(alu.io.out_res.asUInt())(15, 0))                                      // LOAD half-word unsigned
                              ))

      pcr.io.in_npc     := Mux(((bu.io.out_branch & cu.io.out_ctrl(12)) | cu.io.out_ctrl(11)), 
                                    pcr.io.out_pc + 4.U, imm + Mux(cu.io.out_ctrl(13), pcr.io.out_pc, rf.io.data_out1))

      rf.io.read_adr1   := inst(19, 15)
      rf.io.read_adr2   := inst(24, 20)
      rf.io.write_adr   := inst(11, 7)
      rf.io.wr_en       := cu.io.out_ctrl(15)
      rf.io.data_in     := Mux(cu.io.out_ctrl(16), alu.io.out_res, mem_out)

      alu.io.in_op      := cu.io.out_ctrl(2, 0)
      alu.io.in_op2     := cu.io.out_ctrl(3)
      alu.io.in_A       := MuxLookup(cu.io.out_ctrl(5, 4), 0.U,
                                    Array(
                                          0.U -> rf.io.data_out1,
                                          1.U -> pcr.io.out_pc,
                                          2.U -> 0.U,
                                          3.U -> 0.U
                                    ))
      alu.io.in_B       := MuxLookup(cu.io.out_ctrl(7, 6), 0.U,
                                    Array(
                                          0.U -> rf.io.data_out2,
                                          1.U -> imm,
                                          2.U -> 4.U,
                                          3.U -> 0.U
                                    ))

      bu.io.in_enable   := cu.io.out_ctrl(12)
      bu.io.in_func3    := inst(14, 12)
      bu.io.in_A        := rf.io.data_out1
      bu.io.in_B        := rf.io.data_out2
}