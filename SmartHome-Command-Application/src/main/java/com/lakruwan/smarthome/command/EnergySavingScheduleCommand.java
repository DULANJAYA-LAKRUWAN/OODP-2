package com.lakruwan.smarthome.command;

import com.lakruwan.smarthome.facade.SmartHomeFacade;

/**
 * Concrete Command: Create Energy-Saving Schedule.
 * Invoked when the user presses the "Create Energy-Saving Schedule" button.
 */
public class EnergySavingScheduleCommand implements Command {

    private final SmartHomeFacade facade;
    private SmartHomeFacade.SystemSnapshot prevSnapshot;

    public EnergySavingScheduleCommand(SmartHomeFacade facade) {
        this.facade = facade;
    }

    @Override
    public void execute() {
        this.prevSnapshot = facade.captureSnapshot();
        System.out.println("\n[Command] Executing: Create Energy-Saving Schedule");
        facade.setEnergySavingSchedule();
    }

    @Override
    public void undo() {
        System.out.println("\n[Command] Undoing: Energy-Saving Schedule");
        facade.restoreSnapshot(prevSnapshot);
    }

    @Override
    public String getName() {
        return "Energy-Saving Schedule";
    }
}
