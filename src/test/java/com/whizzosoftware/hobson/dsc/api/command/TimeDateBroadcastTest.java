package com.whizzosoftware.hobson.dsc.api.command;

import org.junit.Test;
import static org.junit.Assert.*;

public class TimeDateBroadcastTest {
    @Test
    public void testConstructor() {
        TimeDateBroadcast tdb = new TimeDateBroadcast("1120010716");
        // TODO: account for time zone differences
        assertEquals("2016-01-07T11:20:00.000-07:00", tdb.getISO8601String());

        tdb = new TimeDateBroadcast("1300010716");
        assertEquals("2016-01-07T13:00:00.000-07:00", tdb.getISO8601String());
    }
}
