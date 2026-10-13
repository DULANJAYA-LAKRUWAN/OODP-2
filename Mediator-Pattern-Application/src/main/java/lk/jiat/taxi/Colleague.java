package lk.jiat.taxi;

/**
 * Abstract Colleague class.
 * Holds a reference to the Mediator so concrete colleagues
 * can communicate without directly referencing each other.
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
}
