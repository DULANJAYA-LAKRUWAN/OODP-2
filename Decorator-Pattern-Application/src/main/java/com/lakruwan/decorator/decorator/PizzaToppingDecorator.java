package com.lakruwan.decorator.decorator;

import com.lakruwan.decorator.Pizza;

/**
 * Abstract Decorator class matching the 'Decorator' box in the UML diagram.
 * Implements Pizza and maintains a reference to a Component (Pizza) instance (#component : Component).
 */
public abstract class PizzaToppingDecorator implements Pizza {

    /**
     * Protected reference to the wrapped Pizza component (#component : Component).
     */
    protected final Pizza pizza;

    public PizzaToppingDecorator(Pizza pizza) {
        if (pizza == null) {
            throw new IllegalArgumentException("Pizza component to decorate cannot be null.");
        }
        this.pizza = pizza;
    }

    @Override
    public String getDescription() {
        return pizza.getDescription();
    }

    @Override
    public double getPrice() {
        return pizza.getPrice();
    }
}

