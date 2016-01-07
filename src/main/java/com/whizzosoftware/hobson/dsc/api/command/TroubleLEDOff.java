/*******************************************************************************
 * Copyright (c) 2016 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dsc.api.command;

/**
 * This command sends the general trouble status that the trouble LED on a keypad normally displays when there
 * are no troubles on system.
 *
 * @author Dan Noguerol
 */
public class TroubleLEDOff extends DSCCommand {
    public static final String ID = "841";

    private int partition;

    public TroubleLEDOff(int partition) {
        super(ID);
        this.partition = partition;
    }

    public int getPartition() {
        return partition;
    }
}
