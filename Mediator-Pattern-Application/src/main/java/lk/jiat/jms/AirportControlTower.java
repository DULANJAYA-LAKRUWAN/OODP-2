package lk.jiat.jms;

/**
 * Concrete Mediator representing the Airport Control Tower.
 * Centralizes communication and traffic decisions among flights.
 */
public class AirportControlTower implements AirTrafficControl {

    @Override
    public void requestTakeoff(Flight f) {
        System.out.println("Flight: " + f.getNumber() + " request permission to take off");
        notifyFlight("Take-off approved", f);
    }

    @Override
    public void requestLanding(Flight f) {
        System.out.println("Flight: " + f.getNumber() + " request permission to landing");
        notifyFlight("Landing approved", f);
    }

    @Override
    public void notifyFlight(String message, Flight f) {
        f.receiveMessage(message);
    }
}
