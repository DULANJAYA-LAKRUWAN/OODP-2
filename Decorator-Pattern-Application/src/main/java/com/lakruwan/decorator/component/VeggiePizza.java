package com.lakruwan.decorator.component;

import com.lakruwan.decorator.Pizza;

/**
 * ConcreteComponent: Veggie Pizza
 * Base pizza price: Rs. 650.00
 */
public class VeggiePizza implements Pizza {

    @Override
    public String getDescription() {
        return "Veggie Pizza";
    }

    @Override
    public double getPrice() {
        return 650.0;
    }
}

