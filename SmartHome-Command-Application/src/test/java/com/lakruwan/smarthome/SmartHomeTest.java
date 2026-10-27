package com.lakruwan.smarthome;

import com.lakruwan.smarthome.command.*;
import com.lakruwan.smarthome.facade.SmartHomeFacade;
import com.lakruwan.smarthome.invoker.SmartButtonPanel;
import com.lakruwan.smarthome.subsystem.AirConditioning;
import com.lakruwan.smarthome.subsystem.Light;
import com.lakruwan.smarthome.subsystem.SecurityAlarm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests verifying the Command & Facade pattern implementations.
 */
public class SmartHomeTest {

    private Light light;
    private AirConditioning ac;
    private SecurityAlarm alarm;
    private SmartHomeFacade facade;
    private SmartButtonPanel panel;

    @BeforeEach
    public void setUp() {
        light = new Light("Living Room");
        ac = new AirConditioning("HVAC");
        alarm = new SecurityAlarm("Perimeter");
        facade = new SmartHomeFacade(light, ac, alarm);
        panel = new SmartButtonPanel();

        // Wire commands
        panel.setCommand(SmartButtonPanel.SLOT_LIGHTS_ON, new TurnOnLightsCommand(facade));
        panel.setCommand(SmartButtonPanel.SLOT_ENERGY_SAVING, new EnergySavingScheduleCommand(facade));
        panel.setCommand(SmartButtonPanel.SLOT_AWAY_MODE, new ActivateAwayModeCommand(facade));
        panel.setCommand(SmartButtonPanel.SLOT_HOME_MODE, new ActivateHomeModeCommand(facade));
        panel.setCommand(SmartButtonPanel.SLOT_LIGHTS_OFF, new TurnOffLightsCommand(facade));
    }

    @Test
    @DisplayName("Turn On Lights command turns light ON at 100% and undo restores state")
    public void testTurnOnLightsCommandAndUndo() {
        assertFalse(light.isOn(), "Light should initially be OFF");

        panel.pressTurnOnLights();

        assertTrue(light.isOn(), "Light should be ON");
        assertEquals(100, light.getBrightness(), "Brightness should be 100%");

        // Undo
        panel.pressUndoButton();
        assertFalse(light.isOn(), "Light should be restored to OFF after undo");
    }

    @Test
    @DisplayName("Energy-Saving Schedule dims lights, sets AC to Eco 26C, arms alarm in stay mode")
    public void testEnergySavingScheduleCommandAndUndo() {
        panel.pressEnergySavingSchedule();

        assertTrue(light.isOn(), "Light should be ON in energy saving mode");
        assertEquals(30, light.getBrightness(), "Light brightness should be dimmed to 30%");

        assertTrue(ac.isOn(), "AC should be ON");
        assertTrue(ac.isEcoMode(), "AC should have Eco Mode enabled");
        assertEquals(26, ac.getTemperature(), "AC temperature should be optimized to 26C");

        assertTrue(alarm.isArmed(), "Alarm should be armed");
        assertEquals(SecurityAlarm.Mode.ARMED_STAY, alarm.getMode(), "Alarm mode should be ARMED_STAY");

        // Undo
        panel.pressUndoButton();
        assertFalse(light.isOn(), "Light should be restored to initial state (OFF)");
        assertFalse(ac.isOn(), "AC should be restored to initial state (OFF)");
        assertEquals(SecurityAlarm.Mode.DISARMED, alarm.getMode(), "Alarm should be restored to DISARMED");
    }

    @Test
    @DisplayName("Activate Away Mode turns off lights, powers down AC, and arms alarm in Away mode")
    public void testActivateAwayModeCommandAndUndo() {
        // First turn on light
        panel.pressTurnOnLights();
        assertTrue(light.isOn());

        // Now activate away mode
        panel.pressAwayMode();

        assertFalse(light.isOn(), "Lights must be turned OFF in Away Mode");
        assertFalse(ac.isOn(), "AC must be turned OFF/Standby in Away Mode");
        assertTrue(alarm.isArmed(), "Alarm must be ARMED in Away Mode");
        assertEquals(SecurityAlarm.Mode.ARMED_AWAY, alarm.getMode(), "Alarm mode should be ARMED_AWAY");

        // Undo
        panel.pressUndoButton();
        assertTrue(light.isOn(), "Lights should revert back to ON from snapshot");
    }

    @Test
    @DisplayName("MacroCommand executes and reverses batch commands in composite pattern")
    public void testMacroCommand() {
        Command macro = new MacroCommand("Custom Good Night Sequence", List.of(
                new TurnOffLightsCommand(facade),
                new EnergySavingScheduleCommand(facade)
        ));

        macro.execute();
        assertTrue(light.isOn());
        assertEquals(30, light.getBrightness());
        assertTrue(ac.isEcoMode());

        macro.undo();
        assertFalse(light.isOn());
        assertFalse(ac.isOn());
    }

    @Test
    @DisplayName("Invoker maintains history stack and reports undo capability correctly")
    public void testInvokerHistory() {
        assertFalse(panel.canUndo(), "Initial undo stack must be empty");

        panel.pressTurnOnLights();
        assertTrue(panel.canUndo(), "Undo should be available after command execution");
        assertEquals("Turn On Lights", panel.getLastCommandName());

        panel.pressUndoButton();
        assertFalse(panel.canUndo(), "Undo stack should be empty after single undo");
    }
}
