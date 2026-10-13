package lk.jiat.jms;

/**
 * Mediator Interface for Air Traffic Control.
 */
public interface AirTrafficControl {
    void requestTakeoff(Flight f);
    void requestLanding(Flight f);
    void notifyFlight(String message, Flight f);
}
