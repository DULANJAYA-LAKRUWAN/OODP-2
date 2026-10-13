package com.lakruwan.mediator.taxi.colleague;

import com.lakruwan.mediator.taxi.core.TaxiMediator;

/**
 * Concrete Colleague representing a Customer.
 * Communicates exclusively through the TaxiMediator.
 */
public class Customer extends Colleague {

    public Customer(TaxiMediator mediator, String name) {
        super(mediator, name);
    }

    /**
     * Customer requests a taxi through the taxi center app.
     */
    public void requestTaxi(String location) {
        System.out.println("[Customer " + name + "] Requesting a taxi from: " + location);
        mediator.requestTaxi(this, location);
    }

    /**
     * Receives dispatch notifications from the taxi center app.
     */
    public void receiveNotification(String message) {
        System.out.println("[Customer " + name + " Notification] " + message);
    }
}
