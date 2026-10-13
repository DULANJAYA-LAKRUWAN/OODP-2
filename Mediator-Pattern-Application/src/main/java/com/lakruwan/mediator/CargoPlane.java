package com.lakruwan.mediator;

/**
 * Concrete Colleague representing a Heavy Cargo Freight Aircraft.
 */
public class CargoPlane implements Plane {
    private final ATCTower mediator;
    private final String callsign;

    public CargoPlane(ATCTower mediator, String callsign) {
        this.mediator = mediator;
        this.callsign = callsign;
    }

    public CargoPlane(ATCTower mediator) {
        this(mediator, "Cargo Transport CA-" + (int)(Math.random() * 900 + 100));
    }

    @Override
    public void takeOff() {
        System.out.println("[" + callsign + "] Contacting ATC: Heavy freight aircraft ready, requesting takeoff.");
        mediator.requestTakeoff(this);
    }

    @Override
    public void landing() {
        System.out.println("[" + callsign + "] Contacting ATC: Heavy freight aircraft inbound, requesting landing.");
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
        return "Cargo Aircraft";
    }

    @Override
    public String toString() {
        return callsign + " (Cargo)";
    }
}
