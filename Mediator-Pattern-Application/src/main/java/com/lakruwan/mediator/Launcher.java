package com.lakruwan.mediator;

import com.lakruwan.mediator.taxi.TaxiServiceApp;
import lk.jiat.jms.AirportUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Unified Application Launcher for all Mediator Pattern Implementations.
 * Practical 08 & Coursework Demonstrations.
 */
public class Launcher {

    public static void main(String[] args) {
        if (args.length > 0) {
            String target = args[0].toLowerCase();
            switch (target) {
                case "taxi":
                    TaxiServiceApp.main(new String[]{});
                    return;
                case "atc":
                case "airport":
                    lk.jiat.jms.Main.main(new String[]{});
                    return;
                case "chat":
                    com.lakruwan.mediator.ChatApp.main(new String[]{});
                    return;
                default:
                    System.out.println("Unknown target: " + target + ". Available: taxi, atc, chat");
            }
        }

        // Launch Master GUI Dashboard if desktop is available
        if (!GraphicsEnvironment.isHeadless()) {
            SwingUtilities.invokeLater(() -> {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception ignored) {
                }
                new MasterDashboard().setVisible(true);
            });
        } else {
            // Headless fallback: run taxi demo
            TaxiServiceApp.main(new String[]{});
        }
    }

    private static class MasterDashboard extends JFrame {
        public MasterDashboard() {
            setTitle("JIAT OODP2 - Practical 08: Mediator Pattern Suite");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(560, 420);
            setLocationRelativeTo(null);
            setResizable(false);

            JPanel root = new JPanel(new BorderLayout(16, 16));
            root.setBackground(new Color(245, 247, 250));
            root.setBorder(new EmptyBorder(20, 24, 20, 24));

            // Header
            JPanel header = new JPanel(new GridLayout(2, 1, 4, 4));
            header.setOpaque(false);
            JLabel title = new JLabel("Mediator Pattern Application Suite", SwingConstants.CENTER);
            title.setFont(new Font("Segoe UI", Font.BOLD, 20));
            title.setForeground(new Color(24, 34, 54));

            JLabel subtitle = new JLabel("Select an application below to launch:", SwingConstants.CENTER);
            subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            subtitle.setForeground(new Color(100, 110, 130));

            header.add(title);
            header.add(subtitle);
            root.add(header, BorderLayout.NORTH);

            // Button Cards
            JPanel cards = new JPanel(new GridLayout(3, 1, 12, 12));
            cards.setOpaque(false);

            JButton taxiBtn = createButton("🚖 Launch Taxi Service App (Practical 08)",
                    "Customer-Driver Dispatch via Centralized Taxi App Center",
                    new Color(255, 179, 0), new Color(20, 20, 20),
                    () -> TaxiServiceApp.main(new String[]{}));

            JButton atcBtn = createButton("🛫 Launch Airport Control Tower (ATC)",
                    "Flight Coordination, Landing, and Takeoff via AirportControlTower",
                    new Color(24, 119, 242), Color.WHITE,
                    () -> {
                        AirportUI ui = new AirportUI();
                        ui.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        ui.setVisible(true);
                    });

            JButton chatBtn = createButton("💬 Run Chat Room Mediator Demo",
                    "Decoupled User Messaging System via ChatRoom Mediator",
                    new Color(52, 73, 94), Color.WHITE,
                    () -> com.lakruwan.mediator.ChatApp.main(new String[]{}));

            cards.add(taxiBtn);
            cards.add(atcBtn);
            cards.add(chatBtn);
            root.add(cards, BorderLayout.CENTER);

            setContentPane(root);
        }

        private JButton createButton(String text, String tooltip, Color bg, Color fg, Runnable action) {
            JButton btn = new JButton("<html><div style='text-align: center;'><b>"
                    + text + "</b><br><small style='color: #444;'>" + tooltip + "</small></div></html>");
            btn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            btn.setBackground(bg);
            btn.setForeground(fg);
            btn.setFocusPainted(false);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btn.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(200, 205, 215), 1),
                    new EmptyBorder(10, 16, 10, 16)
            ));
            btn.addActionListener(e -> action.run());
            return btn;
        }
    }
}
