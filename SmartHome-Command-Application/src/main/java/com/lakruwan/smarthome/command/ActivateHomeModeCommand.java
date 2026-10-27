package com.lakruwan.smarthome.command;

import com.lakruwan.smarthome.facade.SmartHomeFacade;

/**
 * Concrete Command: Activate Home Mode.
 */
public class ActivateHomeModeCommand implements Command {

    private final SmartHomeFacade facade;
    private SmartHomeFacade.SystemSnapshot prevSnapshot;

    public ActivateHomeModeCommand(SmartHomeFacade facade) {
        this.facade = facade;
    }

    @Override
    public void execute() {
        this.prevSnapshot = facade.captureSnapshot();
        System.out.println("\n[Command] Executing: Activate Home Mode");
        facade.activateHomeMode();
    }

    @Override
    public void undo() {
        System.out.println("\n[Command] Undoing: Home Mode");
        facade.restoreSnapshot(prevSnapshot);
    }

    @Override
    public String getName() {
        return "Activate Home Mode";
    }
}
