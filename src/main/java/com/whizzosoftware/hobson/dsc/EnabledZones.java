/*******************************************************************************
 * Copyright (c) 2016 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dsc;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * A helper class for parsing and checking for enabled zones -- zones that the user has explicitly configured to
 * be enabled.
 *
 * @author Dan Noguerol
 */
public class EnabledZones {
    List<Integer> zoneList;

    public EnabledZones() {
    }

    public EnabledZones(String s) throws NumberFormatException {
        if (s != null && s.trim().length() > 0) {
            zoneList = new ArrayList<>();
            StringTokenizer tok = new StringTokenizer(s, ",");
            while (tok.hasMoreTokens()) {
                zoneList.add(Integer.parseInt(tok.nextToken()));
            }
        }
    }

    public boolean isZoneEnabled(int num) {
        return (zoneList == null || zoneList.contains(num));
    }
}
