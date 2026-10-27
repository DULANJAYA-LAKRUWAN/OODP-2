package com.lakruwan.smarthome.subsystem;

/**
 * Subsystem component: Smart Security Alarm System.
 */
public class SecurityAlarm {

    public enum Mode {
        DISARMED,
        ARMED_STAY,
        ARMED_AWAY
    }

    private final String zone;
    private Mode mode;
    private boolean sirenActive;

    public SecurityAlarm(String zone) {
        this.zone = zone;
        this.mode = Mode.DISARMED;
        this.sirenActive = false;
    }

    public void armAway() {
        this.mode = Mode.ARMED_AWAY;
        this.sirenActive = false;
        System.out.printf("[Subsystem: Security - %s] ARMED IN AWAY MODE (Perimeter + Motion Sensors Active)%n", zone);
    }

    public void armStay() {
        this.mode = Mode.ARMED_STAY;
        this.sirenActive = false;
        System.out.printf("[Subsystem: Security - %s] ARMED IN STAY/HOME MODE (Perimeter Active, Motion Bypass)%n", zone);
    }

    public void disarm() {
        this.mode = Mode.DISARMED;
        this.sirenActive = false;
        System.out.printf("[Subsystem: Security - %s] DISARMED (System Safe)%n", zone);
    }

    public void triggerAlarm() {
        if (mode != Mode.DISARMED) {
            this.sirenActive = true;
            System.out.printf("🚨 [Subsystem: Security - %s] SIREN TRIGGERED! Intrusion Detected!%n", zone);
        } else {
            System.out.printf("[Subsystem: Security - %s] Motion detected, but system is DISARMED.%n", zone);
        }
    }

    public boolean isArmed() {
        return mode != Mode.DISARMED;
    }

    public Mode getMode() {
        return mode;
    }

    public boolean isSirenActive() {
        return sirenActive;
    }

    public String getZone() {
        return zone;
    }

    public String getStatusDescription() {
        if (sirenActive) {
            return "SIREN ACTIVE!";
        }
        return switch (mode) {
            case DISARMED -> "DISARMED";
            case ARMED_STAY -> "ARMED (Stay)";
            case ARMED_AWAY -> "ARMED (Away)";
        };
    }

    @Override
    public String toString() {
        return String.format("Alarm [%s]: %s", zone, getStatusDescription());
    }
}
