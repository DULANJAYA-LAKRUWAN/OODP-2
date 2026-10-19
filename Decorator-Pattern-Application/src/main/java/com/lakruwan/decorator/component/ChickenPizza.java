package com.lakruwan.decorator.component;

import com.lakruwan.decorator.Pizza;

/**
 * ConcreteComponent: Chicken Pizza
 * Base pizza price: Rs. 850.00
 */
public class ChickenPizza implements Pizza {

    @Override
    public String getDescription() {
        return "Chicken Pizza";
    }

    @Override
    public double getPrice() {
        return 850.0;
    }
}

