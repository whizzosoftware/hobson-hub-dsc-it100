/*******************************************************************************
 * Copyright (c) 2016 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dsc.api.codec;

import com.whizzosoftware.hobson.dsc.api.command.*;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.CorruptedFrameException;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A Netty decoder for RadioRA command frames.
 *
 * @author Dan Noguerol
 */
public class DSCFrameDecoder extends DelimiterBasedFrameDecoder {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final static int MAX_FRAME_LENGTH = 1024;

    public DSCFrameDecoder() {
        super(MAX_FRAME_LENGTH, Unpooled.copiedBuffer("\n", CharsetUtil.UTF_8));
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf buffer) throws Exception {
        logger.trace("decode: {}", buffer.toString(CharsetUtil.UTF_8));

        ByteBuf frame = (ByteBuf)super.decode(ctx, buffer);

        if (frame != null) {
            try {
                if (frame.readableBytes() >= 3) {
                    String cmdId = new String(new byte[] {frame.getByte(0), frame.getByte(1), frame.getByte(2)});
                    switch (cmdId) {
                        case CodeRequired.ID:
                            return new CodeRequired();
                        case CommandAcknowledge.ID:
                            return new CommandAcknowledge();
                        case CommandError.ID:
                            return new CommandError();
                        case LCDUpdate.ID:
                            int lineNumber = frame.getByte(3) - '0';
                            int columnNumber = Integer.parseInt(new String(new byte[] {frame.getByte(4), frame.getByte(5)}));
                            int length = Integer.parseInt(new String(new byte[] {frame.getByte(6), frame.getByte(7)}));
                            return new LCDUpdate(lineNumber, columnNumber, frame.slice(8, length).toString(CharsetUtil.UTF_8));
                        case LEDStatus.ID:
                            return new LEDStatus(LEDStatus.LEDType.forOrdinal(frame.getByte(3) - '0'), LEDStatus.Status.forOrdinal(frame.getByte(4) - '0'));
                        case SoftwareVersion.ID:
                            return new SoftwareVersion(frame.slice(3, 4).toString(CharsetUtil.UTF_8));
                        case PartitionBusy.ID:
                            return new PartitionBusy(frame.getByte(3) - '0');
                        case PartitionNotReady.ID:
                            return new PartitionNotReady(frame.getByte(3) - '0');
                        case PartitionReady.ID:
                            return new PartitionReady(frame.getByte(3) - '0');
                        case TroubleLEDOff.ID:
                            return new TroubleLEDOff(frame.getByte(3) - '0');
                        case ZoneOpen.ID:
                            return new ZoneOpen(Integer.parseInt(new String(new byte[] {frame.getByte(3), frame.getByte(4), frame.getByte(5)})));
                        case ZoneRestored.ID:
                            return new ZoneRestored(Integer.parseInt(new String(new byte[] {frame.getByte(3), frame.getByte(4), frame.getByte(5)})));
                        case TimeDateBroadcast.ID:
                            return new TimeDateBroadcast(frame.slice(3, 10).toString(CharsetUtil.UTF_8));
                        default:
                            logger.debug("Ignoring unknown command frame: {}", buffer.toString(CharsetUtil.UTF_8));
                            return null;
                    }
                } else {
                    throw new CorruptedFrameException("Frame should not be less than 3 bytes");
                }
            } finally {
                frame.release();
            }
        } else {
            return null;
        }
    }
}
