package com.lakruwan.smarthome.invoker;

import com.lakruwan.smarthome.command.Command;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

/**
 * Invoker: Smart Button Panel / Remote Controller.
 * Decouples the user's button presses from the underlying subsystem execution.
 * Maintains an undo history stack.
 */
public class SmartButtonPanel {

    public static final int SLOT_LIGHTS_ON = 0;
    public static final int SLOT_ENERGY_SAVING = 1;
    public static final int SLOT_AWAY_MODE = 2;
    public static final int SLOT_HOME_MODE = 3;
    public static final int SLOT_LIGHTS_OFF = 4;

    private final Map<Integer, Command> buttonSlots = new HashMap<>();
    private final Deque<Command> undoStack = new ArrayDeque<>();

    public SmartButtonPanel() {
        // Initialize with No-Op command
        Command noOp = new Command() {
            @Override
            public void execute() {
                System.out.println("[Invoker] Button slot is not assigned.");
            }

            @Override
            public void undo() {
                // No action
            }

            @Override
            public String getName() {
                return "Unassigned";
            }
        };

        for (int i = 0; i < 6; i++) {
            buttonSlots.put(i, noOp);
        }
    }

    /**
     * Program a button slot with a Command.
     */
    public void setCommand(int slot, Command command) {
        if (command != null) {
            buttonSlots.put(slot, command);
        }
    }

    /**
     * User presses a button on the smart control panel.
     */
    public void pressButton(int slot) {
        Command command = buttonSlots.get(slot);
        if (command != null) {
            System.out.printf("%n>>> [Invoker] USER PRESSED BUTTON: %s (Slot %d)%n", command.getName(), slot);
            command.execute();
            undoStack.push(command);
        }
    }

    /**
     * User presses the Undo button to revert the last executed command.
     */
    public void pressUndoButton() {
        if (!undoStack.isEmpty()) {
            Command lastCommand = undoStack.pop();
            System.out.printf("%n<<< [Invoker] USER PRESSED UNDO BUTTON for: %s%n", lastCommand.getName());
            lastCommand.undo();
        } else {
            System.out.println("\n[Invoker] Undo history is empty. Nothing to undo.");
        }
    }

    // Convenience button triggers matching assignment requirements
    public void pressTurnOnLights() {
        pressButton(SLOT_LIGHTS_ON);
    }

    public void pressEnergySavingSchedule() {
        pressButton(SLOT_ENERGY_SAVING);
    }

    public void pressAwayMode() {
        pressButton(SLOT_AWAY_MODE);
    }

    public void pressHomeMode() {
        pressButton(SLOT_HOME_MODE);
    }

    public void pressTurnOffLights() {
        pressButton(SLOT_LIGHTS_OFF);
    }

    public boolean canUndo() {
        return !undoStack.isEmpty();
    }

    public String getLastCommandName() {
        return undoStack.isEmpty() ? "None" : undoStack.peek().getName();
    }
}
