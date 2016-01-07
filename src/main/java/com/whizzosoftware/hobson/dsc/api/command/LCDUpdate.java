/*******************************************************************************
 * Copyright (c) 2016 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dsc.api.command;

/**
 * This command is sent whenever the text of the alarm system menu changes.
 *
 * @author Dan Noguerol
 */
public class LCDUpdate extends DSCCommand {
    public static final String ID = "901";

    private int lineNumber;
    private int columnNumber;
    private String data;

    public LCDUpdate(int lineNumber, int columnNumber, String data) {
        super(ID);
        this.lineNumber = lineNumber;
        this.columnNumber = columnNumber;
        this.data = data;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public int getColumnNumber() {
        return columnNumber;
    }

    public String getData() {
        return data;
    }
}
