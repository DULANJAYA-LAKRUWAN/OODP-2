package lk.jiat.jms;

import java.util.Stack;

/**
 * Caretaker Component
 * 
 * Responsible for keeping the Mementos safe (in a Stack).
 * It never examines or tampers with the contents of the Memento.
 * It initiates save and load requests on the Originator.
 */
public class Caretaker {
    private final Stack<Memento> mementoStack = new Stack<>();

    /**
     * Saves the current state of the originator onto the stack.
     * @param originator the Originator whose state is to be saved
     */
    public void save(Originator originator) {
        if (originator != null) {
            Memento memento = originator.save();
            mementoStack.push(memento);
            System.out.println("[Caretaker] Successfully saved state to Memento stack. (Current stack size: " + mementoStack.size() + ")");
        }
    }

    /**
     * Restores the originator's state to the last saved snapshot.
     * @param originator the Originator to restore
     */
    public void load(Originator originator) {
        if (originator == null) {
            System.out.println("[Caretaker] Cannot restore: Originator is null.");
            return;
        }

        if (!mementoStack.isEmpty()) {
            Memento memento = mementoStack.pop();
            originator.restore(memento);
            System.out.println("[Caretaker] Successfully restored state from Memento. (Remaining stack size: " + mementoStack.size() + ")");
        } else {
            System.out.println("[Caretaker] Error: No saved states in stack to restore!");
        }
    }

    public boolean hasSavedStates() {
        return !mementoStack.isEmpty();
    }

    public int getStackSize() {
        return mementoStack.size();
    }
}

