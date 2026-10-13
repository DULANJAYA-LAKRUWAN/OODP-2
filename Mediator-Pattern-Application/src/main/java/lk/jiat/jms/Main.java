package lk.jiat.jms;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Main execution class for OODP2-8: Air Traffic Control Mediator System.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("  OODP2-8: Airport Mediator System Demonstration   ");
        System.out.println("==================================================");

        AirportControlTower airportControlTower = new AirportControlTower();

        Flight fa1 = new FlightA(airportControlTower, "123");
        Flight fb1 = new FlightB(airportControlTower, "456");
        Flight fb2 = new FlightB(airportControlTower, "789");

        fa1.requestLanding();
        fb1.requestLanding();
        fb2.requestTakeoff();

        System.out.println("==================================================");

        // Launch GUI if not running in headless mode
        if (!java.awt.GraphicsEnvironment.isHeadless()) {
            SwingUtilities.invokeLater(() -> {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception ignored) {
                }
                AirportUI ui = new AirportUI();
                ui.setVisible(true);
            });
        }
    }
}
