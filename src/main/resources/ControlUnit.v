module ControlUnit(
    input   [31:0] in_inst,
    output  [17:0] out_ctrl
);
    always @* begin
        switchcase(in_inst[6:0])
            b0110111 : out_ctrl = 18'b000_0_01_10_1_01_0_0_000_0_0;     // LUI
            b0010111 : out_ctrl = 18'b000_0_01_01_1_01_0_0_000_0_0;     // AUIPC
            b1101111 : out_ctrl = 18'b000_0_10_01_1_00_0_1_110_0_0;     // JAL
            b1101111 : out_ctrl = 18'b000_0_10_01_0_00_0_1_000_0_1;     // JALR
            b1101111 : begin                                            // BRANCH
                            if((in_inst[14:12] == 3'b111) | (in_inst[14:12] == b110))
                                out_ctrl = 18'b000_0_01_01_0_01_0_0_100_1_0;
                            else
                                out_ctrl = 18'b000_0_01_01_0_01_0_0_011_1_0;
                            end
                        end
            b1101111 : out_ctrl = 18'b000_0_01_00_1_00_0_0_001_0_0;     // LOAD
            b1101111 : out_ctrl = 18'b000_0_01_00_0_00_1_0_101_0_0;     // STORE
            b1101111 : begin                                            // OP-IMM
                            out_ctrl[17:15] = in_inst[14:12];
                            if(in_inst[14:12] == 3'b011)
                                out_ctrl[14:0] = 15'b0_01_00_1_01_0_0_010_0_0;
                            else if((in_inst[14:12] == 3'b101) & (in_inst[31:25] == 7'b0100000))
                                out_ctrl[14:0] = 15'b1_01_00_1_01_0_0_001_0_0;
                            else
                                out_ctrl[14:0] = 15'b0_01_00_1_01_0_0_001_0_0;
                            end
                        end
            b1101111 : begin                                            // OP
                            out_ctrl[17:15] = in_inst[14:12];
                            if(in_inst[31:25] == 7'b0100000)
                                out_ctrl[14:0] = 15'b1_00_00_1_01_0_0_001_0_0;
                            else
                                out_ctrl[14:0] = 15'b0_00_00_1_01_0_0_001_0_0;
                            end
                        end
            b1101111 : out_ctrl = 18'b000_0_00_00_0_01_0_0_001_0_0;     // MISC-MEM
            b1101111 : out_ctrl = 18'b000_0_00_00_0_01_0_0_001_0_0;     // SYSTEM
            default  : out_ctrl = 18'b000_0_00_00_0_01_0_0_001_0_0;     // NOP
        endcase
    end
endmodule

            