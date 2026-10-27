package com.lakruwan.smarthome.command;

import com.lakruwan.smarthome.facade.SmartHomeFacade;

/**
 * Concrete Command: Turn On Lights.
 * Invoked when the user presses the "Turn On Lights" button.
 */
public class TurnOnLightsCommand implements Command {

    private final SmartHomeFacade facade;
    private SmartHomeFacade.SystemSnapshot prevSnapshot;

    public TurnOnLightsCommand(SmartHomeFacade facade) {
        this.facade = facade;
    }

    @Override
    public void execute() {
        this.prevSnapshot = facade.captureSnapshot();
        System.out.println("\n[Command] Executing: Turn On Lights");
        facade.turnOnLights();
    }

    @Override
    public void undo() {
        System.out.println("\n[Command] Undoing: Turn On Lights");
        facade.restoreSnapshot(prevSnapshot);
    }

    @Override
    public String getName() {
        return "Turn On Lights";
    }
}
