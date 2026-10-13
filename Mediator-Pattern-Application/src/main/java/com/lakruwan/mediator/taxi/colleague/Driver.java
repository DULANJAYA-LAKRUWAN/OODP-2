package com.lakruwan.mediator.taxi.colleague;

import com.lakruwan.mediator.taxi.core.TaxiAppCenter;
import com.lakruwan.mediator.taxi.core.TaxiMediator;

/**
 * Concrete Colleague representing a Taxi Driver.
 * Dispatched by the TaxiAppCenter mediator.
 */
public class Driver extends Colleague {

    private boolean available;
    private String currentCustomer;
    private String currentLocation;

    public Driver(TaxiMediator mediator, String name) {
        super(mediator, name);
        this.available = true;
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
     * Receives ride assignment and passenger location from the mediator.
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
     * Completes trip and restores available state through the mediator.
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
