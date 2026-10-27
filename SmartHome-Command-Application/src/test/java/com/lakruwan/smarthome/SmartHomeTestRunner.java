package com.lakruwan.smarthome;

import com.lakruwan.smarthome.command.*;
import com.lakruwan.smarthome.facade.SmartHomeFacade;
import com.lakruwan.smarthome.invoker.SmartButtonPanel;
import com.lakruwan.smarthome.subsystem.AirConditioning;
import com.lakruwan.smarthome.subsystem.Light;
import com.lakruwan.smarthome.subsystem.SecurityAlarm;

import java.util.List;

/**
 * Standalone Test Runner that executes without external dependencies.
 */
public class SmartHomeTestRunner {

    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {
        System.out.println("===============================================================");
        System.out.println("  RUNNING SMART HOME AUTOMATION TEST SUITE");
        System.out.println("===============================================================");

        testTurnOnLightsCommand();
        testEnergySavingScheduleCommand();
        testActivateAwayModeCommand();
        testMacroCommand();
        testInvokerUndoStack();

        System.out.println("\n---------------------------------------------------------------");
        System.out.printf("Test Results: %d Passed, %d Failed%n", passed, failed);
        System.out.println("===============================================================");
        if (failed > 0) {
            System.exit(1);
        }
    }

    private static void assertEquals(Object expected, Object actual, String msg) {
        if ((expected == null && actual == null) || (expected != null && expected.equals(actual))) {
            return;
        }
        throw new AssertionError(String.format("%s: expected [%s] but found [%s]", msg, expected, actual));
    }

    private static void assertTrue(boolean condition, String msg) {
        if (!condition) {
            throw new AssertionError(msg + ": expected [true] but was [false]");
        }
    }

    private static void assertFalse(boolean condition, String msg) {
        if (condition) {
            throw new AssertionError(msg + ": expected [false] but was [true]");
        }
    }

    private static void runTest(String name, Runnable test) {
        try {
            test.run();
            System.out.printf(" [PASS] %s%n", name);
            passed++;
        } catch (Throwable t) {
            System.out.printf(" [FAIL] %s - %s%n", name, t.getMessage());
            failed++;
        }
    }

    private static void testTurnOnLightsCommand() {
        runTest("Turn On Lights Command & Undo", () -> {
            Light light = new Light("Living Room");
            SmartHomeFacade facade = new SmartHomeFacade(light, null, null);
            SmartButtonPanel panel = new SmartButtonPanel();
            panel.setCommand(SmartButtonPanel.SLOT_LIGHTS_ON, new TurnOnLightsCommand(facade));

            assertFalse(light.isOn(), "Light should start OFF");
            panel.pressTurnOnLights();
            assertTrue(light.isOn(), "Light should be ON");
            assertEquals(100, light.getBrightness(), "Brightness should be 100%");

            panel.pressUndoButton();
            assertFalse(light.isOn(), "Light should revert to OFF");
        });
    }

    private static void testEnergySavingScheduleCommand() {
        runTest("Energy-Saving Schedule Command & Undo", () -> {
            Light light = new Light("Living Room");
            AirConditioning ac = new AirConditioning("HVAC");
            SecurityAlarm alarm = new SecurityAlarm("Zone 1");
            SmartHomeFacade facade = new SmartHomeFacade(light, ac, alarm);
            SmartButtonPanel panel = new SmartButtonPanel();
            panel.setCommand(SmartButtonPanel.SLOT_ENERGY_SAVING, new EnergySavingScheduleCommand(facade));

            panel.pressEnergySavingSchedule();
            assertTrue(light.isOn(), "Light should be on in energy saving");
            assertEquals(30, light.getBrightness(), "Light brightness should be 30%");
            assertTrue(ac.isOn(), "AC should be on");
            assertTrue(ac.isEcoMode(), "AC Eco Mode should be true");
            assertEquals(26, ac.getTemperature(), "AC temperature should be 26C");
            assertTrue(alarm.isArmed(), "Alarm should be armed");

            panel.pressUndoButton();
            assertFalse(light.isOn(), "Light should revert to OFF");
            assertFalse(ac.isOn(), "AC should revert to OFF");
            assertEquals(SecurityAlarm.Mode.DISARMED, alarm.getMode(), "Alarm should be disarmed");
        });
    }

    private static void testActivateAwayModeCommand() {
        runTest("Activate Away Mode Command & Undo", () -> {
            Light light = new Light("Living Room");
            AirConditioning ac = new AirConditioning("HVAC");
            SecurityAlarm alarm = new SecurityAlarm("Zone 1");
            SmartHomeFacade facade = new SmartHomeFacade(light, ac, alarm);
            SmartButtonPanel panel = new SmartButtonPanel();
            panel.setCommand(SmartButtonPanel.SLOT_LIGHTS_ON, new TurnOnLightsCommand(facade));
            panel.setCommand(SmartButtonPanel.SLOT_AWAY_MODE, new ActivateAwayModeCommand(facade));

            panel.pressTurnOnLights();
            panel.pressAwayMode();

            assertFalse(light.isOn(), "Lights must be OFF in Away Mode");
            assertFalse(ac.isOn(), "AC must be OFF in Away Mode");
            assertTrue(alarm.isArmed(), "Security alarm must be armed in Away Mode");
            assertEquals(SecurityAlarm.Mode.ARMED_AWAY, alarm.getMode(), "Alarm mode must be ARMED_AWAY");

            panel.pressUndoButton();
            assertTrue(light.isOn(), "Lights should revert back to ON");
        });
    }

    private static void testMacroCommand() {
        runTest("Macro / Composite Command Execution & Reverse Undo", () -> {
            Light light = new Light("Living Room");
            AirConditioning ac = new AirConditioning("HVAC");
            SmartHomeFacade facade = new SmartHomeFacade(light, ac, null);

            Command macro = new MacroCommand("Night Automation", List.of(
                    new TurnOffLightsCommand(facade),
                    new EnergySavingScheduleCommand(facade)
            ));

            macro.execute();
            assertTrue(light.isOn(), "Light is on (dimmed)");
            assertEquals(30, light.getBrightness(), "Light is dimmed");
            assertTrue(ac.isEcoMode(), "AC is in Eco mode");

            macro.undo();
            assertFalse(light.isOn(), "Light is off after undo");
            assertFalse(ac.isOn(), "AC is off after undo");
        });
    }

    private static void testInvokerUndoStack() {
        runTest("Invoker Multi-Level Undo Stack", () -> {
            SmartHomeFacade facade = new SmartHomeFacade();
            SmartButtonPanel panel = new SmartButtonPanel();
            panel.setCommand(0, new TurnOnLightsCommand(facade));
            panel.setCommand(1, new EnergySavingScheduleCommand(facade));

            assertFalse(panel.canUndo(), "Stack initially empty");
            panel.pressButton(0);
            panel.pressButton(1);

            assertTrue(panel.canUndo(), "Can undo step 1");
            assertEquals("Energy-Saving Schedule", panel.getLastCommandName(), "Last command matches");

            panel.pressUndoButton();
            assertTrue(panel.canUndo(), "Can undo step 0");
            assertEquals("Turn On Lights", panel.getLastCommandName(), "Next command in stack matches");

            panel.pressUndoButton();
            assertFalse(panel.canUndo(), "Stack now empty");
        });
    }
}
