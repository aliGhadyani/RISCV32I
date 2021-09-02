package RISCV32I

import chisel3._
import chisel3.util._

class DataPath extends Module {
    val io = IO(new Bundle{
        val in_pause    = Input(Bool())
        val in_ctrl     = Input(UInt(18.W))
        val in_inst     = Input(UInt(32.W))
        val in_inst_wr  = Input(Bool())

        val out_inst    = Output(UInt(32.W))
    })

    val im  = Module(new Memory(bytes = 1024))              // instruction memory
    val pcr = Module(new PCR())                             // program counter
    val ifid= Module(new IFID())                            // IF/ID register
    val rf  = Module(new RegFile())                         // Registeer File
    val hu  = Module(new HazardUnit())                      // Hazard Unit
    val idex= Module(new IDEX())                            // ID/EX register
    val alu = Module(new ALU())                             // ALU
    val bu  = Module(new BranchUnit())                      // branch unit
    val fu  = Module(new ForwardingUnit())                  // forwarding unit
    val exm = Module(new EXM())                             // EX/M register
    val mem = Module(new Memory(bytes = 1024))              // data memory
    val mwb = Module(new MWB())                             // M/WB register

    val m_d       = MuxLookup(exm.io.out_M(2, 1), 0.U,      // Mux for select RF data in
                            Array(0.U -> mem.io.out_w,
                                  1.U -> exm.io.out_alu_res,
                                  2.U -> exm.io.out_Rs2_val))
    val ex_a      = MuxLookup(fu.io.out_slc_A, 0.U,         // Mux for select forwarded data
                            Array(0.U -> idex.io.out_A,
                                  1.U -> m_d,
                                  2.U -> mwb.io.out_data))
    val ex_b      = MuxLookup(fu.io.out_slc_B, 0.U,         // Mux for select forwarded data
                            Array(0.U -> idex.io.out_B,
                                  1.U -> m_d,
                                  2.U -> mwb.io.out_data))
    val id_imm    = MuxLookup(io.in_ctrl(4, 2), 0.U,
                            Array(0.U -> Cat(ifid.io.out_inst(31, 12), 0.U(12.W)),                          // U-type
                                  1.U -> Cat(Fill(20, ifid.io.out_inst(31)), ifid.io.out_inst(31, 20)),     // I-type
                                  2.U -> Cat(Fill(20, "b0".U), ifid.io.out_inst(31, 20)),                   // I-type unsigned
                                  3.U -> Cat(Fill(21, ifid.io.out_inst(31)), ifid.io.out_inst(7),           // B-type
                                             ifid.io.out_inst(30, 25), ifid.io.out_inst(11, 8)),
                                  4.U -> Cat(Fill(21, "b0".U), ifid.io.out_inst(7),                         // B-type unsigned
                                             ifid.io.out_inst(30, 25), ifid.io.out_inst(11, 8)),
                                  5.U -> Cat(Fill(20, ifid.io.out_inst(31)), ifid.io.out_inst(31, 25),      // S-type
                                             ifid.io.out_inst(11, 7)),
                                  6.U -> Cat(Fill(12, ifid.io.out_inst(31)), ifid.io.out_inst(19, 12),      // J-type
                                             ifid.io.out_inst(20), ifid.io.out_inst(30, 21)) ))
    val pc_iorr   = Mux(idex.io.out_ctrl(0), idex.io.out_I, ex_a)
    val pc_jump   = idex.io.out_pc + pc_iorr
    val npc       = Mux(bu.io.out_branch | io.in_ctrl(5) , pcr.io.out_pc + 4.U, pc_jump)
    // IF stage
    pcr.io.in_npc       := npc
    im.io.in_adr        := pcr.io.out_pc
    im.io.in_wr_en      := io.in_inst_wr
    im.io.in_w          := io.in_inst
    im.io.in_d          := 0.U(32.W)
    im.io.in_func       := 2.U

    ifid.io.in_nop      := "b00000000000000000000000000010011".U
    ifid.io.in_inst     := im.io.out_w
    ifid.io.in_pc       := pcr.io.out_pc
    ifid.io.in_stall    := hu.io.out_stall
    ifid.io.in_kill     := hu.io.out_kill

    // ID stage
    io.out_inst   := ifid.io.out_inst

    rf.io.read_adr1 := ifid.io.out_inst(19, 15)
    rf.io.read_adr2 := ifid.io.out_inst(24, 20)

    idex.io.in_A    := rf.io.data_out1
    idex.io.in_B    := rf.io.data_out2
    idex.io.in_I    := id_imm
    idex.io.in_pc   := ifid.io.out_pc
    idex.io.in_Rs1  := ifid.io.out_inst(19, 15)
    idex.io.in_Rs2  := ifid.io.out_inst(24, 20)
    idex.io.in_Rd   := ifid.io.out_inst(11, 7)
    idex.io.in_func3:= ifid.io.out_inst(14, 12)
    idex.io.in_ctrl := io.in_ctrl

    hu.io.in_branch     := io.in_ctrl(1)
    hu.io.in_jump       := io.in_ctrl(5)

    // EX stage
    alu.io.in_op2   := idex.io.out_ctrl(14)
    alu.io.in_op    := idex.io.out_ctrl(17, 15)
    alu.io.in_A     := MuxLookup(idex.io.out_ctrl(11, 10), 0.U,
                                Array(0.U -> ex_a,
                                      1.U -> idex.io.out_pc,
                                      2.U -> 0.U)).asSInt()
    alu.io.in_B     := MuxLookup(idex.io.out_ctrl(13, 12), 0.U,
                                Array(0.U -> ex_b,
                                      1.U -> idex.io.out_I,
                                      2.U -> 4.U)).asSInt()

    bu.io.in_enable := idex.io.out_ctrl(1)
    bu.io.in_A      := ex_a.asSInt()
    bu.io.in_B      := ex_b.asSInt()
    bu.io.in_func3  := idex.io.out_func3

    fu.io.in_M_reg_wr   := exm.io.out_WB
    fu.io.in_M_reg_dst  := exm.io.out_Rd
    fu.io.in_WB_reg_wr  := mwb.io.out_WB
    fu.io.in_WB_reg_dst := mwb.io.out_Rd
    fu.io.in_EX_A_adr   := idex.io.out_Rs1
    fu.io.in_EX_B_adr   := idex.io.out_Rs2

    exm.io.in_M         := idex.io.out_ctrl(8, 6)
    exm.io.in_WB        := idex.io.out_ctrl(9)
    exm.io.in_alu_res   := alu.io.out_res.asUInt()
    exm.io.in_Rs2_val   := ex_b
    exm.io.in_Rd        := idex.io.out_Rd
    exm.io.in_func3     := idex.io.out_func3

    // M stage
    mem.io.in_adr   := exm.io.out_alu_res
    mem.io.in_w     := exm.io.out_Rs2_val
    mem.io.in_d     := 0.U(32.W)
    mem.io.in_func  := exm.io.out_func3
    mem.io.in_wr_en := exm.io.out_M(0)

    mwb.io.in_data  := m_d
    mwb.io.in_Rd    := exm.io.out_Rd
    mwb.io.in_WB    := exm.io.out_WB

    // WB stage
    rf.io.write_adr := mwb.io.out_Rd
    rf.io.data_in   := mwb.io.out_data
    rf.io.wr_en     := mwb.io.out_WB

    // pause signal
    ifid.io.in_pause    := io.in_pause
    idex.io.in_pause    := io.in_pause
    exm.io.in_pause     := io.in_pause
    mwb.io.in_pause     := io.in_pause
    pcr.io.in_pause     := io.in_pause
}