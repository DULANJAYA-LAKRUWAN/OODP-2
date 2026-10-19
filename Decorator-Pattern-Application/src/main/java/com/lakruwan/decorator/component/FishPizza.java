package com.lakruwan.decorator.component;

import com.lakruwan.decorator.Pizza;

/**
 * ConcreteComponent: Fish Pizza
 * Base pizza price: Rs. 720.00
 */
public class FishPizza implements Pizza {

    @Override
    public String getDescription() {
        return "Fish Pizza";
    }

    @Override
    public double getPrice() {
        return 720.0;
    }
}

