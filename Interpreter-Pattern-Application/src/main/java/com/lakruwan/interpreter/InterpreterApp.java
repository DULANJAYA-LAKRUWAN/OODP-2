package com.lakruwan.interpreter;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Main application launcher for Practical 07 - Interpreter Pattern.
 */
public class InterpreterApp {

    public static void main(String[] args) {
        // Console test demonstration of the Interpreter Pattern
        System.out.println("==================================================");
        System.out.println("  Practical 07 - Interpreter Pattern Demonstration ");
        System.out.println("==================================================");

        // Test Case 1: Number to Text
        String numInput = "9317";
        Expression numExpr = ExpressionParser.parseNumberToText(numInput);
        String textOutput = numExpr.interpret();
        System.out.println("Input (Number): " + numInput);
        System.out.println("Output (Text) : " + textOutput);

        System.out.println("--------------------------------------------------");

        // Test Case 2: Text to Number
        String textInput = "Nine One Four";
        Expression textExpr = ExpressionParser.parseTextToNumber(textInput);
        String numOutput = textExpr.interpret();
        System.out.println("Input (Text)  : " + textInput);
        System.out.println("Output (Number): " + numOutput);
        System.out.println("==================================================");

        // Launch GUI
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
            }
            InterpreterUI ui = new InterpreterUI();
            ui.setVisible(true);
        });
    }
}
