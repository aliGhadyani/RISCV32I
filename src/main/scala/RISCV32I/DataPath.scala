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

    val im  = Module(new Memory(bytes = 1024))
    val pcr = Module(new PCR(data_width = dataWidth))
    val ifid= Module(new IFID())
    val rf  = Module(new RegFile())
    val idex= Module(new IDEX())
    val alu = Module(new ALU(data_width = dataWidth))
    val exm = Module(new EXM())
    val mem = Module(new Memory(bytes = 1024))
    val mwb = Module(new MWB())

    im.io.in_adr    := pcr.io.out_pc
    ifid.io.in_inst := im.io.out_w
    ifid.io.in_pc   := pcr.io.out_pc + 4.U
    rf.io.read_adr1 := ifid.io.out_inst(19, 15)
    rf.io.read_adr2 := ifid.io.out_inst(24, 20)
    idex.io.in_A    := rf.io.data_out1
    idex.io.in_B    := rf.io.data_out2
    idex.io.in_Rs1  := ifid.io.out_inst(19, 15)
    idex.io.in_Rs1  := ifid.io.out_inst(24, 20)
    idex.io.in_Rd   := ifid.io.out_inst(11, 7)
    idex.io.in_func3:= ifid.io.out_inst(14, 12)
    idex.io.in_f4   := io.in_f4
    alu.io.in_op2   := idex.io.out_f4(0)

}