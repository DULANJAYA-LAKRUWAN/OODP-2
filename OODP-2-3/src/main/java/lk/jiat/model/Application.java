package lk.jiat.model;

// Abstraction in Bridge Pattern
public abstract class Application {
    protected OperatingSystem os; // Bridge to the Implementor

    public Application(OperatingSystem os) {
        this.os = os;
    }

    public abstract void start();
}

