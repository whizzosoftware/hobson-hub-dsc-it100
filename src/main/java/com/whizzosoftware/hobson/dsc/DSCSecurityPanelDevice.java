/*
 *******************************************************************************
 * Copyright (c) 2016 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************
*/
package com.whizzosoftware.hobson.dsc;

import com.whizzosoftware.hobson.api.device.DeviceType;
import com.whizzosoftware.hobson.api.device.proxy.AbstractHobsonDeviceProxy;
import com.whizzosoftware.hobson.api.plugin.HobsonPlugin;
import com.whizzosoftware.hobson.api.property.TypedProperty;
import com.whizzosoftware.hobson.api.variable.VariableConstants;
import com.whizzosoftware.hobson.api.variable.VariableMask;
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class DSCSecurityPanelDevice extends AbstractHobsonDeviceProxy {
    private static final Logger logger = LoggerFactory.getLogger(DSCSecurityPanelDevice.class);

    public static String ID = "controller";

    DSCSecurityPanelDevice(HobsonPlugin plugin) {
        super(plugin, ID, "DSC Alarm Panel", DeviceType.SECURITY_PANEL);
    }

    @Override
    public void onStartup(String name, Map<String,Object> config) {
        publishVariables(
            createDeviceVariable(VariableConstants.ARMED, VariableMask.READ_WRITE, null, null),
            createDeviceVariable(VariableConstants.FIRMWARE_VERSION, VariableMask.READ_ONLY, null,null),
            createDeviceVariable(VariableConstants.TIME, VariableMask.READ_WRITE, null,null)
        );
    }

    @Override
    public void onShutdown() {

    }

    @Override
    public String getManufacturerName() {
        return "DSC";
    }

    @Override
    public String getManufacturerVersion() {
        return null;
    }

    @Override
    public String getModelName() {
        return null;
    }

    @Override
    public String getPreferredVariableName() {
        return VariableConstants.ARMED;
    }

    @Override
    public void onDeviceConfigurationUpdate(Map<String,Object> config) {
    }

    @Override
    protected TypedProperty[] getConfigurationPropertyTypes() {
        return null;
    }

    @Override
    public void onSetVariables(Map<String,Object> values) {
        for (String name : values.keySet()) {
            Object value = values.get(name);
            if (VariableConstants.TIME.equals(name)) {
                try {
                    DateTime dateTime = ISODateTimeFormat.dateTime().parseDateTime(value.toString());
                    logger.debug("Received set time request: {}", dateTime);
                    ((DSCPlugin) getPlugin()).sendSetDateTimeRequest(dateTime);
                } catch (IllegalArgumentException e) {
                    logger.error("Ignoring set time request with invalid time: {}", value);
                }
            } else if (VariableConstants.ARMED.equals(name)) {
                if ((boolean) value) {
                    ((DSCPlugin) getPlugin()).sendArmPartitionRequest(1);
                } else {
                    ((DSCPlugin) getPlugin()).sendDisarmPartitionRequest(1);
                }
            }
        }
    }

    void onArmedUpdate(boolean armed) {
        setVariableValue(VariableConstants.ARMED, armed, System.currentTimeMillis());
    }

    void onFirmwareUpdate(String firmware) {
        setVariableValue(VariableConstants.FIRMWARE_VERSION, firmware, System.currentTimeMillis());
    }

    void onTimeUpdate(String time) {
        setVariableValue(VariableConstants.TIME, time, System.currentTimeMillis());
    }
}
