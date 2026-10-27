package com.lakruwan.smarthome.ui;

import com.lakruwan.smarthome.command.Command;
import com.lakruwan.smarthome.facade.SmartHomeFacade;
import com.lakruwan.smarthome.facade.SmartHomeListener;
import com.lakruwan.smarthome.invoker.SmartButtonPanel;
import com.lakruwan.smarthome.subsystem.AirConditioning;
import com.lakruwan.smarthome.subsystem.Light;
import com.lakruwan.smarthome.subsystem.SecurityAlarm;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Modern Swing GUI for the Smart Home Automation System.
 * Visualizes the Subsystems (Light, AC, Alarm) and provides interactive Command Buttons.
 */
public class SmartHomeUI extends JFrame implements SmartHomeListener {

    private final SmartHomeFacade facade;
    private final SmartButtonPanel buttonPanel;

    // UI Components for Subsystem Cards
    private JLabel lightStatusLabel;
    private JLabel lightBrightnessLabel;
    private JProgressBar lightProgressBar;
    private JPanel lightIconIndicator;

    private JLabel acStatusLabel;
    private JLabel acTempLabel;
    private JLabel acModeLabel;
    private JPanel acIconIndicator;

    private JLabel alarmStatusLabel;
    private JLabel alarmModeLabel;
    private JPanel alarmIconIndicator;

    private JLabel globalStatusBadge;
    private JTextArea logTextArea;
    private JButton undoButton;

    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");

    // Color Palette
    private static final Color BG_DARK = new Color(24, 24, 37);
    private static final Color CARD_BG = new Color(33, 33, 50);
    private static final Color CARD_BORDER = new Color(55, 55, 80);
    private static final Color ACCENT_BLUE = new Color(59, 130, 246);
    private static final Color ACCENT_GREEN = new Color(16, 185, 129);
    private static final Color ACCENT_AMBER = new Color(245, 158, 11);
    private static final Color ACCENT_RED = new Color(239, 68, 68);
    private static final Color TEXT_PRIMARY = new Color(240, 243, 246);
    private static final Color TEXT_MUTED = new Color(156, 163, 175);

    public SmartHomeUI(SmartHomeFacade facade, SmartButtonPanel buttonPanel) {
        this.facade = facade != null ? facade : new SmartHomeFacade();
        this.buttonPanel = buttonPanel != null ? buttonPanel : new SmartButtonPanel();

        this.facade.addListener(this);

        initFrame();
        initComponents();
        updateSubsystemVisuals();

        appendLog("SYSTEM", "Smart Home Automation System initialized with Command & Facade patterns.");
    }

    private void initFrame() {
        setTitle("Smart Home System (Command & Facade Patterns)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(960, 720);
        setMinimumSize(new Dimension(850, 650));
        setLocationRelativeTo(null);
        getContentPane().setBackground(BG_DARK);
    }

    private void initComponents() {
        setLayout(new BorderLayout(15, 15));

        // 1. Header Panel
        add(createHeaderPanel(), BorderLayout.NORTH);

        // 2. Center Panel: Device Cards + Command Buttons
        JPanel centerPanel = new JPanel(new BorderLayout(15, 15));
        centerPanel.setOpaque(false);
        centerPanel.setBorder(new EmptyBorder(0, 20, 0, 20));

        centerPanel.add(createDeviceCardsPanel(), BorderLayout.NORTH);
        centerPanel.add(createCommandButtonsPanel(), BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        // 3. Bottom Panel: Activity & Pattern Log
        add(createLogPanel(), BorderLayout.SOUTH);
    }

    private JPanel createHeaderPanel() {
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(new EmptyBorder(20, 20, 10, 20));

        JPanel titleBlock = new JPanel(new GridLayout(2, 1, 2, 2));
        titleBlock.setOpaque(false);

        JLabel title = new JLabel("🏠 Smart Home Automation System");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(TEXT_PRIMARY);

        JLabel subtitle = new JLabel("Practical Assignment: Command & Facade Design Patterns");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitle.setForeground(TEXT_MUTED);

        titleBlock.add(title);
        titleBlock.add(subtitle);

        globalStatusBadge = new JLabel(" SYSTEM READY ", SwingConstants.CENTER);
        globalStatusBadge.setFont(new Font("Segoe UI", Font.BOLD, 12));
        globalStatusBadge.setForeground(Color.WHITE);
        globalStatusBadge.setOpaque(true);
        globalStatusBadge.setBackground(ACCENT_BLUE);
        globalStatusBadge.setBorder(new EmptyBorder(6, 14, 6, 14));

        header.add(titleBlock, BorderLayout.WEST);
        header.add(globalStatusBadge, BorderLayout.EAST);

        return header;
    }

    private JPanel createDeviceCardsPanel() {
        JPanel cardsContainer = new JPanel(new GridLayout(1, 3, 15, 15));
        cardsContainer.setOpaque(false);

        cardsContainer.add(createLightCard());
        cardsContainer.add(createAcCard());
        cardsContainer.add(createAlarmCard());

        return cardsContainer;
    }

    private JPanel createLightCard() {
        JPanel card = createBaseCard("💡 Lighting Subsystem", "Living Room & Hall");

        lightIconIndicator = new JPanel();
        lightIconIndicator.setPreferredSize(new Dimension(14, 14));
        lightIconIndicator.setBackground(TEXT_MUTED);

        lightStatusLabel = new JLabel("Status: OFF");
        lightStatusLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lightStatusLabel.setForeground(TEXT_PRIMARY);

        lightBrightnessLabel = new JLabel("Brightness: 0%");
        lightBrightnessLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lightBrightnessLabel.setForeground(TEXT_MUTED);

        lightProgressBar = new JProgressBar(0, 100);
        lightProgressBar.setValue(0);
        lightProgressBar.setPreferredSize(new Dimension(100, 10));
        lightProgressBar.setForeground(ACCENT_AMBER);
        lightProgressBar.setBackground(new Color(45, 45, 65));
        lightProgressBar.setBorder(null);

        JPanel content = new JPanel(new GridLayout(4, 1, 4, 4));
        content.setOpaque(false);

        JPanel statusRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        statusRow.setOpaque(false);
        statusRow.add(lightIconIndicator);
        statusRow.add(lightStatusLabel);

        content.add(statusRow);
        content.add(lightBrightnessLabel);
        content.add(lightProgressBar);

        card.add(content, BorderLayout.CENTER);
        return card;
    }

    private JPanel createAcCard() {
        JPanel card = createBaseCard("❄️ Climate Control (AC)", "Central HVAC Unit");

        acIconIndicator = new JPanel();
        acIconIndicator.setPreferredSize(new Dimension(14, 14));
        acIconIndicator.setBackground(TEXT_MUTED);

        acStatusLabel = new JLabel("Status: OFF (Standby)");
        acStatusLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        acStatusLabel.setForeground(TEXT_PRIMARY);

        acTempLabel = new JLabel("Temperature: 24°C");
        acTempLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        acTempLabel.setForeground(TEXT_MUTED);

        acModeLabel = new JLabel("Mode: Standard");
        acModeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        acModeLabel.setForeground(ACCENT_GREEN);

        JPanel content = new JPanel(new GridLayout(3, 1, 4, 4));
        content.setOpaque(false);

        JPanel statusRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        statusRow.setOpaque(false);
        statusRow.add(acIconIndicator);
        statusRow.add(acStatusLabel);

        content.add(statusRow);
        content.add(acTempLabel);
        content.add(acModeLabel);

        card.add(content, BorderLayout.CENTER);
        return card;
    }

    private JPanel createAlarmCard() {
        JPanel card = createBaseCard("🛡️ Security Alarm", "Zone 1 - Perimeter & Motion");

        alarmIconIndicator = new JPanel();
        alarmIconIndicator.setPreferredSize(new Dimension(14, 14));
        alarmIconIndicator.setBackground(ACCENT_GREEN);

        alarmStatusLabel = new JLabel("Status: DISARMED");
        alarmStatusLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        alarmStatusLabel.setForeground(TEXT_PRIMARY);

        alarmModeLabel = new JLabel("Siren: Inactive | System Safe");
        alarmModeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        alarmModeLabel.setForeground(TEXT_MUTED);

        JPanel content = new JPanel(new GridLayout(3, 1, 4, 4));
        content.setOpaque(false);

        JPanel statusRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        statusRow.setOpaque(false);
        statusRow.add(alarmIconIndicator);
        statusRow.add(alarmStatusLabel);

        content.add(statusRow);
        content.add(alarmModeLabel);

        card.add(content, BorderLayout.CENTER);
        return card;
    }

    private JPanel createBaseCard(String title, String subtitle) {
        JPanel card = new JPanel(new BorderLayout(8, 8));
        card.setBackground(CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(CARD_BORDER, 1, true),
                new EmptyBorder(14, 14, 14, 14)
        ));

        JPanel header = new JPanel(new GridLayout(2, 1, 1, 1));
        header.setOpaque(false);

        JLabel titleLbl = new JLabel(title);
        titleLbl.setFont(new Font("Segoe UI", Font.BOLD, 15));
        titleLbl.setForeground(TEXT_PRIMARY);

        JLabel subLbl = new JLabel(subtitle);
        subLbl.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        subLbl.setForeground(TEXT_MUTED);

        header.add(titleLbl);
        header.add(subLbl);

        card.add(header, BorderLayout.NORTH);
        return card;
    }

    private JPanel createCommandButtonsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(CARD_BORDER, 1, true),
                new EmptyBorder(14, 16, 14, 16)
        ));

        JLabel sectionTitle = new JLabel("🎮 Smart Control Buttons (Command Invoker)");
        sectionTitle.setFont(new Font("Segoe UI", Font.BOLD, 15));
        sectionTitle.setForeground(TEXT_PRIMARY);
        panel.add(sectionTitle, BorderLayout.NORTH);

        JPanel buttonsGrid = new JPanel(new GridLayout(2, 3, 12, 12));
        buttonsGrid.setOpaque(false);

        // Required Button 1: Turn On Lights
        JButton btnTurnOnLights = createStyledButton("💡 Turn On Lights", ACCENT_AMBER);
        btnTurnOnLights.addActionListener(e -> {
            buttonPanel.pressTurnOnLights();
            globalStatusBadge.setText(" LIGHTS ACTIVE ");
            globalStatusBadge.setBackground(ACCENT_AMBER);
        });

        // Required Button 2: Energy-Saving Schedule
        JButton btnEnergySaving = createStyledButton("🌿 Energy-Saving Schedule", ACCENT_GREEN);
        btnEnergySaving.addActionListener(e -> {
            buttonPanel.pressEnergySavingSchedule();
            globalStatusBadge.setText(" ENERGY SAVING MODE ");
            globalStatusBadge.setBackground(ACCENT_GREEN);
        });

        // Required Button 3: Activate Away Mode
        JButton btnAwayMode = createStyledButton("🛡️ Activate Away Mode", ACCENT_RED);
        btnAwayMode.addActionListener(e -> {
            buttonPanel.pressAwayMode();
            globalStatusBadge.setText(" AWAY MODE ACTIVE ");
            globalStatusBadge.setBackground(ACCENT_RED);
        });

        // Bonus Button 4: Activate Home Mode
        JButton btnHomeMode = createStyledButton("🏠 Activate Home Mode", ACCENT_BLUE);
        btnHomeMode.addActionListener(e -> {
            buttonPanel.pressHomeMode();
            globalStatusBadge.setText(" HOME MODE ACTIVE ");
            globalStatusBadge.setBackground(ACCENT_BLUE);
        });

        // Bonus Button 5: Turn Off Lights
        JButton btnTurnOffLights = createStyledButton("🌑 Turn Off Lights", new Color(100, 116, 139));
        btnTurnOffLights.addActionListener(e -> {
            buttonPanel.pressTurnOffLights();
            globalStatusBadge.setText(" LIGHTS OFF ");
            globalStatusBadge.setBackground(new Color(100, 116, 139));
        });

        // Required Command Feature: Undo Button
        undoButton = createStyledButton("↩️ Undo Last Action", new Color(139, 92, 246));
        undoButton.addActionListener(e -> {
            buttonPanel.pressUndoButton();
            globalStatusBadge.setText(" ACTION UNDONE ");
            globalStatusBadge.setBackground(new Color(139, 92, 246));
        });

        buttonsGrid.add(btnTurnOnLights);
        buttonsGrid.add(btnEnergySaving);
        buttonsGrid.add(btnAwayMode);
        buttonsGrid.add(btnHomeMode);
        buttonsGrid.add(btnTurnOffLights);
        buttonsGrid.add(undoButton);

        panel.add(buttonsGrid, BorderLayout.CENTER);
        return panel;
    }

    private JButton createStyledButton(String text, Color accentColor) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setForeground(Color.WHITE);
        btn.setBackground(accentColor);
        btn.setFocusPainted(false);
        btn.setBorder(new EmptyBorder(12, 14, 12, 14));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(accentColor.brighter());
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(accentColor);
            }
        });

        return btn;
    }

    private JPanel createLogPanel() {
        JPanel logPanel = new JPanel(new BorderLayout(8, 8));
        logPanel.setOpaque(false);
        logPanel.setBorder(new EmptyBorder(0, 20, 15, 20));

        JPanel logHeader = new JPanel(new BorderLayout());
        logHeader.setOpaque(false);

        JLabel logTitle = new JLabel("📋 Pattern Execution & Activity Log (Command + Facade Interactions)");
        logTitle.setFont(new Font("Segoe UI", Font.BOLD, 13));
        logTitle.setForeground(TEXT_PRIMARY);

        JButton clearBtn = new JButton("Clear Log");
        clearBtn.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        clearBtn.setForeground(TEXT_MUTED);
        clearBtn.setBackground(CARD_BG);
        clearBtn.setBorder(new EmptyBorder(4, 8, 4, 8));
        clearBtn.addActionListener(e -> logTextArea.setText(""));

        logHeader.add(logTitle, BorderLayout.WEST);
        logHeader.add(clearBtn, BorderLayout.EAST);

        logTextArea = new JTextArea(7, 50);
        logTextArea.setEditable(false);
        logTextArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        logTextArea.setBackground(new Color(17, 17, 27));
        logTextArea.setForeground(new Color(220, 225, 235));
        logTextArea.setCaretColor(Color.WHITE);
        logTextArea.setBorder(new EmptyBorder(8, 8, 8, 8));

        JScrollPane scrollPane = new JScrollPane(logTextArea);
        scrollPane.setBorder(new LineBorder(CARD_BORDER, 1, true));

        logPanel.add(logHeader, BorderLayout.NORTH);
        logPanel.add(scrollPane, BorderLayout.CENTER);

        return logPanel;
    }

    public void updateSubsystemVisuals() {
        Light light = facade.getLight();
        AirConditioning ac = facade.getAc();
        SecurityAlarm alarm = facade.getSecurityAlarm();

        // 1. Update Lighting Subsystem Card
        if (light.isOn()) {
            lightIconIndicator.setBackground(ACCENT_AMBER);
            lightStatusLabel.setText("Status: ON");
            lightBrightnessLabel.setText(String.format("Brightness: %d%%", light.getBrightness()));
            lightProgressBar.setValue(light.getBrightness());
        } else {
            lightIconIndicator.setBackground(TEXT_MUTED);
            lightStatusLabel.setText("Status: OFF");
            lightBrightnessLabel.setText("Brightness: 0%");
            lightProgressBar.setValue(0);
        }

        // 2. Update AC Subsystem Card
        if (ac.isOn()) {
            acIconIndicator.setBackground(ac.isEcoMode() ? ACCENT_GREEN : ACCENT_BLUE);
            acStatusLabel.setText("Status: ON (" + (ac.isEcoMode() ? "ECO" : "Cooling") + ")");
            acTempLabel.setText(String.format("Temperature: %d°C", ac.getTemperature()));
            acModeLabel.setText("Mode: " + (ac.isEcoMode() ? "ECO Mode (Energy-Saving Active)" : "Comfort"));
        } else {
            acIconIndicator.setBackground(TEXT_MUTED);
            acStatusLabel.setText("Status: OFF (Standby)");
            acTempLabel.setText(String.format("Temperature: %d°C (Standby)", ac.getTemperature()));
            acModeLabel.setText("Mode: Standby");
        }

        // 3. Update Security Alarm Subsystem Card
        switch (alarm.getMode()) {
            case DISARMED -> {
                alarmIconIndicator.setBackground(ACCENT_GREEN);
                alarmStatusLabel.setText("Status: DISARMED");
                alarmModeLabel.setText("System: Safe & Unlocked");
            }
            case ARMED_STAY -> {
                alarmIconIndicator.setBackground(ACCENT_AMBER);
                alarmStatusLabel.setText("Status: ARMED (Stay)");
                alarmModeLabel.setText("Perimeter: Active | Motion: Bypassed");
            }
            case ARMED_AWAY -> {
                alarmIconIndicator.setBackground(ACCENT_RED);
                alarmStatusLabel.setText("Status: ARMED (Away)");
                alarmModeLabel.setText("Full Security: Perimeter + Motion Active");
            }
        }

        // Update undo button state
        undoButton.setEnabled(buttonPanel.canUndo());
        undoButton.setText(buttonPanel.canUndo() ?
                String.format("↩️ Undo (%s)", buttonPanel.getLastCommandName()) :
                "↩️ Undo Last Action");
    }

    private void appendLog(String category, String message) {
        String time = LocalTime.now().format(TIME_FORMAT);
        logTextArea.append(String.format("[%s] [%-7s] %s%n", time, category, message));
        logTextArea.setCaretPosition(logTextArea.getDocument().getLength());
    }

    // SmartHomeListener Implementation
    @Override
    public void onDeviceStateChanged() {
        SwingUtilities.invokeLater(this::updateSubsystemVisuals);
    }

    @Override
    public void onLogMessage(String category, String message) {
        SwingUtilities.invokeLater(() -> appendLog(category, message));
    }
}
