package com.lakruwan.smarthome.subsystem;

/**
 * Subsystem component: Smart Lighting System.
 */
public class Light {

    private final String location;
    private boolean on;
    private int brightness; // 0 - 100 percentage

    public Light(String location) {
        this.location = location;
        this.on = false;
        this.brightness = 0;
    }

    public void turnOn() {
        this.on = true;
        this.brightness = 100;
        System.out.printf("[Subsystem: Light - %s] Turned ON (Brightness: %d%%)%n", location, brightness);
    }

    public void turnOff() {
        this.on = false;
        this.brightness = 0;
        System.out.printf("[Subsystem: Light - %s] Turned OFF%n", location);
    }

    public void dim(int percentage) {
        if (percentage <= 0) {
            turnOff();
            return;
        }
        this.on = true;
        this.brightness = Math.min(percentage, 100);
        System.out.printf("[Subsystem: Light - %s] Dimmed to %d%%%n", location, brightness);
    }

    public boolean isOn() {
        return on;
    }

    public int getBrightness() {
        return brightness;
    }

    public String getLocation() {
        return location;
    }

    public String getStatusDescription() {
        return on ? String.format("ON (%d%%)", brightness) : "OFF";
    }

    @Override
    public String toString() {
        return String.format("Light [%s]: %s", location, getStatusDescription());
    }
}
