package lk.jiat.jms;

/**
 * Abstract Colleague class representing a Flight.
 * Interacts only with the Mediator (AirTrafficControl) instead of other flights.
 */
public abstract class Flight {

    private final AirTrafficControl airTrafficControl;
    private final String number;

    public Flight(AirTrafficControl airTrafficControl, String number) {
        this.airTrafficControl = airTrafficControl;
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void requestTakeoff() {
        airTrafficControl.requestTakeoff(this);
    }

    public void requestLanding() {
        airTrafficControl.requestLanding(this);
    }

    public abstract void receiveMessage(String message);
}
