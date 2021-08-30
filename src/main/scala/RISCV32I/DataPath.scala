package RISCV32I

import chisel3._
import chisel3.util._

class DataPath(dataWidth: Int) extends Module {
    val io = IO(new Bundle{
        val in_pause    = Input(Bool())
        val in_kill     = Input(Bool())
        val in_stall    = Input(Bool())
        val in_jump     = Input(Bool())
        val in_f4       = Input(UInt(2.W))
        val in_M        = Input(MSig)
        val in_EX       = Input(EXSig)
        val in_WB       = Input(WBSig)
    })

    val im  = Module(new Memory(bytes = 1024))              // instruction memory
    val pcr = Module(new PCR(data_width = dataWidth))       // program counter
    val ifid= Module(new IFID())                            // IF/ID register
    val rf  = Module(new RegFile())                         // Registeer File
    val idex= Module(new IDEX())                            // ID/EX register
    val alu = Module(new ALU(data_width = dataWidth))       // ALU
    val fu  = Module(new ForwardingUnit())                  // forwarding unit
    val exm = Module(new EXM())                             // EX/M register
    val mem = Module(new Memory(bytes = 1024))              // data memory
    val mwb = Module(new MWB())                             // M/WB register
    val wb_d= Mux(mwb.io.out_WB.d_slc, mwb.io.out_alu_res   // Mux for select RF data input
                                     , mwb.io.out_mem_out)
    val ex_a = MuxLookup(fu.io.slc_A, 0.U,                  // Mux for select forwarded data
                            Array(0.U -> idex.io.out_A,
                                  1.U -> exm.io.out_alu_res,
                                  2.U -> wb_d))
    val ex_b = MuxLookup(fu.io.slc_B, 0.U,                  // Mux for select forwarded data
                            Array(0.U -> idex.io.out_B,
                                  1.U -> exm.io.out_alu_res,
                                  2.U -> wb_d))

    // IF stage
    im.io.in_adr    := pcr.io.out_pc
    ifid.io.in_inst := im.io.out_w
    ifid.io.in_pc   := pcr.io.out_pc + 4.U

    // ID stage
    rf.io.read_adr1 := ifid.io.out_inst(19, 15)
    rf.io.read_adr2 := ifid.io.out_inst(24, 20)
    rf.io.write_adr := mwb.io.out_Rd

    idex.io.in_A    := rf.io.data_out1
    idex.io.in_B    := rf.io.data_out2
    idex.io.in_Rs1  := ifid.io.out_inst(19, 15)
    idex.io.in_Rs1  := ifid.io.out_inst(24, 20)
    idex.io.in_Rd   := ifid.io.out_inst(11, 7)
    idex.io.in_func3:= ifid.io.out_inst(14, 12)
    idex.io.in_f4   := io.in_f4

    // EX stage
    alu.io.in_op2   := idex.io.out_f4(0)
    alu.io.in_op    := idex.io.out_func3
    alu.io.in_A     := MuxLookup(idex.io.out_EX.slc_A, 0.U,
                            Array(0.U -> ex_a))
    alu.io.in_B     := MuxLookup(idex.io.out_EX.slc_B, 0.U,
                            Array(0.U -> ex_b))

    exm.io.in_alu_res   := alu.io.out_res.asUInt()
    exm.io.in_Rs2_val   := ex_b

    // M stage
    mem.io.in_adr   := exm.io.out_alu_res
    mem.io.in_w     := exm.io.out_Rs2_val
    mem.io.in_d     := 0.U(32.W)
    mem.io.in_func  := exm.io.out_func
    mem.io.in_M     := exm.io.out_M

    mwb.io.in_mem_out   := mem.io.out_w
    mwb.io.in_alu_res   := exm.io.out_alu_res
    mwb.io.in_Rd        := exm.io.out_Rd
    mwb.io.in_WB        := exm.io.out_WB

    // WB stage
    rf.io.data_in   := wb_d
    rf.io.wr_en     := mwb.io.out_WB.wr_en
}