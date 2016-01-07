/*******************************************************************************
 * Copyright (c) 2016 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dsc.api.codec;

import com.whizzosoftware.hobson.dsc.api.command.DSCCommand;
import com.whizzosoftware.hobson.dsc.api.command.Poll;
import com.whizzosoftware.hobson.dsc.api.command.StatusRequest;
import com.whizzosoftware.hobson.dsc.api.command.TimeDateBroadcastControl;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A Netty encoder for RadioRA command frames.
 *
 * @author Dan Noguerol
 */
public class DSCFrameEncoder extends MessageToByteEncoder<DSCCommand> {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    protected void encode(ChannelHandlerContext ctx, DSCCommand cmd, ByteBuf out) throws Exception {
        String cmdStr = cmd.getCommandString();

        if (cmdStr != null) {
            logger.trace("encode: {}", cmdStr);

            // calculate checksum
            char checksum = 0;
            for (int i=0; i < cmdStr.length(); i++) {
                checksum += cmdStr.charAt(i);
            }
            checksum = (char)(checksum & 0xFF);

            // write data frame
            byte[] bytes = (cmdStr + Integer.toHexString((int)checksum).toUpperCase() + "\r\n").getBytes();
            out.writeBytes(bytes);

            if (logger.isTraceEnabled()) {
                logger.trace("Wrote {} bytes: {}", bytes.length, Hex.encodeHexString(bytes));
            }
        } else {
            logger.error("Attempt to send unknown command class: {}", cmd);
        }
    }
}
