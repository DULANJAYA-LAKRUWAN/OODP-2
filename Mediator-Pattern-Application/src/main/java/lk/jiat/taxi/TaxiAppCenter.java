package lk.jiat.taxi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Concrete Mediator representing the Taxi App Center.
 * Centralizes communication between Customers and Drivers:
 * 1. Receives customer ride requests.
 * 2. Finds an available driver.
 * 3. Sends customer location to the assigned driver.
 * 4. Notifies the customer.
 */
public class TaxiAppCenter implements TaxiMediator {

    private final List<Driver> drivers = new ArrayList<>();
    private final List<Customer> customers = new ArrayList<>();
    private final List<TaxiAppListener> listeners = new ArrayList<>();

    public void addListener(TaxiAppListener listener) {
        listeners.add(listener);
    }

    public void removeListener(TaxiAppListener listener) {
        listeners.remove(listener);
    }

    private void log(String msg) {
        System.out.println(msg);
        for (TaxiAppListener listener : listeners) {
            listener.onLog(msg);
        }
    }

    private void notifyDriverChanged(Driver driver) {
        for (TaxiAppListener listener : listeners) {
            listener.onDriverStatusChanged(driver);
        }
    }

    public List<Driver> getDrivers() {
        return Collections.unmodifiableList(drivers);
    }

    public List<Customer> getCustomers() {
        return Collections.unmodifiableList(customers);
    }

    @Override
    public void registerDriver(Driver driver) {
        drivers.add(driver);
        log("[Taxi App Center] Driver registered: " + driver.getName());
        notifyDriverChanged(driver);
    }

    @Override
    public void registerCustomer(Customer customer) {
        customers.add(customer);
        log("[Taxi App Center] Customer registered: " + customer.getName());
    }

    @Override
    public void requestTaxi(Customer customer, String location) {
        log("\n--- [Taxi App Center Processing Request] ---");
        log("Customer '" + customer.getName() + "' requested a taxi at location: " + location);

        // Step 1: Find an available taxi
        Driver assignedDriver = findAvailableDriver();

        if (assignedDriver != null) {
            log("Available driver found: " + assignedDriver.getName());

            // Step 2: Send customer location to the available driver
            assignedDriver.receiveRideRequest(customer, location);
            notifyDriverChanged(assignedDriver);

            // Step 3: Notify customer of assigned driver
            String customerMsg = "Taxi dispatched! Driver: " + assignedDriver.getName()
                    + " is on the way to: " + location;
            customer.receiveNotification(customerMsg);
        } else {
            log("No available drivers currently found.");
            String busyMsg = "Sorry, all drivers are currently busy. Please try again shortly.";
            customer.receiveNotification(busyMsg);
        }
        log("--------------------------------------------\n");
    }

    public void notifyTripCompleted(Driver driver) {
        log("[Driver " + driver.getName() + "] Completed trip and is now AVAILABLE.");
        notifyDriverChanged(driver);
    }

    /**
     * Helper method to search registered drivers for an available taxi.
     */
    public Driver findAvailableDriver() {
        for (Driver driver : drivers) {
            if (driver.isAvailable()) {
                return driver;
            }
        }
        return null;
    }
}
