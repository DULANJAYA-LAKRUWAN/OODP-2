package lk.jiat.jms;

/**
 * Main Class - Practical 09 Demonstration
 * 
 * Demonstrates the File restoring process using the Memento Design Pattern:
 * 1. Creating a file (Originator with initial property/content)
 * 2. Writing and saving contents to the file (Caretaker.save)
 * 3. Restoring it to the last saved state (Caretaker.load)
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("===============================================================");
        System.out.println("      Practical 09: File Restoring Process (Memento Pattern)    ");
        System.out.println("===============================================================");

        // Initialize Caretaker
        Caretaker caretaker = new Caretaker();

        // -----------------------------------------------------------------
        // Step 1: Creating a file
        // -----------------------------------------------------------------
        System.out.println("\n[1] Creating a file...");
        Originator file = new Originator("Assignment_01.txt | Version 1.0: Title and Introduction written.");
        System.out.println("--> Initial File State: " + file.getProperty());

        // -----------------------------------------------------------------
        // Step 2: Write and save contents to the File
        // -----------------------------------------------------------------
        System.out.println("\n[2] Writing and saving contents to the File...");

        // Save State 1
        System.out.println("\n--- Saving Initial State (State 1) ---");
        caretaker.save(file);

        // Write update 1
        System.out.println("\n--- Writing update: Added Section 1 (State 2) ---");
        file.setProperty("Assignment_01.txt | Version 1.1: Section 1 - Memento Pattern Overview added.");
        System.out.println("--> Current File State: " + file.getProperty());
        caretaker.save(file);

        // Write update 2
        System.out.println("\n--- Writing update: Added Section 2 (State 3) ---");
        file.setProperty("Assignment_01.txt | Version 1.2: Section 2 - Originator, Memento, Caretaker implemented.");
        System.out.println("--> Current File State: " + file.getProperty());
        caretaker.save(file);

        // Simulate an unwanted or corrupted change
        System.out.println("\n--- Unwanted Modification / Accidental Overwrite! ---");
        file.setProperty("Assignment_01.txt | [CORRUPTED: Entire document text mistakenly erased!]");
        System.out.println("--> Current Corrupted State: " + file.getProperty());

        // -----------------------------------------------------------------
        // Step 3: Restore it to last saved state
        // -----------------------------------------------------------------
        System.out.println("\n[3] Restoring File to last saved state...");
        caretaker.load(file);
        System.out.println("--> Restored File State: " + file.getProperty());

        System.out.println("\n--- Restoring File to previous state once more (Undo) ---");
        caretaker.load(file);
        System.out.println("--> Restored File State: " + file.getProperty());

        System.out.println("\n--- Restoring File back to Initial State ---");
        caretaker.load(file);
        System.out.println("--> Restored File State: " + file.getProperty());

        System.out.println("\n--- Attempting to restore when history is empty ---");
        caretaker.load(file);

        System.out.println("\n===============================================================");
        System.out.println("             Memento Pattern Execution Completed               ");
        System.out.println("===============================================================");
    }
}

