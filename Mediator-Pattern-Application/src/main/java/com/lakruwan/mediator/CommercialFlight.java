package com.lakruwan.mediator;

/**
 * Concrete Colleague representing a Commercial Passenger Flight.
 */
public class CommercialFlight implements Plane {
    private final ATCTower mediator;
    private final String callsign;

    public CommercialFlight(ATCTower mediator, String callsign) {
        this.mediator = mediator;
        this.callsign = callsign;
    }

    public CommercialFlight(ATCTower mediator) {
        this(mediator, "Commercial Flight LK-" + (int)(Math.random() * 900 + 100));
    }

    @Override
    public void takeOff() {
        System.out.println("[" + callsign + "] Contacting ATC: Requesting runway for scheduled passenger takeoff.");
        mediator.requestTakeoff(this);
    }

    @Override
    public void landing() {
        System.out.println("[" + callsign + "] Contacting ATC: Inbound on final approach, requesting landing clearance.");
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
        return "Commercial Flight";
    }

    @Override
    public String toString() {
        return callsign + " (Commercial)";
    }
}
