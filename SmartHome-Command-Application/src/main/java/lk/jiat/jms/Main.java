package lk.jiat.jms;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

// =========================================================================
// 1. SUBSYSTEM INTERFACES & CLASSES (The Receivers)
// =========================================================================

class Light {
    private boolean on;
    private int brightness;

    public void turnOn() {
        this.on = true;
        this.brightness = 100;
        System.out.println("  [Light Subsystem] Lights turned ON at 100% brightness.");
    }

    public void turnOff() {
        this.on = false;
        this.brightness = 0;
        System.out.println("  [Light Subsystem] Lights turned OFF.");
    }

    public void dim(int level) {
        this.on = true;
        this.brightness = level;
        System.out.println("  [Light Subsystem] Lights dimmed to " + level + "% for energy conservation.");
    }

    public boolean isOn() {
        return on;
    }

    public int getBrightness() {
        return brightness;
    }
}

class AirConditioning {
    private boolean on;
    private int temperature;
    private boolean ecoMode;

    public AirConditioning() {
        this.temperature = 24;
    }

    public void turnOn() {
        this.on = true;
        System.out.println("  [AC Subsystem] Air Conditioning turned ON.");
    }

    public void turnOff() {
        this.on = false;
        System.out.println("  [AC Subsystem] Air Conditioning turned OFF (Standby).");
    }

    public void setTemperature(int temp) {
        this.temperature = temp;
        System.out.println("  [AC Subsystem] Target temperature set to " + temp + "C.");
    }

    public void setEcoMode(boolean enabled) {
        this.ecoMode = enabled;
        if (enabled) {
            this.temperature = Math.max(this.temperature, 26);
            System.out.println("  [AC Subsystem] Eco Mode ENABLED (Auto-optimized to " + temperature + "C).");
        } else {
            System.out.println("  [AC Subsystem] Eco Mode DISABLED.");
        }
    }

    public boolean isOn() {
        return on;
    }

    public int getTemperature() {
        return temperature;
    }

    public boolean isEcoMode() {
        return ecoMode;
    }
}

class SecurityAlarm {
    public enum AlarmMode { DISARMED, ARMED_STAY, ARMED_AWAY }

    private AlarmMode mode = AlarmMode.DISARMED;

    public void armAway() {
        this.mode = AlarmMode.ARMED_AWAY;
        System.out.println("  [Security Subsystem] Security Alarm ARMED in AWAY MODE (Full perimeter + motion sensors active).");
    }

    public void armStay() {
        this.mode = AlarmMode.ARMED_STAY;
        System.out.println("  [Security Subsystem] Security Alarm ARMED in STAY MODE (Perimeter armed, motion bypass).");
    }

    public void disarm() {
        this.mode = AlarmMode.DISARMED;
        System.out.println("  [Security Subsystem] Security Alarm DISARMED (System safe).");
    }

    public AlarmMode getMode() {
        return mode;
    }
}

// =========================================================================
// 2. FACADE PATTERN: SmartHomeFacade
// =========================================================================

class SmartHomeFacade {
    private final Light light;
    private final AirConditioning ac;
    private final SecurityAlarm alarm;

    // Snapshot record for undo operations
    static class Snapshot {
        final boolean lightOn;
        final int lightBrightness;
        final boolean acOn;
        final int acTemp;
        final boolean acEco;
        final SecurityAlarm.AlarmMode alarmMode;

        Snapshot(Light l, AirConditioning a, SecurityAlarm s) {
            this.lightOn = l.isOn();
            this.lightBrightness = l.getBrightness();
            this.acOn = a.isOn();
            this.acTemp = a.getTemperature();
            this.acEco = a.isEcoMode();
            this.alarmMode = s.getMode();
        }
    }

    public SmartHomeFacade(Light light, AirConditioning ac, SecurityAlarm alarm) {
        this.light = light;
        this.ac = ac;
        this.alarm = alarm;
    }

    public Snapshot createSnapshot() {
        return new Snapshot(light, ac, alarm);
    }

    public void restoreSnapshot(Snapshot s) {
        if (s == null) return;
        System.out.println("  [Facade] Restoring subsystem state to previous snapshot...");
        if (s.lightOn) {
            light.dim(s.lightBrightness);
        } else {
            light.turnOff();
        }

        if (s.acOn) {
            ac.turnOn();
            ac.setTemperature(s.acTemp);
            ac.setEcoMode(s.acEco);
        } else {
            ac.turnOff();
        }

        switch (s.alarmMode) {
            case DISARMED -> alarm.disarm();
            case ARMED_STAY -> alarm.armStay();
            case ARMED_AWAY -> alarm.armAway();
        }
    }

    // Unified operations
    public void turnOnLights() {
        System.out.println("  [Facade] Handling turnOnLights() request:");
        light.turnOn();
    }

    public void turnOffLights() {
        System.out.println("  [Facade] Handling turnOffLights() request:");
        light.turnOff();
    }

    public void setEnergySavingSchedule() {
        System.out.println("  [Facade] Coordinating subsystems for ENERGY-SAVING SCHEDULE:");
        light.dim(30);
        if (!ac.isOn()) ac.turnOn();
        ac.setEcoMode(true);
        ac.setTemperature(26);
        if (alarm.getMode() == SecurityAlarm.AlarmMode.DISARMED) {
            alarm.armStay();
        }
    }

    public void activateAwayMode() {
        System.out.println("  [Facade] Coordinating subsystems for AWAY MODE:");
        light.turnOff();
        ac.turnOff();
        alarm.armAway();
    }

    public void printCurrentStatus() {
        System.out.println("  +----------------- SYSTEM STATUS -----------------+");
        System.out.printf("  | Light    : %-36s |%n", light.isOn() ? "ON (" + light.getBrightness() + "%)" : "OFF");
        System.out.printf("  | AC       : %-36s |%n", ac.isOn() ? "ON (" + ac.getTemperature() + "C, Eco: " + ac.isEcoMode() + ")" : "OFF (Standby)");
        System.out.printf("  | Security : %-36s |%n", alarm.getMode().name());
        System.out.println("  +-------------------------------------------------+");
    }
}

// =========================================================================
// 3. COMMAND PATTERN: Command Interface & Concrete Commands
// =========================================================================

interface Command {
    void execute();
    void undo();
    String getName();
}

class TurnOnLightsCommand implements Command {
    private final SmartHomeFacade facade;
    private SmartHomeFacade.Snapshot prev;

    public TurnOnLightsCommand(SmartHomeFacade facade) {
        this.facade = facade;
    }

    @Override
    public void execute() {
        prev = facade.createSnapshot();
        facade.turnOnLights();
    }

    @Override
    public void undo() {
        facade.restoreSnapshot(prev);
    }

    @Override
    public String getName() {
        return "Turn On Lights";
    }
}

class EnergySavingScheduleCommand implements Command {
    private final SmartHomeFacade facade;
    private SmartHomeFacade.Snapshot prev;

    public EnergySavingScheduleCommand(SmartHomeFacade facade) {
        this.facade = facade;
    }

    @Override
    public void execute() {
        prev = facade.createSnapshot();
        facade.setEnergySavingSchedule();
    }

    @Override
    public void undo() {
        facade.restoreSnapshot(prev);
    }

    @Override
    public String getName() {
        return "Energy-Saving Schedule";
    }
}

class ActivateAwayModeCommand implements Command {
    private final SmartHomeFacade facade;
    private SmartHomeFacade.Snapshot prev;

    public ActivateAwayModeCommand(SmartHomeFacade facade) {
        this.facade = facade;
    }

    @Override
    public void execute() {
        prev = facade.createSnapshot();
        facade.activateAwayMode();
    }

    @Override
    public void undo() {
        facade.restoreSnapshot(prev);
    }

    @Override
    public String getName() {
        return "Activate Away Mode";
    }
}

// Macro Command (Composite Command)
class MacroCommand implements Command {
    private final String name;
    private final List<Command> commands = new ArrayList<>();

    public MacroCommand(String name, List<Command> list) {
        this.name = name;
        if (list != null) commands.addAll(list);
    }

    @Override
    public void execute() {
        System.out.println("  [Macro Command: " + name + "] Executing batch sequence:");
        for (Command c : commands) {
            c.execute();
        }
    }

    @Override
    public void undo() {
        System.out.println("  [Macro Command: " + name + "] Undoing batch sequence in reverse:");
        for (int i = commands.size() - 1; i >= 0; i--) {
            commands.get(i).undo();
        }
    }

    @Override
    public String getName() {
        return name;
    }
}

// =========================================================================
// 4. INVOKER: Smart Button Remote Control
// =========================================================================

class RemoteControl {
    private final Command[] buttons = new Command[5];
    private final Deque<Command> history = new ArrayDeque<>();

    public void setButton(int slot, Command command) {
        if (slot >= 0 && slot < buttons.length) {
            buttons[slot] = command;
        }
    }

    public void pressButton(int slot) {
        if (slot >= 0 && slot < buttons.length && buttons[slot] != null) {
            Command cmd = buttons[slot];
            System.out.println("\n[Invoker] Button " + slot + " pressed -> Executing: " + cmd.getName());
            cmd.execute();
            history.push(cmd);
        } else {
            System.out.println("\n[Invoker] Button " + slot + " has no assigned command.");
        }
    }

    public void pressUndoButton() {
        if (!history.isEmpty()) {
            Command cmd = history.pop();
            System.out.println("\n[Invoker] Undo button pressed -> Reversing: " + cmd.getName());
            cmd.undo();
        } else {
            System.out.println("\n[Invoker] Undo history is empty.");
        }
    }
}

// =========================================================================
// 5. DEMONSTRATION MAIN CLASS
// =========================================================================

public class Main {
    public static void main(String[] args) {
        System.out.println("======================================================================");
        System.out.println("   SMART HOME AUTOMATION SYSTEM: COMMAND & FACADE PATTERNS DEMO       ");
        System.out.println("======================================================================");

        // Step 1: Create Subsystem Instances (Receivers)
        Light livingRoomLight = new Light();
        AirConditioning centralAc = new AirConditioning();
        SecurityAlarm securityAlarm = new SecurityAlarm();

        // Step 2: Create Facade to simplify subsystem interactions
        SmartHomeFacade facade = new SmartHomeFacade(livingRoomLight, centralAc, securityAlarm);

        // Step 3: Create Commands parameterizing operations
        Command turnOnLights = new TurnOnLightsCommand(facade);
        Command energySaving = new EnergySavingScheduleCommand(facade);
        Command awayMode = new ActivateAwayModeCommand(facade);

        // Step 4: Create Invoker (RemoteControl) and map buttons to commands
        RemoteControl remote = new RemoteControl();
        remote.setButton(0, turnOnLights);  // Button 0: Turn on lights
        remote.setButton(1, energySaving);  // Button 1: Energy-saving schedule
        remote.setButton(2, awayMode);      // Button 2: Activate away mode

        System.out.println("\nInitial State:");
        facade.printCurrentStatus();

        // Scenario 1: User presses button to turn on lights
        System.out.println("\n--- SCENARIO 1: User presses Button 0 to Turn On the Lights ---");
        remote.pressButton(0);
        facade.printCurrentStatus();

        // Scenario 2: User presses button to create an energy-saving schedule
        System.out.println("\n--- SCENARIO 2: User presses Button 1 for Energy-Saving Schedule ---");
        remote.pressButton(1);
        facade.printCurrentStatus();

        // Scenario 3: User presses button to activate away mode
        System.out.println("\n--- SCENARIO 3: User presses Button 2 to Activate Away Mode ---");
        remote.pressButton(2);
        facade.printCurrentStatus();

        // Scenario 4: User presses Undo to revert Away Mode
        System.out.println("\n--- SCENARIO 4: User presses UNDO Button (Reverting Away Mode) ---");
        remote.pressUndoButton();
        facade.printCurrentStatus();

        // Scenario 5: User presses Undo again to revert Energy-Saving Schedule
        System.out.println("\n--- SCENARIO 5: User presses UNDO Button (Reverting Energy Schedule) ---");
        remote.pressUndoButton();
        facade.printCurrentStatus();

        System.out.println("\n======================================================================");
        System.out.println("   ALL SCENARIOS COMPLETED SUCCESSFULLY!                              ");
        System.out.println("======================================================================");
    }
}
