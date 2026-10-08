package com.nanopaint;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Visual Interface for "NanoPaint" Car Painting Shop
 * Interactive demonstration of Flyweight Pattern
 */
public class NanoPaintUI extends JFrame {
    private final JTextField modelField;
    private final JTextField regNoField;
    private final JComboBox<String> colorCombo;
    private final JTable carTable;
    private final DefaultTableModel tableModel;
    private final JLabel poolStatsLabel;
    private final CarCanvas carCanvas;

    private final List<Context> paintedCars = new ArrayList<>();

    public NanoPaintUI() {
        super("NanoPaint Shop - Flyweight Pattern Demonstration");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 650);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Top Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(41, 128, 185));
        headerPanel.setBorder(new EmptyBorder(15, 20, 15, 20));

        JLabel titleLabel = new JLabel("NanoPaint Car Painting Shop (Flyweight Pattern)");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);

        JLabel subLabel = new JLabel("Cars share intrinsic Color Flyweight objects from SharedContextPool");
        subLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subLabel.setForeground(new Color(236, 240, 241));

        headerPanel.add(titleLabel, BorderLayout.NORTH);
        headerPanel.add(subLabel, BorderLayout.SOUTH);
        add(headerPanel, BorderLayout.NORTH);

        // Center Panel: Left Controls & Car Preview, Right Table
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        centerPanel.setBorder(new EmptyBorder(10, 15, 10, 15));

        // Left Panel (Form + Live Car Render)
        JPanel leftPanel = new JPanel(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Car Painting Station"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Car Model:"), gbc);
        gbc.gridx = 1;
        modelField = new JTextField("Toyota Corolla", 12);
        formPanel.add(modelField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Plate / Reg No:"), gbc);
        gbc.gridx = 1;
        regNoField = new JTextField("WP-CA-1024", 12);
        formPanel.add(regNoField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Paint Color:"), gbc);
        gbc.gridx = 1;
        colorCombo = new JComboBox<>(new String[]{"Blue", "Black", "Red", "Silver"});
        formPanel.add(colorCombo, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        JButton paintButton = new JButton("Paint & Register Car");
        paintButton.setBackground(new Color(46, 204, 113));
        paintButton.setForeground(Color.WHITE);
        paintButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        paintButton.setFocusPainted(false);
        paintButton.addActionListener(this::handlePaintCar);
        formPanel.add(paintButton, gbc);

        leftPanel.add(formPanel, BorderLayout.NORTH);

        // Visual Car Canvas
        carCanvas = new CarCanvas();
        carCanvas.setBorder(BorderFactory.createTitledBorder("Painted Car Preview"));
        leftPanel.add(carCanvas, BorderLayout.CENTER);

        // Right Panel (Table of cars & Flyweight pool details)
        JPanel rightPanel = new JPanel(new BorderLayout(5, 5));
        rightPanel.setBorder(BorderFactory.createTitledBorder("Painted Cars & Flyweight Pool Status"));

        String[] columns = {"Car Info (Extrinsic)", "Color (Intrinsic)", "Color HashCode", "Memory Status"};
        tableModel = new DefaultTableModel(columns, 0);
        carTable = new JTable(tableModel);
        carTable.setRowHeight(24);
        rightPanel.add(new JScrollPane(carTable), BorderLayout.CENTER);

        poolStatsLabel = new JLabel("Shared Colors in Pool: 0 | Total Painted Cars: 0");
        poolStatsLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        poolStatsLabel.setBorder(new EmptyBorder(8, 5, 8, 5));
        rightPanel.add(poolStatsLabel, BorderLayout.SOUTH);

        centerPanel.add(leftPanel);
        centerPanel.add(rightPanel);
        add(centerPanel, BorderLayout.CENTER);

        // Preload sample cars matching practical diagram
        preloadSampleData();
    }

    private void preloadSampleData() {
        paintCar("Toyota Corolla [WP-CA-1024]", "Blue");
        paintCar("BMW 520d [WP-KM-9988]", "Black");
        paintCar("Mazda 3 [WP-PJ-3321]", "Red");
        paintCar("Mercedes C200 [WP-KO-2255]", "Silver");
        paintCar("Nissan Leaf [WP-CB-5542]", "Blue");
        paintCar("Audi A4 [WP-KH-7711]", "Black");
    }

    private void handlePaintCar(ActionEvent e) {
        String model = modelField.getText().trim();
        String reg = regNoField.getText().trim();
        String color = (String) colorCombo.getSelectedItem();

        if (model.isEmpty() || reg.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both model and plate number!");
            return;
        }

        String carDetails = model + " [" + reg + "]";
        paintCar(carDetails, color);
    }

    private void paintCar(String carDetails, String colorName) {
        int poolBefore = SharedContextPool.getTotalSharedContexts();

        // 1. Fetch Flyweight from Pool
        SharedContext colorFlyweight = SharedContextPool.getSharedContext(colorName);

        // 2. Create ConcreteContext with extrinsic state and shared flyweight
        ConcreteContext car = new ConcreteContext(carDetails, colorFlyweight);
        paintedCars.add(car);

        int poolAfter = SharedContextPool.getTotalSharedContexts();
        String status = (poolAfter > poolBefore) ? "NEW COLOR CREATED" : "REUSED FROM POOL";

        // Add to table
        tableModel.addRow(new Object[]{
                car.getExtrinsicState(),
                colorFlyweight.getIntrinsicState(),
                System.identityHashCode(colorFlyweight),
                status
        });

        // Update Stats
        poolStatsLabel.setText(String.format(
                "Shared Colors in Pool: %d | Total Painted Cars: %d (Memory Saved!)",
                SharedContextPool.getTotalSharedContexts(),
                paintedCars.size()
        ));

        // Update Car Canvas
        carCanvas.setCurrentColor(colorName);
    }

    // Custom Canvas to draw car with selected color
    static class CarCanvas extends JPanel {
        private Color carColor = new Color(52, 152, 219);

        public void setCurrentColor(String colorName) {
            switch (colorName.toUpperCase()) {
                case "BLUE":
                    carColor = new Color(41, 128, 185);
                    break;
                case "BLACK":
                    carColor = new Color(44, 62, 80);
                    break;
                case "RED":
                    carColor = new Color(231, 76, 60);
                    break;
                case "SILVER":
                    carColor = new Color(189, 195, 199);
                    break;
                default:
                    carColor = Color.GRAY;
            }
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth();
            int h = getHeight();

            // Background
            g2.setColor(new Color(245, 247, 250));
            g2.fillRect(0, 0, w, h);

            int cx = w / 2 - 130;
            int cy = h / 2 - 30;

            // Car Roof / Cabin
            g2.setColor(carColor);
            g2.fillRoundRect(cx + 40, cy - 40, 160, 50, 30, 30);

            // Car Windows
            g2.setColor(new Color(174, 214, 241));
            g2.fillRoundRect(cx + 55, cy - 35, 60, 35, 10, 10);
            g2.fillRoundRect(cx + 125, cy - 35, 65, 35, 10, 10);

            // Car Body
            g2.setColor(carColor);
            g2.fillRoundRect(cx, cy, 260, 60, 20, 20);

            // Wheels
            g2.setColor(Color.DARK_GRAY);
            g2.fillOval(cx + 35, cy + 35, 50, 50);
            g2.fillOval(cx + 175, cy + 35, 50, 50);

            g2.setColor(Color.LIGHT_GRAY);
            g2.fillOval(cx + 48, cy + 48, 24, 24);
            g2.fillOval(cx + 188, cy + 48, 24, 24);

            // Headlight
            g2.setColor(Color.YELLOW);
            g2.fillArc(cx + 245, cy + 15, 15, 20, -90, 180);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {}
            new NanoPaintUI().setVisible(true);
        });
    }
}
