package com.lakruwan.decorator.decorator;

import com.lakruwan.decorator.Pizza;

/**
 * ConcreteDecorator: Swiss Cheese
 * Extra topping price: Rs. 80.00
 */
public class SwissCheese extends PizzaToppingDecorator {

    public SwissCheese(Pizza pizza) {
        super(pizza);
    }

    @Override
    public String getDescription() {
        return pizza.getDescription() + ", Swiss Cheese";
    }

    @Override
    public double getPrice() {
        return pizza.getPrice() + 80.0;
    }
}

