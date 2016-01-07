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
import org.joda.time.format.ISODateTimeFormat;

public class TimeDateBroadcast extends DSCCommand {
    public static final String ID = "550";

    private String s;
    private DateTime dateTime;

    public TimeDateBroadcast(String s) {
        super(ID);
        this.s = s;
        dateTime = DateTimeFormat.forPattern("HHmmMMddYY").parseDateTime(s);
    }

    public String getString() {
        return s;
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public String getISO8601String() {
        return ISODateTimeFormat.dateTime().print(dateTime);
    }
}
