package com.lakruwan.decorator.decorator;

import com.lakruwan.decorator.Pizza;

/**
 * ConcreteDecorator: Fried Onions
 * Extra topping price: Rs. 20.00
 */
public class FriedOnions extends PizzaToppingDecorator {

    public FriedOnions(Pizza pizza) {
        super(pizza);
    }

    @Override
    public String getDescription() {
        return pizza.getDescription() + ", Fried Onions";
    }

    @Override
    public double getPrice() {
        return pizza.getPrice() + 20.0;
    }
}

