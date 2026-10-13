package lk.jiat.taxi;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Modern Swing GUI for Practical 08 - Taxi Service Mediator Pattern.
 * Demonstrates:
 * 1. Customer requests a taxi through the Taxi App Center.
 * 2. Taxi App Center finds an available taxi.
 * 3. Taxi App Center sends customer location to the available driver.
 */
public class TaxiAppUI extends JFrame implements TaxiAppListener {

    private final TaxiAppCenter taxiCenter;

    private DefaultTableModel driverTableModel;
    private JTable driverTable;
    private DefaultComboBoxModel<String> customerComboModel;
    private JComboBox<String> customerComboBox;
    private JTextField pickupLocationField;
    private JTextField newDriverField;
    private JTextField newCustomerField;
    private JTextArea dispatchLogArea;

    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");

    public TaxiAppUI() {
        this.taxiCenter = new TaxiAppCenter();
        this.taxiCenter.addListener(this);

        initSampleData();
        initComponents();
        refreshDriverTable();
    }

    private void initSampleData() {
        Driver d1 = new Driver(taxiCenter, "Kamal (Toyota Prius - CAB-1234)");
        Driver d2 = new Driver(taxiCenter, "Nimal (Suzuki WagonR - CAD-5678)");
        Driver d3 = new Driver(taxiCenter, "Sunil (Nissan Leaf - CAH-9012)");
        taxiCenter.registerDriver(d1);
        taxiCenter.registerDriver(d2);
        taxiCenter.registerDriver(d3);

        Customer c1 = new Customer(taxiCenter, "Kasun");
        Customer c2 = new Customer(taxiCenter, "Ruwan");
        Customer c3 = new Customer(taxiCenter, "Dilshan");
        taxiCenter.registerCustomer(c1);
        taxiCenter.registerCustomer(c2);
        taxiCenter.registerCustomer(c3);
    }

    private void initComponents() {
        setTitle("JIAT OODP2 - Practical 08: Taxi Service (Mediator Pattern)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(960, 680);
        setMinimumSize(new Dimension(860, 580));
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(14, 14));
        mainPanel.setBackground(new Color(245, 247, 250));
        mainPanel.setBorder(new EmptyBorder(16, 16, 16, 16));

        // ----------------- Header -----------------
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(24, 34, 54));
        headerPanel.setBorder(new EmptyBorder(16, 20, 16, 20));

        JLabel titleLabel = new JLabel("Taxi App Center - Mediator Design Pattern");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(new Color(255, 193, 7)); // Taxi amber yellow

        JLabel subtitleLabel = new JLabel("Practical 08 • Encapsulated Customer-Driver Communication via Centralized Mediator");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitleLabel.setForeground(new Color(200, 210, 230));

        headerPanel.add(titleLabel, BorderLayout.NORTH);
        headerPanel.add(subtitleLabel, BorderLayout.SOUTH);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // ----------------- Center Split -----------------
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setResizeWeight(0.42);
        splitPane.setDividerSize(6);
        splitPane.setOpaque(false);

        // --- Left Panel: Controls & Registration ---
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setOpaque(false);

        // Card 1: Request Taxi
        JPanel requestCard = createCardPanel("Customer Ride Request");
        requestCard.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        requestCard.add(new JLabel("Select Customer:"), gbc);

        gbc.gridx = 1; gbc.gridy = 0;
        customerComboModel = new DefaultComboBoxModel<>();
        for (Customer c : taxiCenter.getCustomers()) {
            customerComboModel.addElement(c.getName());
        }
        customerComboBox = new JComboBox<>(customerComboModel);
        customerComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        requestCard.add(customerComboBox, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        requestCard.add(new JLabel("Pickup Location:"), gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        pickupLocationField = new JTextField("Colombo Fort Railway Station", 15);
        pickupLocationField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        requestCard.add(pickupLocationField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        JButton requestBtn = new JButton("Request Taxi");
        requestBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        requestBtn.setBackground(new Color(255, 179, 0));
        requestBtn.setForeground(new Color(20, 20, 20));
        requestBtn.setFocusPainted(false);
        requestBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        requestBtn.addActionListener(e -> onRequestTaxiClicked());
        requestCard.add(requestBtn, gbc);

        leftPanel.add(requestCard);
        leftPanel.add(Box.createVerticalStrut(12));

        // Card 2: Driver Trip Management
        JPanel tripMgmtCard = createCardPanel("Driver Action");
        tripMgmtCard.setLayout(new BorderLayout(8, 8));
        JButton completeTripBtn = new JButton("Complete Selected Driver's Trip");
        completeTripBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        completeTripBtn.setBackground(new Color(40, 167, 69));
        completeTripBtn.setForeground(Color.WHITE);
        completeTripBtn.setFocusPainted(false);
        completeTripBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        completeTripBtn.addActionListener(e -> onCompleteTripClicked());
        tripMgmtCard.add(completeTripBtn, BorderLayout.CENTER);

        leftPanel.add(tripMgmtCard);
        leftPanel.add(Box.createVerticalStrut(12));

        // Card 3: Register Colleagues
        JPanel registerCard = createCardPanel("Register New Colleagues");
        registerCard.setLayout(new GridBagLayout());
        GridBagConstraints regGbc = new GridBagConstraints();
        regGbc.insets = new Insets(5, 5, 5, 5);
        regGbc.fill = GridBagConstraints.HORIZONTAL;

        regGbc.gridx = 0; regGbc.gridy = 0;
        registerCard.add(new JLabel("New Driver:"), regGbc);
        regGbc.gridx = 1; regGbc.gridy = 0;
        newDriverField = new JTextField("Saman (Prius)", 10);
        registerCard.add(newDriverField, regGbc);
        regGbc.gridx = 2; regGbc.gridy = 0;
        JButton addDriverBtn = new JButton("+ Add Driver");
        addDriverBtn.setBackground(new Color(52, 73, 94));
        addDriverBtn.setForeground(Color.WHITE);
        addDriverBtn.addActionListener(e -> onAddDriverClicked());
        registerCard.add(addDriverBtn, regGbc);

        regGbc.gridx = 0; regGbc.gridy = 1;
        registerCard.add(new JLabel("New Customer:"), regGbc);
        regGbc.gridx = 1; regGbc.gridy = 1;
        newCustomerField = new JTextField("Anura", 10);
        registerCard.add(newCustomerField, regGbc);
        regGbc.gridx = 2; regGbc.gridy = 1;
        JButton addCustBtn = new JButton("+ Add Customer");
        addCustBtn.setBackground(new Color(52, 73, 94));
        addCustBtn.setForeground(Color.WHITE);
        addCustBtn.addActionListener(e -> onAddCustomerClicked());
        registerCard.add(addCustBtn, regGbc);

        leftPanel.add(registerCard);
        splitPane.setLeftComponent(leftPanel);

        // --- Right Panel: Drivers Table & Live Log ---
        JPanel rightPanel = new JPanel(new BorderLayout(10, 10));
        rightPanel.setOpaque(false);

        // Table Panel (Drivers Status)
        JPanel tableCard = createCardPanel("Registered Drivers Status");
        tableCard.setLayout(new BorderLayout());

        driverTableModel = new DefaultTableModel(new String[]{"Driver Name", "Status", "Customer", "Pickup Location"}, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        driverTable = new JTable(driverTableModel);
        driverTable.setRowHeight(28);
        driverTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        driverTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        driverTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Status renderer with color pill
        driverTable.getColumnModel().getColumn(1).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
                label.setHorizontalAlignment(SwingConstants.CENTER);
                String status = String.valueOf(value);
                if ("AVAILABLE".equals(status)) {
                    label.setForeground(new Color(25, 135, 84));
                    label.setFont(new Font("Segoe UI", Font.BOLD, 12));
                } else if ("BUSY".equals(status)) {
                    label.setForeground(new Color(220, 53, 69));
                    label.setFont(new Font("Segoe UI", Font.BOLD, 12));
                }
                return label;
            }
        });

        JScrollPane tableScroll = new JScrollPane(driverTable);
        tableScroll.setPreferredSize(new Dimension(480, 180));
        tableCard.add(tableScroll, BorderLayout.CENTER);
        rightPanel.add(tableCard, BorderLayout.NORTH);

        // Live Mediator Dispatch Log
        JPanel logCard = createCardPanel("Taxi App Center Dispatch Log (Mediator Operations)");
        logCard.setLayout(new BorderLayout(6, 6));

        dispatchLogArea = new JTextArea();
        dispatchLogArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        dispatchLogArea.setEditable(false);
        dispatchLogArea.setBackground(new Color(20, 24, 33));
        dispatchLogArea.setForeground(new Color(220, 230, 242));
        dispatchLogArea.setMargin(new Insets(8, 8, 8, 8));

        JScrollPane logScroll = new JScrollPane(dispatchLogArea);
        logCard.add(logScroll, BorderLayout.CENTER);

        JButton clearLogBtn = new JButton("Clear Log");
        clearLogBtn.addActionListener(e -> dispatchLogArea.setText(""));
        logCard.add(clearLogBtn, BorderLayout.SOUTH);

        rightPanel.add(logCard, BorderLayout.CENTER);
        splitPane.setRightComponent(rightPanel);

        mainPanel.add(splitPane, BorderLayout.CENTER);
        setContentPane(mainPanel);
    }

    private JPanel createCardPanel(String title) {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(222, 226, 230), 1),
                BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder(
                                BorderFactory.createEmptyBorder(2, 6, 4, 6),
                                title,
                                0, 0,
                                new Font("Segoe UI", Font.BOLD, 13),
                                new Color(40, 50, 70)
                        ),
                        new EmptyBorder(8, 8, 8, 8)
                )
        ));
        return panel;
    }

    private void onRequestTaxiClicked() {
        String customerName = (String) customerComboBox.getSelectedItem();
        String location = pickupLocationField.getText().trim();

        if (customerName == null || customerName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a customer.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (location.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a pickup location.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Customer selectedCust = null;
        for (Customer c : taxiCenter.getCustomers()) {
            if (c.getName().equals(customerName)) {
                selectedCust = c;
                break;
            }
        }

        if (selectedCust != null) {
            selectedCust.requestTaxi(location);
        }
    }

    private void onCompleteTripClicked() {
        int selectedRow = driverTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a driver from the table first.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String driverName = (String) driverTableModel.getValueAt(selectedRow, 0);
        for (Driver d : taxiCenter.getDrivers()) {
            if (d.getName().equals(driverName)) {
                if (d.isAvailable()) {
                    JOptionPane.showMessageDialog(this, "Driver is already available.", "Info", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    d.completeTrip();
                }
                break;
            }
        }
    }

    private void onAddDriverClicked() {
        String name = newDriverField.getText().trim();
        if (!name.isEmpty()) {
            Driver d = new Driver(taxiCenter, name);
            taxiCenter.registerDriver(d);
            newDriverField.setText("");
        }
    }

    private void onAddCustomerClicked() {
        String name = newCustomerField.getText().trim();
        if (!name.isEmpty()) {
            Customer c = new Customer(taxiCenter, name);
            taxiCenter.registerCustomer(c);
            customerComboModel.addElement(c.getName());
            newCustomerField.setText("");
        }
    }

    private void refreshDriverTable() {
        driverTableModel.setRowCount(0);
        for (Driver d : taxiCenter.getDrivers()) {
            String status = d.isAvailable() ? "AVAILABLE" : "BUSY";
            String cust = d.getCurrentCustomer() != null ? d.getCurrentCustomer() : "-";
            String loc = d.getCurrentLocation() != null ? d.getCurrentLocation() : "-";
            driverTableModel.addRow(new Object[]{d.getName(), status, cust, loc});
        }
    }

    // --- TaxiAppListener Callbacks ---
    @Override
    public void onLog(String message) {
        SwingUtilities.invokeLater(() -> {
            String time = LocalTime.now().format(TIME_FORMAT);
            dispatchLogArea.append("[" + time + "] " + message + "\n");
            dispatchLogArea.setCaretPosition(dispatchLogArea.getDocument().getLength());
        });
    }

    @Override
    public void onDriverStatusChanged(Driver driver) {
        SwingUtilities.invokeLater(this::refreshDriverTable);
    }
}
