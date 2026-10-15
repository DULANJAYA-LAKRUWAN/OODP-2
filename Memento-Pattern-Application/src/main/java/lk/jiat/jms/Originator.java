package lk.jiat.jms;

/**
 * Originator Component (Represents the File)
 * 
 * The object whose internal state needs to be saved and restored.
 * It creates a Memento containing a snapshot of its current state,
 * and restores its state from a Memento.
 */
public class Originator {
    private String property;

    public Originator() {
    }

    public Originator(String property) {
        this.property = property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getProperty() {
        return property;
    }

    /**
     * Creates a new Memento containing a snapshot of the current state.
     */
    public Memento save() {
        return new Memento(this.property);
    }

    /**
     * Restores state from a given Memento snapshot.
     */
    public void restore(Memento memento) {
        if (memento != null) {
            this.property = memento.getProperty();
        }
    }

    @Override
    public String toString() {
        return "File [property=" + property + "]";
    }
}

