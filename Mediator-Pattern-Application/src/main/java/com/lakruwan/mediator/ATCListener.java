package com.lakruwan.mediator;

/**
 * Listener interface for Air Traffic Control events (used for UI updates and logging).
 */
public interface ATCListener {
    void onATCMessage(String source, String message, boolean isAlert);
    void onRunwayStatusChanged(String runwayName, String status, String planeCallsign);
    void onPlaneRegistered(Plane plane);
}
