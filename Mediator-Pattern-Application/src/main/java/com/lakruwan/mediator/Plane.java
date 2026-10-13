package com.lakruwan.mediator;

/**
 * Colleague Interface representing an Aircraft in the Airport System.
 * Corresponds to the Plane interface from the course curriculum.
 */
public interface Plane {
    void takeOff();
    void landing();
    void notifyATC(String message);
    void receiveATCInstruction(String message);
    String getCallsign();
    String getAircraftType();
}
