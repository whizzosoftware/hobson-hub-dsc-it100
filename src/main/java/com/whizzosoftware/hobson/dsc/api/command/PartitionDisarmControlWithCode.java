package com.whizzosoftware.hobson.dsc.api.command;

import com.whizzosoftware.hobson.api.HobsonInvalidRequestException;

public class PartitionDisarmControlWithCode extends DSCCommand {
    public static final String ID = "040";

    private int partition;
    private String code;

    public PartitionDisarmControlWithCode(int partition, String code) throws HobsonInvalidRequestException {
        super(ID);

        if (partition < 1 || partition > 8) {
            throw new HobsonInvalidRequestException("Partition must be 1-8");
        }

        this.partition = partition;
        this.code = code;

        while (this.code.length() < 6) {
            this.code = this.code + "0";
        }
    }

    @Override
    public String getDataString() {
        return partition + code;
    }
}
