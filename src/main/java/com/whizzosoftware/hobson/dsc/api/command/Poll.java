/*******************************************************************************
 * Copyright (c) 2016 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dsc.api.command;

/**
 * Verifies communication channel. Should receive a Command Acknowledge (500) in response.
 *
 * @author Dan Noguerol
 */
public class Poll extends DSCCommand {
    public static final String ID = "000";

    public Poll() {
        super(ID);
    }
}
