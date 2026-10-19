package com.lakruwan.decorator;

/**
 * Component Interface matching <<interface>> Component in the Decorator Pattern UML.
 * Defines the contract for all pizzas (both plain base pizzas and decorated pizzas).
 */
public interface Pizza {
    /**
     * Returns the description of the pizza including its base and all attached toppings.
     *
     * @return String description
     */
    String getDescription();

    /**
     * Calculates and returns the total price of the pizza with all applied toppings.
     *
     * @return double total price in LKR (Rs.)
     */
    double getPrice();
}

