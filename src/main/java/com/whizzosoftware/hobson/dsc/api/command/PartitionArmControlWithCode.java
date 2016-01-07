/*******************************************************************************
 * Copyright (c) 2016 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dsc.api.command;

import com.whizzosoftware.hobson.api.HobsonInvalidRequestException;

/**
 * This command arms a partition with a user code. This is identical to entering an access
 * code when a partition is in Ready mode.
 *
 * @author Dan Noguerol
 */
public class PartitionArmControlWithCode extends DSCCommand {
    public static final String ID = "033";

    private int partition;
    private String code;

    public PartitionArmControlWithCode(int partition, String code) throws HobsonInvalidRequestException {
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
