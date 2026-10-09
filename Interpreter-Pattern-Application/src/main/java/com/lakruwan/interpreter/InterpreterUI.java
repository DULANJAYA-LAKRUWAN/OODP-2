package com.lakruwan.interpreter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Swing Graphical User Interface matching Practical 07 screenshot.
 * Provides two conversion sections:
 * 1. Number To Text (e.g. 9317 -> Nine Three One Seven)
 * 2. Text To Number (e.g. Nine One Four -> 914)
 */
public class InterpreterUI extends JFrame {

    private static final Color BG_COLOR = new Color(0, 153, 204);       // Cyan / Teal background
    private static final Color BUTTON_COLOR = new Color(0, 195, 130);   // Emerald green button
    private static final Color BUTTON_HOVER = new Color(0, 175, 115);
    private static final Color TEXT_WHITE = Color.WHITE;
    private static final Color DIVIDER_COLOR = new Color(255, 255, 255, 120);

    private JTextField numberToTextField;
    private JLabel numberToTextResultLabel;

    private JTextField textToNumberField;
    private JLabel textToNumberResultLabel;

    public InterpreterUI() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Interpreter Pattern");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(480, 360);
        setMinimumSize(new Dimension(420, 320));
        setLocationRelativeTo(null);

        // Main Container Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(BG_COLOR);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        Font headerFont = new Font("Segoe UI", Font.BOLD, 16);
        Font inputFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font buttonFont = new Font("Segoe UI", Font.BOLD, 14);
        Font resultFont = new Font("Segoe UI", Font.BOLD, 15);

        // ================= SECTION 1: Number To Text =================
        JLabel numToTextHeader = new JLabel("Number To Text");
        numToTextHeader.setFont(headerFont);
        numToTextHeader.setForeground(TEXT_WHITE);
        numToTextHeader.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(numToTextHeader);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 8)));

        // Input Row 1
        JPanel numInputPanel = new JPanel(new BorderLayout(8, 0));
        numInputPanel.setOpaque(false);
        numInputPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        numInputPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        numberToTextField = new JTextField("9317");
        numberToTextField.setFont(inputFont);
        numberToTextField.setPreferredSize(new Dimension(280, 34));
        numberToTextField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255, 80), 1),
                BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));

        JButton numConvertBtn = createStyledButton("Convert", buttonFont);
        numInputPanel.add(numberToTextField, BorderLayout.CENTER);
        numInputPanel.add(numConvertBtn, BorderLayout.EAST);
        mainPanel.add(numInputPanel);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        numberToTextResultLabel = new JLabel("Nine Three One Seven");
        numberToTextResultLabel.setFont(resultFont);
        numberToTextResultLabel.setForeground(TEXT_WHITE);
        numberToTextResultLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(numberToTextResultLabel);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 18)));

        // ================= DIVIDER =================
        JSeparator separator = new JSeparator() {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(DIVIDER_COLOR);
                g.drawLine(0, 0, getWidth(), 0);
            }
        };
        separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        separator.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(separator);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 18)));

        // ================= SECTION 2: Text To Number =================
        JLabel textToNumHeader = new JLabel("Text To Number");
        textToNumHeader.setFont(headerFont);
        textToNumHeader.setForeground(TEXT_WHITE);
        textToNumHeader.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(textToNumHeader);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 8)));

        // Input Row 2
        JPanel textInputPanel = new JPanel(new BorderLayout(8, 0));
        textInputPanel.setOpaque(false);
        textInputPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        textInputPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        textToNumberField = new JTextField("Nine One Four");
        textToNumberField.setFont(inputFont);
        textToNumberField.setPreferredSize(new Dimension(280, 34));
        textToNumberField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255, 80), 1),
                BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));

        JButton textConvertBtn = createStyledButton("Convert", buttonFont);
        textInputPanel.add(textToNumberField, BorderLayout.CENTER);
        textInputPanel.add(textConvertBtn, BorderLayout.EAST);
        mainPanel.add(textInputPanel);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        textToNumberResultLabel = new JLabel("914");
        textToNumberResultLabel.setFont(resultFont);
        textToNumberResultLabel.setForeground(TEXT_WHITE);
        textToNumberResultLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(textToNumberResultLabel);

        // Event Listeners
        ActionListener numConvertAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleNumberToText();
            }
        };
        numConvertBtn.addActionListener(numConvertAction);
        numberToTextField.addActionListener(numConvertAction);

        ActionListener textConvertAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleTextToNumber();
            }
        };
        textConvertBtn.addActionListener(textConvertAction);
        textToNumberField.addActionListener(textConvertAction);

        setContentPane(mainPanel);
    }

    private JButton createStyledButton(String text, Font font) {
        JButton button = new JButton(text);
        button.setFont(font);
        button.setForeground(TEXT_WHITE);
        button.setBackground(BUTTON_COLOR);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(6, 16, 6, 16));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(BUTTON_HOVER);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(BUTTON_COLOR);
            }
        });

        return button;
    }

    /**
     * Executes Interpreter for Number -> Text
     */
    private void handleNumberToText() {
        String input = numberToTextField.getText().trim();
        if (input.isEmpty()) {
            numberToTextResultLabel.setText("Please enter digits (0-9)");
            return;
        }

        try {
            // Build Abstract Syntax Tree using Interpreter Pattern
            Expression expression = ExpressionParser.parseNumberToText(input);
            // Evaluate Expression
            String output = expression.interpret();
            numberToTextResultLabel.setText(output);
            numberToTextResultLabel.setForeground(TEXT_WHITE);
        } catch (IllegalArgumentException ex) {
            numberToTextResultLabel.setText("Error: " + ex.getMessage());
            numberToTextResultLabel.setForeground(new Color(255, 230, 230));
        }
    }

    /**
     * Executes Interpreter for Text -> Number
     */
    private void handleTextToNumber() {
        String input = textToNumberField.getText().trim();
        if (input.isEmpty()) {
            textToNumberResultLabel.setText("Please enter number words");
            return;
        }

        try {
            // Build Abstract Syntax Tree using Interpreter Pattern
            Expression expression = ExpressionParser.parseTextToNumber(input);
            // Evaluate Expression
            String output = expression.interpret();
            textToNumberResultLabel.setText(output);
            textToNumberResultLabel.setForeground(TEXT_WHITE);
        } catch (IllegalArgumentException ex) {
            textToNumberResultLabel.setText("Error: " + ex.getMessage());
            textToNumberResultLabel.setForeground(new Color(255, 230, 230));
        }
    }
}
