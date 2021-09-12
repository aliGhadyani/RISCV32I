package RISCV32I

import chisel3._
import chisel3.util._

class Core extends Module {
      val io      = IO(new Bundle{
            val start   = Input(Bool())
      })
    
      val pcr     = Module(new PCR())                             // program counter
      val im      = Module(new InstMem())              // instruction memory
      val rf      = Module(new RegFile())                         // Registeer File
      val alu     = Module(new ALU())                             // ALU
      val bu      = Module(new BranchUnit())                      // branch unit
      val cu      = Module(new ControlUnit())                     // Control Unit
      val mem     = Module(new Memory(bytes = 1024))              // Data Memory

      im.io.in_adr      := pcr.io.out_pc(11,0) 
      val inst    = Wire(UInt(32.W))
      inst              := im.io.out_inst

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

      pcr.io.in_npc     := Mux(((bu.io.out_branch & cu.io.out_ctrl(12)) | cu.io.out_ctrl(11)), 
                                    pcr.io.out_pc + 4.U, imm + Mux(cu.io.out_ctrl(13), pcr.io.out_pc, rf.io.data_out1))
      pcr.io.in_pause   := false.B

      rf.io.read_adr1   := inst(19, 15)
      rf.io.read_adr2   := inst(24, 20)
      rf.io.write_adr   := inst(11, 7)
      rf.io.wr_en       := cu.io.out_ctrl(15)
      rf.io.data_in     := Mux(cu.io.out_ctrl(16), alu.io.out_res.asUInt(), mem.io.out_w)

      alu.io.in_op      := cu.io.out_ctrl(2, 0)
      alu.io.in_op2     := cu.io.out_ctrl(3)
      alu.io.in_A       := MuxLookup(cu.io.out_ctrl(5, 4), 0.U,
                                    Array(
                                          0.U -> rf.io.data_out1,
                                          1.U -> pcr.io.out_pc,
                                          2.U -> 0.U,
                                          3.U -> 0.U
                                    )).asSInt()
      alu.io.in_B       := MuxLookup(cu.io.out_ctrl(7, 6), 0.U,
                                    Array(
                                          0.U -> rf.io.data_out2,
                                          1.U -> imm,
                                          2.U -> 4.U,
                                          3.U -> 0.U
                                    )).asSInt()

      bu.io.in_enable   := cu.io.out_ctrl(12)
      bu.io.in_func3    := inst(14, 12)
      bu.io.in_A        := rf.io.data_out1.asSInt()
      bu.io.in_B        := rf.io.data_out2.asSInt()

      mem.io.in_func    := inst(14, 12)
      mem.io.in_adr     := alu.io.out_res.asUInt()
      mem.io.in_w       := rf.io.data_out2
      mem.io.in_d       := 0.U
      mem.io.in_wr_en   := cu.io.out_ctrl(14)
}