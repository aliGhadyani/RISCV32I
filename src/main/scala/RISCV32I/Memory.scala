package RISCV32I

import chisel3._
import chisel3.util._ 
import scala.annotation.switch

class Memory(bytes: Int) extends Module {
    val io = IO(new Bundle {
        val in_func = Input(UInt(3.W))
        val in_adr  = Input(UInt(32.W))
        val in_w    = Input(UInt(32.W))
        val in_d    = Input(UInt(32.W))
        val in_M    = Input(new MSig())
        val out_w   = Output(UInt(32.W))
        val out_d   = Output(UInt(32.W))
    })
    val memFile = Mem(bytes, UInt(8.W))

    switch(io.in_func){
        is(0.U) {
            io.out_w    := Cat(Fill(24, memFile(io.in_adr)(7)), memFile(io.in_adr))
            io.out_d    := Fill(32, memFile(io.in_adr)(7))
        }
        is(1.U) {
            io.out_w    := Cat(Fill(16, memFile(io.in_adr)(7)), memFile(io.in_adr), memFile(io.in_adr + 1.U))
            io.out_d    := Fill(32, memFile(io.in_adr)(7))
        }
        is(2.U) {
            io.out_w    := Cat(memFile(io.in_adr), memFile(io.in_adr + 1.U)
                                , memFile(io.in_adr + 2.U), memFile(io.in_adr + 3.U))
            io.out_d    := Fill(32, memFile(io.in_adr)(7))
        }
        is(3.U) {
            io.out_w    := Cat(memFile(io.in_adr), memFile(io.in_adr + 1.U)
                                 , memFile(io.in_adr + 2.U), memFile(io.in_adr + 3.U))
            io.out_d    := Cat(memFile(io.in_adr + 4.U), memFile(io.in_adr + 5.U)
                                , memFile(io.in_adr + 6.U), memFile(io.in_adr + 7.U))
        }
        is(4.U) {
            io.out_w    := Cat(Fill(24, "b0".U), memFile(io.in_adr))
            io.out_d    := Fill(32, "b0".U)
        }
        is(5.U) {
            io.out_w    := Cat(Fill(16, "b0".U), memFile(io.in_adr), memFile(io.in_adr + 1.U))
            io.out_d    := Fill(32, "b0".U)
        }
        is(6.U) {
            io.out_w    := Cat(memFile(io.in_adr), memFile(io.in_adr + 1.U)
                                , memFile(io.in_adr + 2.U), memFile(io.in_adr + 3.U))
            io.out_d    := Fill(32, "b0".U)
        }
        is(7.U) {
            io.out_w    := Cat(memFile(io.in_adr), memFile(io.in_adr + 1.U)
                                , memFile(io.in_adr + 2.U), memFile(io.in_adr + 3.U))
            io.out_d    := Cat(memFile(io.in_adr + 4.U), memFile(io.in_adr + 5.U)
                                , memFile(io.in_adr + 6.U), memFile(io.in_adr + 7.U))
        }
    }

    when(io.in_M.wr_en) {
        switch(io.in_func){
            is(0.U) {
                memFile(io.in_adr)  := io.in_w(7, 0)
            }
            is(1.U) {
                memFile(io.in_adr)  := io.in_w(15, 8)
                memFile(io.in_adr + 1.U)  := io.in_w(7, 0)
            }
            is(2.U) {
                memFile(io.in_adr)  := io.in_w(31, 25)
                memFile(io.in_adr + 1.U)  := io.in_w(24, 17)
                memFile(io.in_adr + 2.U)  := io.in_w(15, 8)
                memFile(io.in_adr + 3.U)  := io.in_w(7, 0)
            }
            is(3.U) {
                memFile(io.in_adr)  := io.in_d(31, 25)
                memFile(io.in_adr + 1.U)  := io.in_d(24, 17)
                memFile(io.in_adr + 2.U)  := io.in_d(15, 8)
                memFile(io.in_adr + 3.U)  := io.in_d(7, 0)
                memFile(io.in_adr + 4.U)  := io.in_w(31, 25)
                memFile(io.in_adr + 5.U)  := io.in_w(24, 17)
                memFile(io.in_adr + 6.U)  := io.in_w(15, 8)
                memFile(io.in_adr + 7.U)  := io.in_w(7, 0)
            }
            is(4.U) {
                memFile(io.in_adr)  := io.in_w(7, 0)
            }
            is(5.U) {
                memFile(io.in_adr)  := io.in_w(15, 8)
                memFile(io.in_adr + 1.U)  := io.in_w(7, 0)
            }
            is(6.U) {
                memFile(io.in_adr)  := io.in_w(31, 25)
                memFile(io.in_adr + 1.U)  := io.in_w(24, 17)
                memFile(io.in_adr + 2.U)  := io.in_w(15, 8)
                memFile(io.in_adr + 3.U)  := io.in_w(7, 0)
            }
            is(7.U) {
                memFile(io.in_adr)  := io.in_d(31, 25)
                memFile(io.in_adr + 1.U)  := io.in_d(24, 17)
                memFile(io.in_adr + 2.U)  := io.in_d(15, 8)
                memFile(io.in_adr + 3.U)  := io.in_d(7, 0)
                memFile(io.in_adr + 4.U)  := io.in_w(31, 25)
                memFile(io.in_adr + 5.U)  := io.in_w(24, 17)
                memFile(io.in_adr + 6.U)  := io.in_w(15, 8)
                memFile(io.in_adr + 7.U)  := io.in_w(7, 0)
            }
        }
    }
}