package com.lakruwan.decorator.decorator;

import com.lakruwan.decorator.Pizza;

/**
 * ConcreteDecorator: Mayonnaise
 * Extra topping price: Rs. 35.00
 */
public class Mayonnaise extends PizzaToppingDecorator {

    public Mayonnaise(Pizza pizza) {
        super(pizza);
    }

    @Override
    public String getDescription() {
        return pizza.getDescription() + ", Mayonnaise";
    }

    @Override
    public double getPrice() {
        return pizza.getPrice() + 35.0;
    }
}

