/*******************************************************************************
 * Copyright (c) 2016 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dsc.api.command;

/**
 * This command indicates that a Command has been received. This command is always the first response to a command
 * from the application unless there is a checksum error then a Command Error (501) is sent.
 *
 * @author Dan Noguerol
 */
public class CommandAcknowledge extends DSCCommand {
    public static final String ID = "500";

    public CommandAcknowledge() {
        super(ID);
    }
}
