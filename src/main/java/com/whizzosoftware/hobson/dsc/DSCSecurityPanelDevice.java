/*******************************************************************************
 * Copyright (c) 2016 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dsc;

import com.whizzosoftware.hobson.api.device.AbstractHobsonDevice;
import com.whizzosoftware.hobson.api.device.DeviceType;
import com.whizzosoftware.hobson.api.plugin.HobsonPlugin;
import com.whizzosoftware.hobson.api.property.PropertyContainer;
import com.whizzosoftware.hobson.api.property.TypedProperty;
import com.whizzosoftware.hobson.api.variable.HobsonVariable;
import com.whizzosoftware.hobson.api.variable.VariableConstants;
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DSCSecurityPanelDevice extends AbstractHobsonDevice {
    private static final Logger logger = LoggerFactory.getLogger(DSCSecurityPanelDevice.class);

    public static String ID = "controller";

    public DSCSecurityPanelDevice(HobsonPlugin plugin) {
        super(plugin, ID);

        setDefaultName("DSC Alarm Panel");
    }

    @Override
    public void onStartup(PropertyContainer config) {
        super.onStartup(config);

        publishVariable(VariableConstants.ARMED, null, HobsonVariable.Mask.READ_WRITE);
        publishVariable(VariableConstants.FIRMWARE_VERSION, null, HobsonVariable.Mask.READ_ONLY);
        publishVariable(VariableConstants.TIME, null, HobsonVariable.Mask.READ_WRITE);
    }

    @Override
    public void onShutdown() {

    }

    @Override
    public DeviceType getType() {
        return DeviceType.SECURITY_PANEL;
    }

    @Override
    public String getPreferredVariableName() {
        return VariableConstants.ARMED;
    }

    @Override
    protected TypedProperty[] createSupportedProperties() {
        return null;
    }

    @Override
    public void onSetVariable(String variableName, Object value) {
        if (VariableConstants.TIME.equals(variableName)) {
            try {
                DateTime dateTime = ISODateTimeFormat.dateTime().parseDateTime(value.toString());
                logger.debug("Received set time request: {}", dateTime);
                ((DSCPlugin)getPlugin()).sendSetDateTimeRequest(dateTime);
            } catch (IllegalArgumentException e) {
                logger.error("Ignoring set time request with invalid time: {}", value);
            }
        } else if (VariableConstants.ARMED.equals(variableName)) {
            if ((boolean)value) {
                ((DSCPlugin)getPlugin()).sendArmPartitionRequest(1);
            } else {
                ((DSCPlugin)getPlugin()).sendDisarmPartitionRequest(1);
            }
        }
    }
}
