package RISCV32I

import chisel3._
import chisel3.util._

class AXIL_SLAVE extends Module {
    val io = IO(new Bundle{
        // write address channel
        val AWVALID = Input(Bool())
        val AWREADY = Output(Bool())
        val AWADDR  = Input(UInt(32.W))
        val AWPROT  = Input(UInt(3.W))

        // write data channel
        val WVALID  = Input(Bool())
        val WREADY  = Output(Bool())
        val WDATA   = Input(UInt(32.W))
        val WSTRB   = Input(UInt(4.W))

        // write response channel
        val BVALID  = Output(Bool())
        val BREADY  = Input(Bool())
        val BRESP   = Output(UInt(2.W))

        // read address channel
        val ARVALID = Input(Bool())
        val ARREADY = Output(Bool())
        val ARADDR  = Input(UInt(32.W))
        val ARPROT  = Input(UInt(3.W))

        // read data channel
        val RVALID  = Output(Bool())
        val RREADY  = Input(Bool())
        val RDATA   = Output(UInt(32.W))
        val RRESP   = Output(UInt(2.W))

        val in_invalid_wr   = Input(Bool())
        val in_ack_wr       = Input(Bool())
        val out_en_wr       = Output(Bool())
        val in_data     = Input(UInt(32.W))  //
        val out_data    = Output(UInt(32.W))
        val in_adr      = Input(UInt(32.W))
        val out_adr     = Output(UInt(32.W))
        val out_pause   = Output(Bool())

        val in_invalid_rd  = Input(Bool())
        val in_strb_rd  = Input(Bool())
        val out_en_rd   = Output(Bool())
        val rd_addr     = Output(UInt(32.W))
        val in_rd_data  = Input(UInt(32.W))

    })

    val wr_state    = RegInit(0.U(2.W))
    val rd_state    = RegInit(0.U(2.W))

    val rd_data     = RegInit(0.U(32.W))
    val rd_resp     = RegInit(0.U(2.W))
    val rd_valid    = RegInit(false.B)
    val rd_adr_rdy  = RegInit(false.B)
    val wr_adr_rdy  = RegInit(false.B)
    val wr_data_rdy = RegInit(false.B)
    val wr_resp_vld = RegInit(false.B)
    val wr_resp     = RegInit(0.U(2.W))

    io.RDATA    := rd_data
    io.RRESP    := rd_resp
    io.RVALID   := rd_valid
    io.ARREADY  := rd_adr_rdy
    io.AWREADY  := wr_adr_rdy
    io.WREADY   := wr_data_rdy
    io.BVALID   := wr_resp_vld
    io.BRESP    := wr_resp

    //Write Channel
    switch(wr_state) {
        is(0.U) {
            when(!wr_adr_rdy & !wr_data_rdy & io.AWVALID & io.WVALID) {
                wr_adr_rdy      := true.B
                wr_data_rdy     := true.B
                wr_state        := 1.U
                io.out_en_wr    := true.B
                io.out_data     := io.WDATA
                io.out_adr      := io.AWADDR
            } .otherwise {
                wr_adr_rdy      := false.B
                wr_data_rdy     := false.B      
            }
        }
        is(1.U) {
            wr_adr_rdy          := false.B
            wr_data_rdy         := false.B

            when(io.in_ack_wr & !wr_resp_vld) {
                wr_resp_vld     := true.B
                io.out_en_wr    := false.B
                wr_state        := 2.U
                
                when(io.in_invalid_wr) {
                    wr_resp     := 3.U
                } .otherwise {
                    wr_resp     := 0.U
                }
            }

        }
        is(2.U) {
            when(io.BREADY & wr_resp_vld) {
                wr_resp_vld     := false.B
                wr_state        := 0.U
            }
        }
    }

    //Read Channel
    switch(rd_state){
        is(0.U){
            when(!rd_adr_rdy & io.ARVALID){
                rd_adr_rdy    := true.B
                io.out_en_rd  := true.B
                io.rd_addr    := io.ARADDR
                rd_state      := 1.U
            }
        }

        is(1.U){
            rd_adr_rdy := false.B

            when(io.in_strb_rd & !rd_valid){
                rd_valid      := true.B
                rd_data       := io.in_rd_data
                io.out_en_rd  := false.B
                rd_state      := 2.U  

                when(io.in_invalid_rd){
                    rd_resp := 3.U
                } .otherwise{
                    rd_resp := 0.U
                }               
            }
        }

        is(2.U){
            when(io.RREADY & rd_valid){
                rd_valid := false.B
                rd_state := 0.U
            }
        }
    }

}