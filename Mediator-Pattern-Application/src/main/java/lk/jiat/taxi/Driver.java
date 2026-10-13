package lk.jiat.taxi;

/**
 * Concrete Colleague representing a Taxi Driver.
 * Receives trip assignments with customer location through the mediator.
 */
public class Driver extends Colleague {

    private boolean available;
    private String currentCustomer;
    private String currentLocation;

    public Driver(TaxiMediator mediator, String name) {
        super(mediator, name);
        this.available = true; // By default, driver is available
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getCurrentCustomer() {
        return currentCustomer;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    /**
     * Taxi center app sends customer location to the available driver.
     *
     * @param customer The requesting customer.
     * @param location The customer's pickup location.
     */
    public void receiveRideRequest(Customer customer, String location) {
        this.available = false;
        this.currentCustomer = customer.getName();
        this.currentLocation = location;
        System.out.println("[Driver " + name + "] Received ride assignment!");
        System.out.println("   -> Customer Name: " + customer.getName());
        System.out.println("   -> Pickup Location: " + location);
        System.out.println("   -> Status: Heading to pickup location now.");
    }

    /**
     * Simulates completing a trip and becoming available again.
     */
    public void completeTrip() {
        this.available = true;
        this.currentCustomer = null;
        this.currentLocation = null;
        if (mediator instanceof TaxiAppCenter) {
            ((TaxiAppCenter) mediator).notifyTripCompleted(this);
        } else {
            System.out.println("[Driver " + name + "] Completed trip and is now AVAILABLE.");
        }
    }
}
