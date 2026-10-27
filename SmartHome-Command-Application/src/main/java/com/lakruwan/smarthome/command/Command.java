package com.lakruwan.smarthome.command;

/**
 * Command Pattern Interface.
 * Encapsulates a request as an object, thereby letting you parameterize clients
 * with different requests, queue or log requests, and support undoable operations.
 */
public interface Command {

    /**
     * Executes the command.
     */
    void execute();

    /**
     * Reverses the effect of the command.
     */
    void undo();

    /**
     * Returns a human-readable display name for the command.
     */
    String getName();
}
