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
import com.whizzosoftware.hobson.api.property.TypedProperty;
import com.whizzosoftware.hobson.api.variable.VariableConstants;
import com.whizzosoftware.hobson.api.variable.VariableMask;

import java.util.Map;

/**
 * A device representing a DSC alarm zone.
 *
 * @author Dan Noguerol
 */
public class DSCZoneDevice extends AbstractHobsonDeviceProxy {
    private boolean startValue;

    DSCZoneDevice(DSCPlugin plugin, int zone, boolean startValue) {
        super(plugin, Integer.toString(zone), "DSC Alarm Zone " + zone, DeviceType.SENSOR);
        this.startValue = startValue;
    }

    @Override
    public void onStartup(String name, Map<String,Object> config) {
        // publish an "on" variable
        publishVariables(createDeviceVariable(VariableConstants.ON, VariableMask.READ_ONLY, startValue, System.currentTimeMillis()));
    }

    @Override
    protected TypedProperty[] getConfigurationPropertyTypes() {
        return null;
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
        return VariableConstants.ON;
    }

    @Override
    public void onDeviceConfigurationUpdate(Map<String,Object> config) {

    }

    public void onUpdateZoneState(boolean open) {
        setVariableValue(VariableConstants.ON, open, System.currentTimeMillis());
    }

    @Override
    public void onSetVariables(Map<String,Object> values) {
    }
}
