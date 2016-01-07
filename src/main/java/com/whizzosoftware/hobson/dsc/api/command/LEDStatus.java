/*******************************************************************************
 * Copyright (c) 2016 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dsc.api.command;

public class LEDStatus extends DSCCommand {
    public static final String ID = "903";

    private LEDType ledType;
    private Status status;

    public LEDStatus(LEDType ledType, Status status) {
        super(ID);
        this.ledType = ledType;
        this.status = status;
    }

    public LEDType getLEDType() {
        return ledType;
    }

    public Status getStatus() {
        return status;
    }

    public enum LEDType {
        Ready,
        Armed,
        Memory,
        Bypass,
        Trouble,
        Program,
        Fire,
        Backlight,
        AC,
        Flashing;

        public static LEDType forOrdinal(int i) {
            switch (i) {
                case 1:
                    return Ready;
                case 2:
                    return Armed;
                case 3:
                    return Memory;
                case 4:
                    return Bypass;
                case 5:
                    return Trouble;
                case 6:
                    return Program;
                case 7:
                    return Fire;
                case 8:
                    return Backlight;
                case 9:
                    return AC;
                case 10:
                    return Flashing;
            }
            throw new IllegalArgumentException();
        }
    }

    public enum Status {
        Off,
        On,
        Flashing;

        public static Status forOrdinal(int i) {
            switch (i) {
                case 0:
                    return Off;
                case 1:
                    return On;
                case 2:
                    return Flashing;
            }
            throw new IllegalArgumentException();
        }
    }
}
