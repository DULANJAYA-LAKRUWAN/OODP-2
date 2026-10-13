package com.lakruwan.mediator.taxi.core;

import com.lakruwan.mediator.taxi.colleague.Customer;
import com.lakruwan.mediator.taxi.colleague.Driver;

/**
 * Mediator Interface for the Taxi Service System.
 * Defines the interaction contract between Customers and Drivers.
 */
public interface TaxiMediator {
    void registerDriver(Driver driver);
    void registerCustomer(Customer customer);
    void requestTaxi(Customer customer, String location);
}
