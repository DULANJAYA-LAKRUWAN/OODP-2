package com.lakruwan.smarthome.facade;

import com.lakruwan.smarthome.subsystem.AirConditioning;
import com.lakruwan.smarthome.subsystem.Light;
import com.lakruwan.smarthome.subsystem.SecurityAlarm;

import java.util.ArrayList;
import java.util.List;

/**
 * Facade Pattern Implementation.
 * Provides a unified, high-level interface to the complex subsystem
 * consisting of Lights, Air Conditioning, and Security Alarm.
 */
public class SmartHomeFacade {

    private final Light light;
    private final AirConditioning ac;
    private final SecurityAlarm securityAlarm;

    private final List<SmartHomeListener> listeners = new ArrayList<>();

    /**
     * Snapshot record used to restore subsystem states during Command undo.
     */
    public record SystemSnapshot(
            boolean lightOn,
            int lightBrightness,
            boolean acOn,
            int acTemperature,
            boolean acEcoMode,
            SecurityAlarm.Mode alarmMode,
            boolean alarmSiren
    ) {}

    public SmartHomeFacade(Light light, AirConditioning ac, SecurityAlarm securityAlarm) {
        this.light = light != null ? light : new Light("Main Living Area");
        this.ac = ac != null ? ac : new AirConditioning("Central Climate");
        this.securityAlarm = securityAlarm != null ? securityAlarm : new SecurityAlarm("Whole House");
    }

    public SmartHomeFacade() {
        this(new Light("Living Room & Hall"), new AirConditioning("Central HVAC"), new SecurityAlarm("Zone 1"));
    }

    public void addListener(SmartHomeListener listener) {
        if (listener != null && !listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public void removeListener(SmartHomeListener listener) {
        listeners.remove(listener);
    }

    private void notifyStateChanged() {
        for (SmartHomeListener listener : listeners) {
            listener.onDeviceStateChanged();
        }
    }

    private void notifyLog(String category, String message) {
        for (SmartHomeListener listener : listeners) {
            listener.onLogMessage(category, message);
        }
    }

    // ==========================================
    // Unified Facade Methods for the Subsystem
    // ==========================================

    /**
     * Operation 1: Turn on the lights.
     */
    public void turnOnLights() {
        notifyLog("FACADE", "Executing simplified operation: turnOnLights()");
        light.turnOn();
        notifyStateChanged();
    }

    /**
     * Helper operation: Turn off the lights.
     */
    public void turnOffLights() {
        notifyLog("FACADE", "Executing simplified operation: turnOffLights()");
        light.turnOff();
        notifyStateChanged();
    }

    /**
     * Operation 2: Create an energy-saving schedule.
     * Coordinates multiple subsystems to minimize power consumption:
     * - Dims lights to 30%
     * - Sets AC to energy-saving 26°C with ECO mode enabled
     * - Arms security alarm in STAY mode for household safety
     */
    public void setEnergySavingSchedule() {
        notifyLog("FACADE", "Coordinating subsystems for: ENERGY-SAVING SCHEDULE");
        
        // Subsystem 1: Lighting optimization
        light.dim(30);
        
        // Subsystem 2: AC eco optimization
        if (!ac.isOn()) {
            ac.turnOn();
        }
        ac.setEcoMode(true);
        ac.setTemperature(26);

        // Subsystem 3: Security verification
        if (!securityAlarm.isArmed()) {
            securityAlarm.armStay();
        }

        notifyLog("FACADE", "Energy-Saving schedule successfully applied across all subsystems.");
        notifyStateChanged();
    }

    /**
     * Operation 3: Activate away mode.
     * Prepares the entire house when residents leave:
     * - Turns off all lights
     * - Powers down or sets AC to standby
     * - Arms security alarm in full AWAY mode (all perimeter & motion sensors active)
     */
    public void activateAwayMode() {
        notifyLog("FACADE", "Coordinating subsystems for: AWAY MODE ACTIVATION");

        // Subsystem 1: Turn off all lights
        light.turnOff();

        // Subsystem 2: Power down AC for zero energy waste
        ac.turnOff();

        // Subsystem 3: Arm security in Away Mode
        securityAlarm.armAway();

        notifyLog("FACADE", "Away Mode active: Lights OFF, AC Standby, Security ARMED.");
        notifyStateChanged();
    }

    /**
     * Operation 4: Activate home mode (Return home preset).
     */
    public void activateHomeMode() {
        notifyLog("FACADE", "Coordinating subsystems for: HOME MODE (Welcome Home)");

        light.turnOn();
        ac.turnOn();
        ac.setEcoMode(false);
        ac.setTemperature(22);
        securityAlarm.disarm();

        notifyLog("FACADE", "Home Mode active: Welcome lights ON, AC 22°C, Security DISARMED.");
        notifyStateChanged();
    }

    // ==========================================
    // Snapshot & Memento Support for Command Undo
    // ==========================================

    public SystemSnapshot captureSnapshot() {
        return new SystemSnapshot(
                light.isOn(),
                light.getBrightness(),
                ac.isOn(),
                ac.getTemperature(),
                ac.isEcoMode(),
                securityAlarm.getMode(),
                securityAlarm.isSirenActive()
        );
    }

    public void restoreSnapshot(SystemSnapshot snapshot) {
        if (snapshot == null) return;
        notifyLog("FACADE", "Restoring previous subsystem state from snapshot...");

        // Restore Light
        if (snapshot.lightOn()) {
            light.dim(snapshot.lightBrightness());
        } else {
            light.turnOff();
        }

        // Restore AC
        if (snapshot.acOn()) {
            ac.turnOn();
            ac.setTemperature(snapshot.acTemperature());
            ac.setEcoMode(snapshot.acEcoMode());
        } else {
            ac.turnOff();
        }

        // Restore Security Alarm
        switch (snapshot.alarmMode()) {
            case DISARMED -> securityAlarm.disarm();
            case ARMED_STAY -> securityAlarm.armStay();
            case ARMED_AWAY -> securityAlarm.armAway();
        }

        notifyLog("FACADE", "Subsystem state successfully restored.");
        notifyStateChanged();
    }

    // Subsystem getters
    public Light getLight() {
        return light;
    }

    public AirConditioning getAc() {
        return ac;
    }

    public SecurityAlarm getSecurityAlarm() {
        return securityAlarm;
    }
}
