/*******************************************************************************
 * Copyright (c) 2016 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dsc.api.command;

/**
 * This command indicates that the partition can now be armed (all zones restored, no troubles, etc.) Also issued
 * at the end of Bell Timeout if the partition was READY when an alarm occurred.
 *
 * @author Dan Noguerol
 */
public class PartitionReady extends DSCCommand {
    public static final String ID = "650";

    private int partition;

    public PartitionReady(int partition) {
        super(ID);
        this.partition = partition;
    }

    public int getPartition() {
        return partition;
    }
}
