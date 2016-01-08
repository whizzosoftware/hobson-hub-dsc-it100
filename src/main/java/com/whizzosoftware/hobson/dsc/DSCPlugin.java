/*******************************************************************************
 * Copyright (c) 2016 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dsc;

import com.whizzosoftware.hobson.api.HobsonInvalidRequestException;
import com.whizzosoftware.hobson.api.device.DeviceContext;
import com.whizzosoftware.hobson.api.device.HobsonDevice;
import com.whizzosoftware.hobson.api.event.HobsonEvent;
import com.whizzosoftware.hobson.api.plugin.channel.AbstractChannelObjectPlugin;
import com.whizzosoftware.hobson.api.property.PropertyContainer;
import com.whizzosoftware.hobson.api.property.TypedProperty;
import com.whizzosoftware.hobson.api.variable.VariableConstants;
import com.whizzosoftware.hobson.api.variable.VariableContext;
import com.whizzosoftware.hobson.api.variable.VariableUpdate;
import com.whizzosoftware.hobson.dsc.api.codec.DSCFrameDecoder;
import com.whizzosoftware.hobson.dsc.api.codec.DSCFrameEncoder;
import com.whizzosoftware.hobson.dsc.api.command.*;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.rxtx.RxtxChannelConfig;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A Hobson driver that controls a DSC alarm system via its RS-232 interface.
 *
 * @author Dan Noguerol
 */
public class DSCPlugin extends AbstractChannelObjectPlugin {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private DeviceContext panelContext;
    private String userCode;
    private EnabledZones enabledZones = new EnabledZones();
    private boolean initialStatusRequestSent;

    public DSCPlugin(String pluginId) {
        super(pluginId);
    }

    @Override
    public String getName() {
        return "DSC Alarm IT-100";
    }

    @Override
    public void onPluginConfigurationUpdate(PropertyContainer config) {
        super.onPluginConfigurationUpdate(config);
        userCode = config.getStringPropertyValue("user.code");
        try {
            enabledZones = new EnabledZones(config.getStringPropertyValue("enabled.zones"));
        } catch (NumberFormatException e) {
            logger.error("Error reading enabled zones property; enabling all zones", e);
        }

        if (initialStatusRequestSent) {
            logger.debug("Sending status request for configuration change");
            send(new StatusRequest());
        }
    }

    @Override
    protected ChannelInboundHandlerAdapter getDecoder() {
        return new DSCFrameDecoder();
    }

    @Override
    protected ChannelOutboundHandlerAdapter getEncoder() {
        return new DSCFrameEncoder();
    }

    @Override
    protected void configureChannel(ChannelConfig cfg) {
        if (cfg instanceof RxtxChannelConfig) {
            RxtxChannelConfig config = (RxtxChannelConfig)cfg;
            config.setBaudrate(9600);
            config.setDatabits(RxtxChannelConfig.Databits.DATABITS_8);
            config.setStopbits(RxtxChannelConfig.Stopbits.STOPBITS_1);
            config.setParitybit(RxtxChannelConfig.Paritybit.NONE);
        }
    }

    @Override
    protected void onChannelConnected() {
        panelContext = DeviceContext.create(getContext(), DSCSecurityPanelDevice.ID);
        if (!hasDevice(panelContext)) {
            publishDevice(new DSCSecurityPanelDevice(this));
        }

        logger.debug("Enabling time/date broadcast control");
        send(new TimeDateBroadcastControl(true));

        logger.debug("Sending initial status request");
        send(new StatusRequest());
        initialStatusRequestSent = true;
    }

    @Override
    protected void onChannelData(Object o) {
        // process the received command
        if (o instanceof CodeRequired) {
            logger.debug("Received code required");
        } else if (o instanceof CommandAcknowledge) {
            logger.debug("Received command acknowledgement");
        } else if (o instanceof CommandError) {
            logger.error("Received command error");
        } else if (o instanceof LCDUpdate) {
            String line = ((LCDUpdate)o).getData();
            logger.trace("Received LCD update for partition {}", line);
        } else if (o instanceof LEDStatus) {
            String ledType = ((LEDStatus)o).getLEDType().name();
            String ledStatus = ((LEDStatus)o).getStatus().name();
            logger.debug("LED {} is {}", ledType, ledStatus);
            if (ledType.equalsIgnoreCase("armed")) {
                fireVariableUpdateNotification(new VariableUpdate(VariableContext.create(panelContext, VariableConstants.ARMED), "on".equalsIgnoreCase(ledStatus)));
            }
        } else if (o instanceof PartitionBusy) {
            logger.debug("Partition {} is busy", ((PartitionBusy)o).getPartition());
        } else if (o instanceof PartitionNotReady) {
            logger.debug("Partition {} is not ready", ((PartitionNotReady)o).getPartition());
        } else if (o instanceof PartitionReady) {
            logger.debug("Partition {} is ready", ((PartitionReady)o).getPartition());
        } else if (o instanceof SoftwareVersion) {
            String version = ((SoftwareVersion)o).getVersion();
            logger.info("DSC software version is: {}", version);
            if (version != null) {
                fireVariableUpdateNotification(new VariableUpdate(VariableContext.create(panelContext, VariableConstants.FIRMWARE_VERSION), version));
            }
        } else if (o instanceof TroubleLEDOff) {
            logger.debug("Trouble LED off: {}", ((TroubleLEDOff) o).getPartition());
        } else if (o instanceof ZoneOpen) {
            logger.debug("Zone {} is open", ((ZoneOpen)o).getZone());
            updateZoneState(((ZoneOpen) o).getZone(), true);
        } else if (o instanceof ZoneRestored) {
            logger.debug("Zone {} is closed", ((ZoneRestored)o).getZone());
            updateZoneState(((ZoneRestored)o).getZone(), false);
        } else if (o instanceof TimeDateBroadcast) {
            String ts = ((TimeDateBroadcast)o).getISO8601String();
            logger.debug("Time/date broadcast: {}", ts);
            fireVariableUpdateNotification(new VariableUpdate(VariableContext.create(panelContext, VariableConstants.TIME), ts));
        } else {
            logger.debug("Ignoring unknown command: {}", o);
        }

        // flag the panel and all sensors as available
        long now = System.currentTimeMillis();
        getDevice(panelContext).getRuntime().setDeviceAvailability(true, now);
        for (HobsonDevice d : getDeviceManager().getAllDevices(getContext())) {
            setDeviceAvailability(d.getContext(), true, now);
        }
    }

    public void sendSetDateTimeRequest(DateTime dateTime) {
        send(new SetDateAndTime(dateTime));
    }

    public void sendArmPartitionRequest(int partition) {
        if (userCode != null && userCode.trim().length() > 0) {
            try {
                send(new PartitionArmControlWithCode(partition, userCode));
            } catch (HobsonInvalidRequestException e) {
                logger.error("Invalid partition or code", e);
            }
        } else {
            logger.error("No user code has been entered; unable to arm system");
        }
    }

    public void sendDisarmPartitionRequest(int partition) {
        if (userCode != null && userCode.trim().length() > 0) {
            try {
                send(new PartitionDisarmControlWithCode(partition, userCode));
            } catch (HobsonInvalidRequestException e) {
                logger.error("Invalid partition or code", e);
            }
        } else {
            logger.error("No user code has been entered; unable to disarm system");
        }
    }

    @Override
    protected void onChannelDisconnected() {
    }

    private void updateZoneState(int zone, boolean isOpen) {
        if (enabledZones.isZoneEnabled(zone)) {
            try {
                DeviceContext dctx = DeviceContext.create(getContext(), Integer.toString(zone));
                if (!hasDevice(dctx)) {
                    logger.debug("Creating new device: {}", dctx);
                    publishDevice(new DSCZoneDevice(this, zone, isOpen));
                } else {
                    logger.debug("Setting existing device {} to {}", dctx, isOpen);
                    fireVariableUpdateNotification(new VariableUpdate(VariableContext.create(dctx, VariableConstants.ON), isOpen));
                }
            } catch (Exception e) {
                logger.error("Error updating zone state", e);
            }
        }
    }

    @Override
    public long getRefreshInterval() {
        return 0;
    }

    @Override
    public void onHobsonEvent(HobsonEvent event) {
        // TODO
    }

    @Override
    protected TypedProperty[] createSupportedProperties() {
        return new TypedProperty[] {
            new TypedProperty.Builder("serial.port", "Serial Port", "The serial port that the Lutron RA-RS232 controller is connected to (should not be used with Serial Hostname)", TypedProperty.Type.STRING).build(),
            new TypedProperty.Builder("serial.hostname", "Serial Hostname", "The hostname of the GlobalCache device that the Lutron RA-RS232 controller is connected to (should not be used with Serial Port)", TypedProperty.Type.STRING).build(),
            new TypedProperty.Builder("user.code", "User Code", "The user code you enter into the keypad to arm/disarm the system", TypedProperty.Type.SECURE_STRING).build(),
            new TypedProperty.Builder("enabled.zones", "Enabled Zones", "A comma-separated list of zones numbers you wish to include (or leave blank to include all)", TypedProperty.Type.STRING).build()
        };
    }
}
