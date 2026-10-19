package com.lakruwan.decorator;

import com.lakruwan.decorator.ui.PizzaBlastUI;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import java.awt.GraphicsEnvironment;
import java.util.Scanner;

/**
 * Unified Application Launcher for Practical 01: Decorator Pattern.
 */
public class Launcher {

    public static void main(String[] args) {
        if (args.length > 0) {
            String choice = args[0].toLowerCase();
            if (choice.equals("console") || choice.equals("cli")) {
                DecoratorApp.main(args);
                return;
            } else if (choice.equals("ui") || choice.equals("gui")) {
                launchGUI();
                return;
            }
        }

        // If desktop is available, launch GUI by default
        if (!GraphicsEnvironment.isHeadless()) {
            System.out.println("Launching Pizza Blast Interactive GUI Application...");
            launchGUI();
        } else {
            // Headless fallback
            System.out.println("Headless environment detected. Running Console Demo...\n");
            DecoratorApp.main(args);
        }
    }

    private static void launchGUI() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }
        SwingUtilities.invokeLater(() -> {
            new PizzaBlastUI().setVisible(true);
        });
    }
}

