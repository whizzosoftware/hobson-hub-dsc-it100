/*******************************************************************************
 * Copyright (c) 2016 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dsc.api.command;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

public class SetDateAndTime extends DSCCommand {
    public static final String ID = "010";

    private DateTime dateTime;

    public SetDateAndTime(DateTime dateTime) {
        super(ID);
        this.dateTime = dateTime;
    }

    public String getDataString() {
        return DateTimeFormat.forPattern("HHmmMMddYY").print(dateTime);
    }
}
