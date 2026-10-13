package lk.jiat.taxi;

/**
 * Concrete Colleague representing a Customer.
 * Requests a taxi through the mediator and receives notifications.
 */
public class Customer extends Colleague {

    public Customer(TaxiMediator mediator, String name) {
        super(mediator, name);
    }

    /**
     * Customer requests a taxi through the taxi center app.
     *
     * @param location The pickup location of the customer.
     */
    public void requestTaxi(String location) {
        System.out.println("[Customer " + name + "] Requesting a taxi from: " + location);
        mediator.requestTaxi(this, location);
    }

    /**
     * Receives update/confirmation message from the taxi center app.
     *
     * @param message Notification message.
     */
    public void receiveNotification(String message) {
        System.out.println("[Customer " + name + " Notification] " + message);
    }
}
