package com.lakruwan.smarthome;

import com.lakruwan.smarthome.command.ActivateAwayModeCommand;
import com.lakruwan.smarthome.command.ActivateHomeModeCommand;
import com.lakruwan.smarthome.command.EnergySavingScheduleCommand;
import com.lakruwan.smarthome.command.TurnOffLightsCommand;
import com.lakruwan.smarthome.command.TurnOnLightsCommand;
import com.lakruwan.smarthome.facade.SmartHomeFacade;
import com.lakruwan.smarthome.invoker.SmartButtonPanel;
import com.lakruwan.smarthome.subsystem.AirConditioning;
import com.lakruwan.smarthome.subsystem.Light;
import com.lakruwan.smarthome.subsystem.SecurityAlarm;
import com.lakruwan.smarthome.ui.SmartHomeUI;

import javax.swing.*;

/**
 * Main application launcher for the Smart Home Automation System.
 * Configures the Subsystems, Facade, Commands, and Invoker, then launches the GUI.
 */
public class Launcher {

    public static void main(String[] args) {
        System.out.println("===============================================================");
        System.out.println("  Smart Home Automation System (Command & Facade Patterns)");
        System.out.println("===============================================================");

        // 1. Initialize Subsystem Devices (Receivers)
        Light livingRoomLight = new Light("Living Room & Hall");
        AirConditioning centralAc = new AirConditioning("Central HVAC");
        SecurityAlarm securityAlarm = new SecurityAlarm("Main Zone");

        // 2. Initialize Facade
        SmartHomeFacade facade = new SmartHomeFacade(livingRoomLight, centralAc, securityAlarm);

        // 3. Initialize Invoker (Smart Button Panel)
        SmartButtonPanel buttonPanel = new SmartButtonPanel();

        // 4. Bind Commands to Invoker Button Slots
        buttonPanel.setCommand(SmartButtonPanel.SLOT_LIGHTS_ON, new TurnOnLightsCommand(facade));
        buttonPanel.setCommand(SmartButtonPanel.SLOT_ENERGY_SAVING, new EnergySavingScheduleCommand(facade));
        buttonPanel.setCommand(SmartButtonPanel.SLOT_AWAY_MODE, new ActivateAwayModeCommand(facade));
        buttonPanel.setCommand(SmartButtonPanel.SLOT_HOME_MODE, new ActivateHomeModeCommand(facade));
        buttonPanel.setCommand(SmartButtonPanel.SLOT_LIGHTS_OFF, new TurnOffLightsCommand(facade));

        // 5. Launch Modern Swing UI
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
            }

            SmartHomeUI ui = new SmartHomeUI(facade, buttonPanel);
            ui.setVisible(true);
        });
    }
}
