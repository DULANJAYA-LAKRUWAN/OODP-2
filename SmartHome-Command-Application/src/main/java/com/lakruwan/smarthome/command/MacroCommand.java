package com.lakruwan.smarthome.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Macro Command (Composite Command).
 * Enables grouping multiple commands into a single compound action.
 */
public class MacroCommand implements Command {

    private final String name;
    private final List<Command> commands;

    public MacroCommand(String name, List<Command> commands) {
        this.name = name;
        this.commands = commands != null ? new ArrayList<>(commands) : new ArrayList<>();
    }

    @Override
    public void execute() {
        System.out.printf("%n[MacroCommand: %s] Executing batch sequence (%d commands)...%n", name, commands.size());
        for (Command cmd : commands) {
            cmd.execute();
        }
    }

    @Override
    public void undo() {
        System.out.printf("%n[MacroCommand: %s] Undoing batch sequence in reverse order...%n", name);
        List<Command> reverseList = new ArrayList<>(commands);
        Collections.reverse(reverseList);
        for (Command cmd : reverseList) {
            cmd.undo();
        }
    }

    @Override
    public String getName() {
        return name;
    }
}
