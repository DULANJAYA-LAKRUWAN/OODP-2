package com.lakruwan.smarthome.command;

import com.lakruwan.smarthome.facade.SmartHomeFacade;

/**
 * Concrete Command: Turn Off Lights.
 */
public class TurnOffLightsCommand implements Command {

    private final SmartHomeFacade facade;
    private SmartHomeFacade.SystemSnapshot prevSnapshot;

    public TurnOffLightsCommand(SmartHomeFacade facade) {
        this.facade = facade;
    }

    @Override
    public void execute() {
        this.prevSnapshot = facade.captureSnapshot();
        System.out.println("\n[Command] Executing: Turn Off Lights");
        facade.turnOffLights();
    }

    @Override
    public void undo() {
        System.out.println("\n[Command] Undoing: Turn Off Lights");
        facade.restoreSnapshot(prevSnapshot);
    }

    @Override
    public String getName() {
        return "Turn Off Lights";
    }
}
