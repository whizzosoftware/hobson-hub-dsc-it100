/*******************************************************************************
 * Copyright (c) 2016 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dsc.api.command;

public class TimeDateBroadcastControl extends DSCCommand {
    public static final String ID = "056";

    private boolean enabled;

    public TimeDateBroadcastControl(boolean enabled) {
        super(ID);

        this.enabled = enabled;
    }

    public String getDataString() {
        return (enabled ? "1" : "0");
    }
}
