package com.lakruwan.mediator.taxi.colleague;

import com.lakruwan.mediator.taxi.core.TaxiMediator;

/**
 * Abstract Colleague participant.
 * Maintains a reference to the TaxiMediator.
 */
public abstract class Colleague {
    protected TaxiMediator mediator;
    protected String name;

    public Colleague(TaxiMediator mediator, String name) {
        this.mediator = mediator;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public TaxiMediator getMediator() {
        return mediator;
    }
}
