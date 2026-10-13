package lk.jiat.jms;

/**
 * Concrete Colleague class representing Flight Type A.
 */
public class FlightA extends Flight {

    public FlightA(AirTrafficControl airTrafficControl, String number) {
        super(airTrafficControl, number);
    }

    @Override
    public void receiveMessage(String message) {
        System.out.println("Flight A:" + getNumber() + " New message: " + message);
    }
}
