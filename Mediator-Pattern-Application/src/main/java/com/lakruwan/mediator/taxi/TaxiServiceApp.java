package com.lakruwan.mediator.taxi;

import com.lakruwan.mediator.taxi.colleague.Customer;
import com.lakruwan.mediator.taxi.colleague.Driver;
import com.lakruwan.mediator.taxi.core.TaxiAppCenter;
import com.lakruwan.mediator.taxi.ui.TaxiAppUI;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Main Executable for Practical 08: Taxi Service Mediator Pattern.
 */
public class TaxiServiceApp {

    public static void main(String[] args) {
        System.out.println("==================================================================");
        System.out.println("     Practical 08: Taxi Service Mediator Pattern Demo             ");
        System.out.println("==================================================================");

        TaxiAppCenter taxiCenter = new TaxiAppCenter();

        Driver driver1 = new Driver(taxiCenter, "Kamal (Toyota Prius - WP CAB-1234)");
        Driver driver2 = new Driver(taxiCenter, "Nimal (Suzuki WagonR - WP CAD-5678)");
        taxiCenter.registerDriver(driver1);
        taxiCenter.registerDriver(driver2);

        Customer customer1 = new Customer(taxiCenter, "Kasun");
        Customer customer2 = new Customer(taxiCenter, "Ruwan");
        Customer customer3 = new Customer(taxiCenter, "Dilshan");
        taxiCenter.registerCustomer(customer1);
        taxiCenter.registerCustomer(customer2);
        taxiCenter.registerCustomer(customer3);

        System.out.println("\n--- [Scenario 1: Customer Kasun requests taxi] ---");
        customer1.requestTaxi("Colombo Fort Railway Station");

        System.out.println("\n--- [Scenario 2: Customer Ruwan requests taxi] ---");
        customer2.requestTaxi("Bambalapitiya Majestic City");

        System.out.println("\n--- [Scenario 3: Customer Dilshan requests taxi (All drivers busy)] ---");
        customer3.requestTaxi("Nugegoda Supermarket");

        System.out.println("\n--- [Scenario 4: Driver Kamal completes trip, Dilshan retries] ---");
        driver1.completeTrip();
        customer3.requestTaxi("Nugegoda Supermarket");

        System.out.println("==================================================================");
        System.out.println("     End of Console Demo - Launching Taxi App Center UI...        ");
        System.out.println("==================================================================");
        System.out.flush();

        // Launch UI
        if (!java.awt.GraphicsEnvironment.isHeadless()) {
            SwingUtilities.invokeLater(() -> {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception ignored) {
                }
                TaxiAppUI ui = new TaxiAppUI(taxiCenter);
                ui.setVisible(true);
            });
        }
    }
}
