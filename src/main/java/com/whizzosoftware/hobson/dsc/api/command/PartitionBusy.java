/*******************************************************************************
 * Copyright (c) 2016 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dsc.api.command;

/**
 * This command indicates that a partition has been armed by a user - sent at the end of exit delay.
 *
 * @author Dan Noguerol
 */
public class PartitionBusy extends DSCCommand {
    public static final String ID = "673";

    private int partition;

    public PartitionBusy(int partition) {
        super(ID);
        this.partition = partition;
    }

    public int getPartition() {
        return partition;
    }
}
