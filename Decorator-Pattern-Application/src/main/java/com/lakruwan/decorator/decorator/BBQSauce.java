package com.lakruwan.decorator.decorator;

import com.lakruwan.decorator.Pizza;

/**
 * ConcreteDecorator: BBQ Sauce
 * Extra topping price: Rs. 50.00
 */
public class BBQSauce extends PizzaToppingDecorator {

    public BBQSauce(Pizza pizza) {
        super(pizza);
    }

    @Override
    public String getDescription() {
        return pizza.getDescription() + ", BBQ Sauce";
    }

    @Override
    public double getPrice() {
        return pizza.getPrice() + 50.0;
    }
}

