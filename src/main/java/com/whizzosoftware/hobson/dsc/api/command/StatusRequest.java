/*******************************************************************************
 * Copyright (c) 2016 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dsc.api.command;

/**
 * A request for alarm system status. Responds with general zone, partition and trouble status updates.
 *
 * @author Dan Noguerol
 */
public class StatusRequest extends DSCCommand {
    public static final String ID = "001";

    public StatusRequest() {
        super(ID);
    }
}
