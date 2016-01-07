package com.whizzosoftware.hobson.dsc;

import org.junit.Test;
import static org.junit.Assert.*;

public class EnabledZonesTest {
    @Test
    public void testEmptyConstructor() {
        EnabledZones ez = new EnabledZones();
        assertTrue(ez.isZoneEnabled(1));
        assertTrue(ez.isZoneEnabled(2));
    }

    @Test
    public void testNullConstructor() {
        EnabledZones ez = new EnabledZones(null);
        assertTrue(ez.isZoneEnabled(1));
        assertTrue(ez.isZoneEnabled(2));
    }

    @Test
    public void testEmptyStringConstructor() {
        EnabledZones ez = new EnabledZones("");
        assertTrue(ez.isZoneEnabled(1));
        assertTrue(ez.isZoneEnabled(2));
    }

    @Test
    public void testSingleZoneConstructor() {
        EnabledZones ez = new EnabledZones("1");
        assertTrue(ez.isZoneEnabled(1));
        assertFalse(ez.isZoneEnabled(2));
    }

    @Test
    public void testMultiZoneConstructor() {
        EnabledZones ez = new EnabledZones("1,2");
        assertTrue(ez.isZoneEnabled(1));
        assertTrue(ez.isZoneEnabled(2));
        assertFalse(ez.isZoneEnabled(3));
    }
}
