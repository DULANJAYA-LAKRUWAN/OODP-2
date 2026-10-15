package lk.jiat.jms;

/**
 * Memento Component
 * 
 * Captures and stores the internal state of the Originator object.
 * It protects against access by objects other than the Originator,
 * ensuring strict encapsulation.
 */
public class Memento {
    private final String property;

    public Memento(String property) {
        this.property = property;
    }

    public String getProperty() {
        return property;
    }
}

