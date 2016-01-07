package com.whizzosoftware.hobson.dsc.api.codec;

import com.whizzosoftware.hobson.dsc.api.command.Poll;
import com.whizzosoftware.hobson.dsc.api.command.SetDateAndTime;
import com.whizzosoftware.hobson.dsc.api.command.StatusRequest;
import com.whizzosoftware.hobson.dsc.api.command.TimeDateBroadcastControl;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;
import org.joda.time.format.ISODateTimeFormat;
import org.junit.Test;
import static org.junit.Assert.*;

public class DSCFrameEncoderTest {
    @Test
    public void testPoll() throws Exception {
        DSCFrameEncoder encoder = new DSCFrameEncoder();
        ByteBuf buf = Unpooled.buffer();
        encoder.encode(null, new Poll(), buf);
        assertEquals(7, buf.readableBytes());
        assertEquals("00090\r\n", buf.slice(0, 7).toString(CharsetUtil.UTF_8));
    }

    @Test
    public void testStatusRequest() throws Exception {
        DSCFrameEncoder encoder = new DSCFrameEncoder();
        ByteBuf buf = Unpooled.buffer();
        encoder.encode(null, new StatusRequest(), buf);
        assertEquals(7, buf.readableBytes());
        assertEquals("00191\r\n", buf.slice(0, 7).toString(CharsetUtil.UTF_8));
    }

    @Test
    public void testTimeDateBroadcastControl() throws Exception {
        DSCFrameEncoder encoder = new DSCFrameEncoder();
        ByteBuf buf = Unpooled.buffer();
        encoder.encode(null, new TimeDateBroadcastControl(true), buf);
        assertEquals(8, buf.readableBytes());
        assertEquals("0561CC\r\n", buf.slice(0, 8).toString(CharsetUtil.UTF_8));
    }

    @Test
    public void testSetDateAndTime() throws Exception {
        DSCFrameEncoder encoder = new DSCFrameEncoder();
        ByteBuf buf = Unpooled.buffer();
        encoder.encode(null, new SetDateAndTime(ISODateTimeFormat.dateTime().parseDateTime("2016-01-07T11:20:00.000-07:00")), buf);
        assertEquals(17, buf.readableBytes());
        assertEquals("010112001071684\r\n", buf.slice(0, 17).toString(CharsetUtil.UTF_8));
    }
}
