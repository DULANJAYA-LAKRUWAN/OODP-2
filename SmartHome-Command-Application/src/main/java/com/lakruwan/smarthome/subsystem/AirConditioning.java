package com.lakruwan.smarthome.subsystem;

/**
 * Subsystem component: Air Conditioning System.
 */
public class AirConditioning {

    private final String location;
    private boolean on;
    private int temperature; // In Celsius
    private boolean ecoMode;

    public AirConditioning(String location) {
        this.location = location;
        this.on = false;
        this.temperature = 24; // Default comfortable temp
        this.ecoMode = false;
    }

    public void turnOn() {
        this.on = true;
        System.out.printf("[Subsystem: AC - %s] Turned ON (Temp: %d C, Eco: %s)%n",
                location, temperature, ecoMode ? "Active" : "Disabled");
    }

    public void turnOff() {
        this.on = false;
        System.out.printf("[Subsystem: AC - %s] Turned OFF (Standby)%n", location);
    }

    public void setTemperature(int targetTemp) {
        this.temperature = Math.max(16, Math.min(30, targetTemp));
        System.out.printf("[Subsystem: AC - %s] Temperature set to %d C%n", location, this.temperature);
    }

    public void setEcoMode(boolean enabled) {
        this.ecoMode = enabled;
        if (enabled) {
            // Eco mode automatically optimizes temperature for energy conservation
            this.temperature = Math.max(this.temperature, 26);
            System.out.printf("[Subsystem: AC - %s] ECO Mode ENABLED (Optimized to %d C)%n", location, this.temperature);
        } else {
            System.out.printf("[Subsystem: AC - %s] ECO Mode DISABLED%n", location);
        }
    }

    public boolean isOn() {
        return on;
    }

    public int getTemperature() {
        return temperature;
    }

    public boolean isEcoMode() {
        return ecoMode;
    }

    public String getLocation() {
        return location;
    }

    public String getStatusDescription() {
        if (!on) {
            return "OFF (Standby)";
        }
        return String.format("ON (%d C, %s)", temperature, ecoMode ? "ECO" : "Normal");
    }

    @Override
    public String toString() {
        return String.format("AC [%s]: %s", location, getStatusDescription());
    }
}
