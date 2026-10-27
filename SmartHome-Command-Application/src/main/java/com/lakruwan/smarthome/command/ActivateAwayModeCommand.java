package com.lakruwan.smarthome.command;

import com.lakruwan.smarthome.facade.SmartHomeFacade;

/**
 * Concrete Command: Activate Away Mode.
 * Invoked when the user presses the "Activate Away Mode" button.
 */
public class ActivateAwayModeCommand implements Command {

    private final SmartHomeFacade facade;
    private SmartHomeFacade.SystemSnapshot prevSnapshot;

    public ActivateAwayModeCommand(SmartHomeFacade facade) {
        this.facade = facade;
    }

    @Override
    public void execute() {
        this.prevSnapshot = facade.captureSnapshot();
        System.out.println("\n[Command] Executing: Activate Away Mode");
        facade.activateAwayMode();
    }

    @Override
    public void undo() {
        System.out.println("\n[Command] Undoing: Away Mode (Restoring previous state)");
        facade.restoreSnapshot(prevSnapshot);
    }

    @Override
    public String getName() {
        return "Activate Away Mode";
    }
}
