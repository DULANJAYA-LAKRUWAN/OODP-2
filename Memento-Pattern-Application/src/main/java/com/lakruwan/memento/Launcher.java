package com.lakruwan.memento;

import java.util.Scanner;

/**
 * Launcher for Memento Pattern Application
 * 
 * Allows running:
 * 1. Console Demonstration (Practical 09 lk.jiat.jms.Main)
 * 2. Interactive Swing GUI Application (FileEditorUI)
 */
public class Launcher {
    public static void main(String[] args) {
        System.out.println("==============================================================");
        System.out.println("       Practical 09: Memento Pattern - Application Launcher   ");
        System.out.println("==============================================================");
        System.out.println("Select an option:");
        System.out.println("  [1] Run Practical 09 Console Demo (lk.jiat.jms.Main)");
        System.out.println("  [2] Launch Interactive GUI File Editor (Swing App)");
        System.out.println("  [3] Exit");
        System.out.print("Enter choice (1-3) [default: 1]: ");

        String choice = "1";
        try {
            Scanner scanner = new Scanner(System.in);
            if (scanner.hasNextLine()) {
                String input = scanner.nextLine().trim();
                if (!input.isEmpty()) {
                    choice = input;
                }
            }
        } catch (Exception ignored) {}

        switch (choice) {
            case "2":
                System.out.println("\nLaunching Interactive GUI Application...");
                FileEditorUI.main(args);
                break;
            case "3":
                System.out.println("\nExiting. Goodbye!");
                break;
            case "1":
            default:
                System.out.println("\nExecuting Console Practical 09 Demonstration...\n");
                lk.jiat.jms.Main.main(args);
                break;
        }
    }
}

