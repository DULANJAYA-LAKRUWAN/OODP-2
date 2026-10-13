package lk.jiat.jms;

/**
 * Concrete Colleague class representing Flight Type B.
 */
public class FlightB extends Flight {

    public FlightB(AirTrafficControl airTrafficControl, String number) {
        super(airTrafficControl, number);
    }

    @Override
    public void receiveMessage(String message) {
        System.out.println("Flight B:" + getNumber() + " New message: " + message);
    }
}
