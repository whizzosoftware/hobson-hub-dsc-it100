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
import com.whizzosoftware.hobson.api.property.PropertyContainer;
import com.whizzosoftware.hobson.api.property.TypedProperty;
import com.whizzosoftware.hobson.api.variable.HobsonVariable;
import com.whizzosoftware.hobson.api.variable.VariableConstants;

/**
 * A device representing a DSC alarm zone.
 *
 * @author Dan Noguerol
 */
public class DSCZoneDevice extends AbstractHobsonDevice {
    private boolean startValue;

    public DSCZoneDevice(DSCPlugin plugin, int zone, boolean startValue) {
        super(plugin, Integer.toString(zone));
        setDefaultName("DSC Alarm Zone " + zone);
        this.startValue = startValue;
    }

    @Override
    public void onStartup(PropertyContainer config) {
        super.onStartup(config);

        // publish an "on" variable
        publishVariable(VariableConstants.ON, startValue, HobsonVariable.Mask.READ_ONLY);
    }

    @Override
    protected TypedProperty[] createSupportedProperties() {
        return null;
    }

    @Override
    public void onShutdown() {
    }

    @Override
    public DeviceType getType() {
        return DeviceType.SENSOR;
    }

    @Override
    public String getPreferredVariableName() {
        return VariableConstants.ON;
    }

    @Override
    public void onSetVariable(String variableName, Object value) {
    }
}
