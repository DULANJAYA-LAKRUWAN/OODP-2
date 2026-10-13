package lk.jiat.jms;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Modern Swing UI for Practical OODP2-8 - Airport Traffic Control Mediator System.
 */
public class AirportUI extends JFrame {

    private final AirportControlTower controlTower;
    private DefaultTableModel flightTableModel;
    private JTextArea radioLogArea;
    private JComboBox<String> flightTypeCombo;
    private JTextField flightNumberField;

    public AirportUI() {
        this.controlTower = new AirportControlTower();
        initComponents();
    }

    private void initComponents() {
        setTitle("JIAT OODP2 - Practical 08: Air Traffic Control (Mediator Pattern)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(860, 580);
        setMinimumSize(new Dimension(780, 500));
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(12, 12));
        mainPanel.setBackground(new Color(243, 246, 250));
        mainPanel.setBorder(new EmptyBorder(16, 16, 16, 16));

        // ================= Header Panel =================
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(15, 32, 67));
        headerPanel.setBorder(new EmptyBorder(16, 20, 16, 20));

        JLabel titleLabel = new JLabel("Airport Control Tower - Mediator Pattern");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);

        JLabel subtitleLabel = new JLabel("Practical OODP2-8 (lk.jiat.jms) • Centralized Flight Coordination");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitleLabel.setForeground(new Color(180, 200, 230));

        headerPanel.add(titleLabel, BorderLayout.NORTH);
        headerPanel.add(subtitleLabel, BorderLayout.SOUTH);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // ================= Center Panel (Split: Controls/Table & Console Log) =================
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setResizeWeight(0.5);
        splitPane.setDividerSize(6);
        splitPane.setOpaque(false);

        // --- Left: Flight Management & Actions ---
        JPanel leftPanel = new JPanel(new BorderLayout(10, 10));
        leftPanel.setOpaque(false);

        // Add Flight Form
        JPanel formCard = new JPanel(new GridBagLayout());
        formCard.setBackground(Color.WHITE);
        formCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 225, 235), 1),
                new EmptyBorder(12, 12, 12, 12)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        formCard.add(new JLabel("Flight Type:"), gbc);

        gbc.gridx = 1; gbc.gridy = 0;
        flightTypeCombo = new JComboBox<>(new String[]{"FlightA", "FlightB"});
        formCard.add(flightTypeCombo, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formCard.add(new JLabel("Flight Number:"), gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        flightNumberField = new JTextField("999", 8);
        formCard.add(flightNumberField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        JButton addFlightBtn = new JButton("Register New Flight");
        addFlightBtn.setBackground(new Color(24, 119, 242));
        addFlightBtn.setForeground(Color.WHITE);
        addFlightBtn.setFocusPainted(false);
        formCard.add(addFlightBtn, gbc);

        leftPanel.add(formCard, BorderLayout.NORTH);

        // Flights Table
        flightTableModel = new DefaultTableModel(new String[]{"Flight Type", "Number", "Status"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable flightTable = new JTable(flightTableModel);
        flightTable.setRowHeight(26);
        flightTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        flightTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        JScrollPane tableScroll = new JScrollPane(flightTable);
        tableScroll.setBorder(BorderFactory.createLineBorder(new Color(220, 225, 235), 1));
        leftPanel.add(tableScroll, BorderLayout.CENTER);

        // Actions Toolbar
        JPanel actionPanel = new JPanel(new GridLayout(1, 2, 8, 0));
        actionPanel.setOpaque(false);

        JButton landingBtn = new JButton("Request Landing");
        landingBtn.setBackground(new Color(16, 149, 102));
        landingBtn.setForeground(Color.WHITE);
        landingBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        landingBtn.setFocusPainted(false);

        JButton takeoffBtn = new JButton("Request Takeoff");
        takeoffBtn.setBackground(new Color(13, 110, 253));
        takeoffBtn.setForeground(Color.WHITE);
        takeoffBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        takeoffBtn.setFocusPainted(false);

        actionPanel.add(landingBtn);
        actionPanel.add(takeoffBtn);
        leftPanel.add(actionPanel, BorderLayout.SOUTH);

        // --- Right: ATC Radio Communications & Transmission Log ---
        JPanel rightPanel = new JPanel(new BorderLayout(8, 8));
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 225, 235), 1),
                new EmptyBorder(12, 12, 12, 12)
        ));

        JLabel logHeader = new JLabel("Tower Radio Transmissions & Logs");
        logHeader.setFont(new Font("Segoe UI", Font.BOLD, 14));
        rightPanel.add(logHeader, BorderLayout.NORTH);

        radioLogArea = new JTextArea();
        radioLogArea.setEditable(false);
        radioLogArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        radioLogArea.setBackground(new Color(248, 250, 253));
        radioLogArea.setMargin(new Insets(8, 8, 8, 8));
        JScrollPane logScroll = new JScrollPane(radioLogArea);
        rightPanel.add(logScroll, BorderLayout.CENTER);

        splitPane.setLeftComponent(leftPanel);
        splitPane.setRightComponent(rightPanel);
        mainPanel.add(splitPane, BorderLayout.CENTER);

        // Seed with the default flights from Main.java
        seedDefaultFlights();

        // Button Listeners
        addFlightBtn.addActionListener(e -> {
            String num = flightNumberField.getText().trim();
            if (num.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a flight number.");
                return;
            }
            String type = (String) flightTypeCombo.getSelectedItem();
            flightTableModel.addRow(new Object[]{type, num, "Ready"});
            appendLog("[SYSTEM] Registered " + type + " #" + num + " into ATC airspace.");
            flightNumberField.setText(String.valueOf((int) (Math.random() * 899 + 100)));
        });

        landingBtn.addActionListener(e -> {
            int selectedRow = flightTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a flight from the table first!");
                return;
            }
            String type = (String) flightTableModel.getValueAt(selectedRow, 0);
            String number = (String) flightTableModel.getValueAt(selectedRow, 1);
            Flight flight = createFlight(type, number);

            appendLog(">> Flight: " + number + " request permission to landing");
            flight.requestLanding();
            appendLog("<< " + type + ":" + number + " New message: Landing approved\n");
            flightTableModel.setValueAt("Landed", selectedRow, 2);
        });

        takeoffBtn.addActionListener(e -> {
            int selectedRow = flightTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a flight from the table first!");
                return;
            }
            String type = (String) flightTableModel.getValueAt(selectedRow, 0);
            String number = (String) flightTableModel.getValueAt(selectedRow, 1);
            Flight flight = createFlight(type, number);

            appendLog(">> Flight: " + number + " request permission to take off");
            flight.requestTakeoff();
            appendLog("<< " + type + ":" + number + " New message: Take-off approved\n");
            flightTableModel.setValueAt("Airborne", selectedRow, 2);
        });

        setContentPane(mainPanel);
    }

    private void seedDefaultFlights() {
        flightTableModel.addRow(new Object[]{"FlightA", "123", "En Route"});
        flightTableModel.addRow(new Object[]{"FlightB", "456", "Inbound"});
        flightTableModel.addRow(new Object[]{"FlightB", "789", "Taxiing"});

        appendLog("=== Airport Control Tower Mediator Online ===");
        appendLog("AirportControlTower initialized as central Mediator.");
        appendLog("Flights 123, 456, and 789 connected via Mediator interface.\n");
    }

    private Flight createFlight(String type, String number) {
        if ("FlightA".equals(type)) {
            return new FlightA(controlTower, number);
        } else {
            return new FlightB(controlTower, number);
        }
    }

    private void appendLog(String text) {
        radioLogArea.append(text + "\n");
        radioLogArea.setCaretPosition(radioLogArea.getDocument().getLength());
    }
}
