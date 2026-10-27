package com.lakruwan.smarthome.facade;

/**
 * Listener interface for observing Smart Home events and subsystem state transitions.
 */
public interface SmartHomeListener {

    /**
     * Called when a subsystem device state changes.
     */
    void onDeviceStateChanged();

    /**
     * Called when a system event or pattern execution log message is produced.
     */
    void onLogMessage(String category, String message);
}
