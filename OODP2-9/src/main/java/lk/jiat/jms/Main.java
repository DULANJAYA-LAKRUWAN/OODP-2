package lk.jiat.jms;

import java.util.Stack;

// Memento: Stores the internal state of the Originator without exposing details
class Memento {
    private final String property;

    public Memento(String property) {
        this.property = property;
    }

    public String getProperty() {
        return property;
    }
}

// Originator: Represents the File whose state can change and needs to be saved/restored
class Originator {
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

    // Creates a Memento containing a snapshot of the current state
    public Memento save() {
        return new Memento(this.property);
    }

    // Restores internal state from a given Memento
    public void restore(Memento memento) {
        if (memento != null) {
            this.property = memento.getProperty();
        }
    }
}

// Caretaker: Manages the stack of Mementos without inspecting their contents
class Caretaker {
    private final Stack<Memento> mementoStack = new Stack<>();

    // Saves the originator's current state to the stack
    public void save(Originator originator) {
        mementoStack.push(originator.save());
        System.out.println("[Caretaker] State saved to Memento stack. (Stack size: " + mementoStack.size() + ")");
    }

    // Restores the originator's state to the last saved state
    public void load(Originator originator) {
        if (!mementoStack.isEmpty()) {
            Memento memento = mementoStack.pop();
            originator.restore(memento);
            System.out.println("[Caretaker] State restored from Memento. (Stack size: " + mementoStack.size() + ")");
        } else {
            System.out.println("[Caretaker] No saved state found in stack to restore!");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("    Practical 09: Memento Pattern Demonstration   ");
        System.out.println("==================================================");

        Caretaker caretaker = new Caretaker();

        // 1. Creating a file (Originator with initial property / content)
        System.out.println("\n--- Step 1: Creating a File ---");
        Originator file = new Originator("File: document.txt | Content: Hello, World!");
        System.out.println("Created File State: " + file.getProperty());

        // 2. Write and save contents to the File
        System.out.println("\n--- Step 2: Write and Save Contents to the File ---");
        
        // Save initial state
        caretaker.save(file);

        // Write new content
        file.setProperty("File: document.txt | Content: Hello, World! Practical 09 Memento Pattern.");
        System.out.println("Updated File State: " + file.getProperty());
        // Save state again
        caretaker.save(file);

        // Write another update
        file.setProperty("File: document.txt | Content: Hello, World! Practical 09 Memento Pattern. Added File History.");
        System.out.println("Updated File State: " + file.getProperty());
        // Save state again
        caretaker.save(file);

        // An accidental edit / corruption
        System.out.println("\n--- Accidental modification ---");
        file.setProperty("File: document.txt | Content: [CORRUPTED / ACCIDENTAL OVERWRITE]");
        System.out.println("Current File State: " + file.getProperty());

        // 3. Restore it to last saved state
        System.out.println("\n--- Step 3: Restore to Last Saved State ---");
        caretaker.load(file);
        System.out.println("Restored File State: " + file.getProperty());

        System.out.println("\n--- Restoring to previous saved state again ---");
        caretaker.load(file);
        System.out.println("Restored File State: " + file.getProperty());

        System.out.println("\n--- Restoring to initial saved state ---");
        caretaker.load(file);
        System.out.println("Restored File State: " + file.getProperty());

        System.out.println("\n--- Attempting to restore when stack is empty ---");
        caretaker.load(file);

        System.out.println("\n==================================================");
    }
}

