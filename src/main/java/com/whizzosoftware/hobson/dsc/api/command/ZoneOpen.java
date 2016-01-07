/*******************************************************************************
 * Copyright (c) 2016 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dsc.api.command;

/**
 * This command indicates the general status of a zone.
 *
 * @author Dan Noguerol
 */
public class ZoneOpen extends DSCCommand {
    public static final String ID = "609";

    private int zone;

    public ZoneOpen(int zone) {
        super(ID);
        this.zone = zone;
    }

    public int getZone() {
        return zone;
    }
}
