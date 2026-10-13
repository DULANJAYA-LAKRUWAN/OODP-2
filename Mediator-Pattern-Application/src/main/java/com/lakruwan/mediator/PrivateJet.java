package com.lakruwan.mediator;

/**
 * Concrete Colleague representing a Private Jet aircraft.
 */
public class PrivateJet implements Plane {
    private final ATCTower mediator;
    private final String callsign;

    public PrivateJet(ATCTower mediator, String callsign) {
        this.mediator = mediator;
        this.callsign = callsign;
    }

    public PrivateJet(ATCTower mediator) {
        this(mediator, "Private Jet PJ-" + (int)(Math.random() * 900 + 100));
    }

    @Override
    public void takeOff() {
        System.out.println("[" + callsign + "] Contacting ATC: Requesting clearance to take off.");
        mediator.requestTakeoff(this);
    }

    @Override
    public void landing() {
        System.out.println("[" + callsign + "] Contacting ATC: Requesting clearance to land.");
        mediator.requestLanding(this);
    }

    @Override
    public void notifyATC(String message) {
        System.out.println("[" + callsign + "] Transmission to Tower: " + message);
    }

    @Override
    public void receiveATCInstruction(String message) {
        System.out.println("[" + callsign + "] Radio Ack from Tower: \"" + message + "\"");
    }

    @Override
    public String getCallsign() {
        return callsign;
    }

    @Override
    public String getAircraftType() {
        return "Private Jet";
    }

    @Override
    public String toString() {
        return callsign + " (Private Jet)";
    }
}
