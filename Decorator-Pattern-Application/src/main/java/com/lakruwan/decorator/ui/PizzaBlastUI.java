package com.lakruwan.decorator.ui;

import com.lakruwan.decorator.Pizza;
import com.lakruwan.decorator.component.ChickenPizza;
import com.lakruwan.decorator.component.FishPizza;
import com.lakruwan.decorator.component.VeggiePizza;
import com.lakruwan.decorator.decorator.BBQSauce;
import com.lakruwan.decorator.decorator.FriedOnions;
import com.lakruwan.decorator.decorator.Mayonnaise;
import com.lakruwan.decorator.decorator.SwissCheese;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Interactive Swing User Interface for Practical 01: Pizza Blast Decorator Pattern.
 */
public class PizzaBlastUI extends JFrame {

    // Theme Colors
    private static final Color PRIMARY_COLOR = new Color(220, 53, 69);     // Crimson Red
    private static final Color ACCENT_COLOR = new Color(255, 193, 7);      // Gold / Yellow
    private static final Color SUCCESS_COLOR = new Color(40, 167, 69);     // Emerald Green
    private static final Color BG_COLOR = new Color(248, 249, 250);        // Off-white

    // UI Components
    private JRadioButton rbChicken;
    private JRadioButton rbFish;
    private JRadioButton rbVeggie;
    private ButtonGroup basePizzaGroup;

    private JCheckBox cbBBQSauce;
    private JCheckBox cbSwissCheese;
    private JCheckBox cbFriedOnions;
    private JCheckBox cbMayonnaise;

    private JLabel lblLivePrice;
    private JLabel lblLiveDescription;

    private DefaultTableModel tableModel;
    private JLabel lblGrandTotal;

    private final List<Pizza> currentOrder = new ArrayList<>();

    public PizzaBlastUI() {
        super("Pizza Blast — Practical 01: Decorator Pattern Demonstration");
        initComponents();
        updateLivePreview();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(920, 680);
        setMinimumSize(new Dimension(840, 600));
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(14, 14));
        mainPanel.setBackground(BG_COLOR);
        mainPanel.setBorder(new EmptyBorder(16, 16, 16, 16));

        // ================= Header Panel =================
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setBorder(new EmptyBorder(16, 20, 16, 20));

        JLabel titleLabel = new JLabel("🍕 Pizza Blast — Custom Pizza Decorator");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);

        JLabel subLabel = new JLabel("Practical 01: Decorator Design Pattern • Dynamically wrap base pizzas with custom extra toppings");
        subLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subLabel.setForeground(new Color(255, 235, 235));

        headerPanel.add(titleLabel, BorderLayout.NORTH);
        headerPanel.add(subLabel, BorderLayout.SOUTH);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // ================= Center Split =================
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setResizeWeight(0.48);
        splitPane.setDividerSize(6);
        splitPane.setOpaque(false);

        // --- Left: Pizza Builder Card ---
        JPanel leftPanel = new JPanel(new BorderLayout(10, 10));
        leftPanel.setOpaque(false);

        JPanel builderCard = new JPanel();
        builderCard.setLayout(new BoxLayout(builderCard, BoxLayout.Y_AXIS));
        builderCard.setBackground(Color.WHITE);
        builderCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(222, 226, 230), 1),
                new EmptyBorder(14, 16, 14, 16)
        ));

        // 1. Base Pizza Selection
        JLabel lblBase = new JLabel("1. Select Base Pizza (ConcreteComponent):");
        lblBase.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblBase.setAlignmentX(Component.LEFT_ALIGNMENT);
        builderCard.add(lblBase);
        builderCard.add(Box.createRigidArea(new Dimension(0, 6)));

        rbChicken = new JRadioButton("Chicken Pizza (Rs. 850.00)", true);
        rbFish = new JRadioButton("Fish Pizza (Rs. 720.00)");
        rbVeggie = new JRadioButton("Veggie Pizza (Rs. 650.00)");

        Font radioFont = new Font("Segoe UI", Font.PLAIN, 13);
        rbChicken.setFont(radioFont); rbChicken.setOpaque(false);
        rbFish.setFont(radioFont); rbFish.setOpaque(false);
        rbVeggie.setFont(radioFont); rbVeggie.setOpaque(false);

        basePizzaGroup = new ButtonGroup();
        basePizzaGroup.add(rbChicken);
        basePizzaGroup.add(rbFish);
        basePizzaGroup.add(rbVeggie);

        rbChicken.addItemListener(e -> updateLivePreview());
        rbFish.addItemListener(e -> updateLivePreview());
        rbVeggie.addItemListener(e -> updateLivePreview());

        builderCard.add(rbChicken);
        builderCard.add(rbFish);
        builderCard.add(rbVeggie);

        builderCard.add(Box.createRigidArea(new Dimension(0, 14)));

        // 2. Extra Toppings Selection (Decorators)
        JLabel lblToppings = new JLabel("2. Add Extra Toppings (ConcreteDecorators):");
        lblToppings.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblToppings.setAlignmentX(Component.LEFT_ALIGNMENT);
        builderCard.add(lblToppings);
        builderCard.add(Box.createRigidArea(new Dimension(0, 6)));

        cbBBQSauce = new JCheckBox("BBQ Sauce (+ Rs. 50.00)");
        cbSwissCheese = new JCheckBox("Swiss Cheese (+ Rs. 80.00)");
        cbFriedOnions = new JCheckBox("Fried Onions (+ Rs. 20.00)");
        cbMayonnaise = new JCheckBox("Mayonnaise (+ Rs. 35.00)");

        JCheckBox[] toppingBoxes = {cbBBQSauce, cbSwissCheese, cbFriedOnions, cbMayonnaise};
        for (JCheckBox cb : toppingBoxes) {
            cb.setFont(radioFont);
            cb.setOpaque(false);
            cb.addItemListener(e -> updateLivePreview());
            builderCard.add(cb);
        }

        builderCard.add(Box.createRigidArea(new Dimension(0, 14)));

        // 3. Live Preview Banner
        JPanel previewPanel = new JPanel(new GridLayout(2, 1, 4, 4));
        previewPanel.setBackground(new Color(254, 249, 231));
        previewPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(241, 196, 15), 1),
                new EmptyBorder(10, 12, 10, 12)
        ));
        previewPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        lblLiveDescription = new JLabel("Selected: Chicken Pizza");
        lblLiveDescription.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblLiveDescription.setForeground(new Color(125, 102, 8));

        lblLivePrice = new JLabel("Current Price: Rs. 850.00");
        lblLivePrice.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblLivePrice.setForeground(PRIMARY_COLOR);

        previewPanel.add(lblLiveDescription);
        previewPanel.add(lblLivePrice);
        builderCard.add(previewPanel);

        builderCard.add(Box.createRigidArea(new Dimension(0, 14)));

        // Add to Order Button
        JButton btnAddToCart = new JButton("🛒 Add Decorated Pizza to Order");
        btnAddToCart.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnAddToCart.setBackground(SUCCESS_COLOR);
        btnAddToCart.setForeground(Color.WHITE);
        btnAddToCart.setFocusPainted(false);
        btnAddToCart.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAddToCart.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnAddToCart.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btnAddToCart.addActionListener(e -> addCurrentPizzaToOrder());
        builderCard.add(btnAddToCart);

        leftPanel.add(builderCard, BorderLayout.CENTER);

        // Quick Preset Buttons (Matches Practical 01 Scenarios)
        JPanel presetPanel = new JPanel(new GridLayout(3, 1, 6, 6));
        presetPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 205, 215)),
                "Quick Test Scenarios (From Sheet)",
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 12)
        ));
        presetPanel.setBackground(Color.WHITE);

        JButton btnPreset1 = new JButton("Load #1: Fish Pizza + BBQ + Swiss (Rs. 850)");
        JButton btnPreset2 = new JButton("Load #2: Chicken Pizza + BBQ + Onion + Mayo (Rs. 955)");
        JButton btnPreset3 = new JButton("Load #3: Veggie Pizza + Swiss + Onion (Rs. 750)");

        setupPresetButton(btnPreset1, () -> loadPreset(2, true, true, false, false));
        setupPresetButton(btnPreset2, () -> loadPreset(1, true, false, true, true));
        setupPresetButton(btnPreset3, () -> loadPreset(3, false, true, true, false));

        presetPanel.add(btnPreset1);
        presetPanel.add(btnPreset2);
        presetPanel.add(btnPreset3);
        leftPanel.add(presetPanel, BorderLayout.SOUTH);

        // --- Right: Customer Order Table & Checkout ---
        JPanel rightPanel = new JPanel(new BorderLayout(10, 10));
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(222, 226, 230), 1),
                new EmptyBorder(14, 16, 14, 16)
        ));

        JLabel orderHeader = new JLabel("Current Customer Order");
        orderHeader.setFont(new Font("Segoe UI", Font.BOLD, 16));
        rightPanel.add(orderHeader, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{"#", "Pizza Description", "Price (Rs.)"}, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        JTable orderTable = new JTable(tableModel);
        orderTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        orderTable.setRowHeight(28);
        orderTable.getColumnModel().getColumn(0).setPreferredWidth(35);
        orderTable.getColumnModel().getColumn(1).setPreferredWidth(340);
        orderTable.getColumnModel().getColumn(2).setPreferredWidth(95);

        JScrollPane tableScroll = new JScrollPane(orderTable);
        rightPanel.add(tableScroll, BorderLayout.CENTER);

        // Bottom Summary & Actions
        JPanel summaryPanel = new JPanel(new BorderLayout(10, 10));
        summaryPanel.setOpaque(false);

        lblGrandTotal = new JLabel("Grand Total: Rs. 0.00", SwingConstants.RIGHT);
        lblGrandTotal.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblGrandTotal.setForeground(new Color(40, 50, 70));

        JPanel tableButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        tableButtons.setOpaque(false);

        JButton btnAddAllPresets = new JButton("Load All 3 Sheet Items");
        btnAddAllPresets.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnAddAllPresets.addActionListener(e -> {
            loadPreset(2, true, true, false, false);
            addCurrentPizzaToOrder();
            loadPreset(1, true, false, true, true);
            addCurrentPizzaToOrder();
            loadPreset(3, false, true, true, false);
            addCurrentPizzaToOrder();
        });

        JButton btnClear = new JButton("Clear Order");
        btnClear.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnClear.addActionListener(e -> {
            currentOrder.clear();
            tableModel.setRowCount(0);
            updateGrandTotal();
        });

        tableButtons.add(btnAddAllPresets);
        tableButtons.add(btnClear);

        summaryPanel.add(lblGrandTotal, BorderLayout.NORTH);
        summaryPanel.add(tableButtons, BorderLayout.SOUTH);
        rightPanel.add(summaryPanel, BorderLayout.SOUTH);

        splitPane.setLeftComponent(leftPanel);
        splitPane.setRightComponent(rightPanel);
        mainPanel.add(splitPane, BorderLayout.CENTER);

        setContentPane(mainPanel);
    }

    private void setupPresetButton(JButton btn, Runnable action) {
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        btn.setBackground(new Color(240, 243, 246));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addActionListener(e -> action.run());
    }

    private void loadPreset(int baseIndex, boolean bbq, boolean cheese, boolean onions, boolean mayo) {
        if (baseIndex == 1) rbChicken.setSelected(true);
        else if (baseIndex == 2) rbFish.setSelected(true);
        else rbVeggie.setSelected(true);

        cbBBQSauce.setSelected(bbq);
        cbSwissCheese.setSelected(cheese);
        cbFriedOnions.setSelected(onions);
        cbMayonnaise.setSelected(mayo);

        updateLivePreview();
    }

    private Pizza buildCurrentPizza() {
        // Step 1: Instantiate ConcreteComponent
        Pizza pizza;
        if (rbChicken.isSelected()) {
            pizza = new ChickenPizza();
        } else if (rbFish.isSelected()) {
            pizza = new FishPizza();
        } else {
            pizza = new VeggiePizza();
        }

        // Step 2: Dynamically wrap with ConcreteDecorators
        if (cbBBQSauce.isSelected()) {
            pizza = new BBQSauce(pizza);
        }
        if (cbSwissCheese.isSelected()) {
            pizza = new SwissCheese(pizza);
        }
        if (cbFriedOnions.isSelected()) {
            pizza = new FriedOnions(pizza);
        }
        if (cbMayonnaise.isSelected()) {
            pizza = new Mayonnaise(pizza);
        }

        return pizza;
    }

    private void updateLivePreview() {
        Pizza current = buildCurrentPizza();
        lblLiveDescription.setText("<html><b>Selected:</b> " + current.getDescription() + "</html>");
        lblLivePrice.setText(String.format("Current Price: Rs. %.2f", current.getPrice()));
    }

    private void addCurrentPizzaToOrder() {
        Pizza pizza = buildCurrentPizza();
        currentOrder.add(pizza);
        tableModel.addRow(new Object[]{
                currentOrder.size(),
                pizza.getDescription(),
                String.format("%.2f", pizza.getPrice())
        });
        updateGrandTotal();
    }

    private void updateGrandTotal() {
        double total = 0.0;
        for (Pizza p : currentOrder) {
            total += p.getPrice();
        }
        lblGrandTotal.setText(String.format("Grand Total: Rs. %.2f", total));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
            }
            new PizzaBlastUI().setVisible(true);
        });
    }
}

