/*******************************************************************************
 * Copyright (c) 2016 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dsc.api.command;

/**
 * This command simulates a Keypress on a Keypad.
 *
 * @author Dan Noguerol
 */
public class KeyPressed extends DSCCommand {
    public static final String ID = "070";

    public static final byte KEY_0 = 30;
    public static final byte KEY_1 = 31;
    public static final byte KEY_2 = 32;
    public static final byte KEY_3 = 33;
    public static final byte KEY_4 = 34;
    public static final byte KEY_5 = 35;
    public static final byte KEY_6 = 36;
    public static final byte KEY_7 = 37;
    public static final byte KEY_8 = 38;
    public static final byte KEY_9 = 39;
    public static final byte KEY_ASTERISK = 0x2A;
    public static final byte KEY_HASH = 0x23;
    public static final byte KEY_FIRE = 46;
    public static final byte KEY_AMBULANCE = 41;
    public static final byte KEY_PANIC = 0x50;
    public static final byte KEY_FUNC_1 = 61;
    public static final byte KEY_FUNC_2 = 62;
    public static final byte KEY_FUNC_3 = 63;
    public static final byte KEY_FUNC_4 = 64;
    public static final byte KEY_FUNC_5 = 65;
    public static final byte KEY_ARROW_LEFT = 0x3c;
    public static final byte KEY_ARROW_RIGHT = 0x3e;
    public static final byte KEY_ARROW_BOTH = 0x3d;
    public static final byte KEY_BREAK = 0x5e;

    private byte key;

    public KeyPressed(byte key) {
        super(ID);
        this.key = key;
    }

    public byte getKey() {
        return key;
    }
}
