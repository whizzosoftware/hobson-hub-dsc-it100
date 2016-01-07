package com.whizzosoftware.hobson.dsc.api.command;

import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import org.junit.Test;
import static org.junit.Assert.*;

public class SetDateAndTimeTest {
    @Test
    public void testConstructor() {
        DateTime dateTime = ISODateTimeFormat.dateTime().parseDateTime("2016-01-07T11:20:00.000-07:00");
        SetDateAndTime sdat = new SetDateAndTime(dateTime);
        assertEquals("1120010716", sdat.getDataString());
        assertEquals("0101120010716", sdat.getCommandString());
    }
}
