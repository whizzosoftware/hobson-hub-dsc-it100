/*******************************************************************************
 * Copyright (c) 2016 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dsc.api.command;

/**
 * Base class for DSC command frames.
 *
 * @author Dan Noguerol
 */
abstract public class DSCCommand {
    private String type;

    public DSCCommand(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public String getCommandString() {
        String data = getDataString();
        return this.type + (data != null ? data : "");
    }

    public String getDataString() {
        return null;
    }
}
