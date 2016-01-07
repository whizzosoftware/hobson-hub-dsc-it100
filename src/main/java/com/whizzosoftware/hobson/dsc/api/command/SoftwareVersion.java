/*******************************************************************************
 * Copyright (c) 2016 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dsc.api.command;

public class SoftwareVersion extends DSCCommand {
    public final static String ID = "908";

    private String version;

    public SoftwareVersion(String version) {
        super(ID);
        this.version = version;
    }

    public String getVersion() {
        return version;
    }
}
