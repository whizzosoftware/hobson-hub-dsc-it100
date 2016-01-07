package com.whizzosoftware.hobson.dsc.api.codec;

import com.whizzosoftware.hobson.dsc.api.command.*;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.CorruptedFrameException;
import io.netty.util.CharsetUtil;
import org.junit.Test;
import static org.junit.Assert.*;

import java.nio.charset.Charset;

public class DSCFrameDecoderTest {
    @Test
    public void testCommandAcknowledge() throws Exception {
        DSCFrameDecoder decoder = new DSCFrameDecoder();
        Object o = decoder.decode(null, Unpooled.copiedBuffer("500\r\n", Charset.forName("UTF8")));
        assertTrue(o instanceof CommandAcknowledge);
    }

    @Test
    public void testCommandError() throws Exception {
        DSCFrameDecoder decoder = new DSCFrameDecoder();
        Object o = decoder.decode(null, Unpooled.copiedBuffer("501\r\n", Charset.forName("UTF8")));
        assertTrue(o instanceof CommandError);
    }

    @Test
    public void testLCDUpdate() throws Exception {
        DSCFrameDecoder decoder = new DSCFrameDecoder();
        Object o = decoder.decode(null, Unpooled.copiedBuffer("90100032  Date     Time JUL 08/14 10:49p\r\n", CharsetUtil.UTF_8));
        assertTrue(o instanceof LCDUpdate);
        assertEquals("  Date     Time JUL 08/14 10:49p", ((LCDUpdate)o).getData());
    }

    @Test
    public void testLEDStatus() throws Exception {
        DSCFrameDecoder decoder = new DSCFrameDecoder();

        Object o = decoder.decode(null, Unpooled.copiedBuffer("90311FE\r\n", Charset.forName("UTF8")));
        assertTrue(o instanceof LEDStatus);
        LEDStatus ls = (LEDStatus)o;
        assertEquals(LEDStatus.LEDType.Ready, ls.getLEDType());
        assertEquals(LEDStatus.Status.On, ls.getStatus());

        o = decoder.decode(null, Unpooled.copiedBuffer("90320FE\r\n", Charset.forName("UTF8")));
        assertTrue(o instanceof LEDStatus);
        ls = (LEDStatus)o;
        assertEquals(LEDStatus.LEDType.Armed, ls.getLEDType());
        assertEquals(LEDStatus.Status.Off, ls.getStatus());

        o = decoder.decode(null, Unpooled.copiedBuffer("90332FE\r\n", Charset.forName("UTF8")));
        assertTrue(o instanceof LEDStatus);
        ls = (LEDStatus)o;
        assertEquals(LEDStatus.LEDType.Memory, ls.getLEDType());
        assertEquals(LEDStatus.Status.Flashing, ls.getStatus());

        o = decoder.decode(null, Unpooled.copiedBuffer("90341FE\r\n", Charset.forName("UTF8")));
        assertTrue(o instanceof LEDStatus);
        ls = (LEDStatus)o;
        assertEquals(LEDStatus.LEDType.Bypass, ls.getLEDType());
        assertEquals(LEDStatus.Status.On, ls.getStatus());

        o = decoder.decode(null, Unpooled.copiedBuffer("90350FE\r\n", Charset.forName("UTF8")));
        assertTrue(o instanceof LEDStatus);
        ls = (LEDStatus)o;
        assertEquals(LEDStatus.LEDType.Trouble, ls.getLEDType());
        assertEquals(LEDStatus.Status.Off, ls.getStatus());

        o = decoder.decode(null, Unpooled.copiedBuffer("90362FE\r\n", Charset.forName("UTF8")));
        assertTrue(o instanceof LEDStatus);
        ls = (LEDStatus)o;
        assertEquals(LEDStatus.LEDType.Program, ls.getLEDType());
        assertEquals(LEDStatus.Status.Flashing, ls.getStatus());

        o = decoder.decode(null, Unpooled.copiedBuffer("90371FE\r\n", Charset.forName("UTF8")));
        assertTrue(o instanceof LEDStatus);
        ls = (LEDStatus)o;
        assertEquals(LEDStatus.LEDType.Fire, ls.getLEDType());
        assertEquals(LEDStatus.Status.On, ls.getStatus());

        o = decoder.decode(null, Unpooled.copiedBuffer("90380FE\r\n", Charset.forName("UTF8")));
        assertTrue(o instanceof LEDStatus);
        ls = (LEDStatus)o;
        assertEquals(LEDStatus.LEDType.Backlight, ls.getLEDType());
        assertEquals(LEDStatus.Status.Off, ls.getStatus());

        o = decoder.decode(null, Unpooled.copiedBuffer("90391FE\r\n", Charset.forName("UTF8")));
        assertTrue(o instanceof LEDStatus);
        ls = (LEDStatus)o;
        assertEquals(LEDStatus.LEDType.AC, ls.getLEDType());
        assertEquals(LEDStatus.Status.On, ls.getStatus());

        try {
            decoder.decode(null, Unpooled.copiedBuffer("903A1FE\r\n", Charset.forName("UTF8")));
            fail("Should have thrown exception");
        } catch (IllegalArgumentException e) {
            // NO-OP
        }

        try {
            decoder.decode(null, Unpooled.copiedBuffer("90313FE\r\n", Charset.forName("UTF8")));
            fail("Should have thrown exception");
        } catch (IllegalArgumentException e) {
            // NO-OP
        }
    }

    @Test
    public void testPartitionBusy() throws Exception {
        DSCFrameDecoder decoder = new DSCFrameDecoder();
        Object o = decoder.decode(null, Unpooled.copiedBuffer("6731CC\n", CharsetUtil.UTF_8));
        assertTrue(o instanceof PartitionBusy);
        assertEquals(1, ((PartitionBusy)o).getPartition());
    }

    @Test
    public void testPartitionNotReady() throws Exception {
        DSCFrameDecoder decoder = new DSCFrameDecoder();
        Object o = decoder.decode(null, Unpooled.copiedBuffer("6511CC\n", CharsetUtil.UTF_8));
        assertTrue(o instanceof PartitionNotReady);
        assertEquals(1, ((PartitionNotReady)o).getPartition());
    }

    @Test
    public void testPartitionReady() throws Exception {
        DSCFrameDecoder decoder = new DSCFrameDecoder();
        Object o = decoder.decode(null, Unpooled.copiedBuffer("6501CC\n", CharsetUtil.UTF_8));
        assertTrue(o instanceof PartitionReady);
        assertEquals(1, ((PartitionReady)o).getPartition());
    }

    @Test
    public void testSoftwareVersion() throws Exception {
        DSCFrameDecoder decoder = new DSCFrameDecoder();
        Object o = decoder.decode(null, Unpooled.copiedBuffer("908010102C5\n", CharsetUtil.UTF_8));
        assertTrue(o instanceof SoftwareVersion);
        assertEquals("0101", ((SoftwareVersion)o).getVersion());
    }

    @Test
    public void testTroubleLEDOff() throws Exception {
        DSCFrameDecoder decoder = new DSCFrameDecoder();
        Object o = decoder.decode(null, Unpooled.copiedBuffer("8411CE\n", CharsetUtil.UTF_8));
        assertTrue(o instanceof TroubleLEDOff);
        assertEquals(1, ((TroubleLEDOff)o).getPartition());
    }

    @Test
    public void testZoneOpen() throws Exception {
        DSCFrameDecoder decoder = new DSCFrameDecoder();

        Object o = decoder.decode(null, Unpooled.copiedBuffer("60906330\r\n", CharsetUtil.UTF_8));
        assertTrue(o instanceof ZoneOpen);
        assertEquals(63, ((ZoneOpen)o).getZone());
    }

    @Test
    public void testZoneRestored() throws Exception {
        DSCFrameDecoder decoder = new DSCFrameDecoder();

        Object o = decoder.decode(null, Unpooled.copiedBuffer("61006330\r\n", CharsetUtil.UTF_8));
        assertTrue(o instanceof ZoneRestored);
        assertEquals(63, ((ZoneRestored)o).getZone());
    }

    @Test
    public void testInvalidCommand() throws Exception {
        DSCFrameDecoder decoder = new DSCFrameDecoder();
        Object o = decoder.decode(null, Unpooled.copiedBuffer("XYZZY\r\n", Charset.forName("UTF8")));
        assertNull(o);
    }

    @Test
    public void testShortFrame() throws Exception {
        DSCFrameDecoder decoder = new DSCFrameDecoder();
        try {
            decoder.decode(null, Unpooled.copiedBuffer("XY\n", Charset.forName("UTF8")));
            fail("Should have thrown exception");
        } catch (CorruptedFrameException e) {
            // NO-OP
        }
    }
}
