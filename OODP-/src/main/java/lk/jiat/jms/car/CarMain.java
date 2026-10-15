package lk.jiat.jms.car;

public class CarMain {
    public static void main(String[] args) {
        System.out.println("========================================================");
        System.out.println("         PRACTICAL 10 - CAR PROTOTYPE PATTERN           ");
        System.out.println("========================================================");

        // 1. Fetching base prototype from Registry
        System.out.println("\n[1] Fetching Base Prototype (Audi A5 Cabriolet from Image):");
        Car baseAudi = CarRegistry.getCar("AUDI_CABRIOLET");
        baseAudi.displayCarInfo();

        // 2. Customer 1 orders a customized version of Audi A5 Cabriolet
        System.out.println("\n[2] Customer 1 Order: Customizing Cloned Audi A5 Cabriolet:");
        System.out.println("-> Custom Requirements: Phantom Black color, Year 2025, Upgraded 3.0L V6 Turbo engine, Price $64,500");
        Car customer1Audi = CarRegistry.getCar("AUDI_CABRIOLET"); // Cloned copy
        customer1Audi.setColor("Phantom Black");
        customer1Audi.setYear(2025);
        customer1Audi.setEngineType("3.0L V6 Turbo");
        customer1Audi.setPrice(64500.0);
        customer1Audi.displayCarInfo();

        // 3. Customer 2 orders another customized version
        System.out.println("\n[3] Customer 2 Order: Customizing Another Cloned Audi A5 Cabriolet:");
        System.out.println("-> Custom Requirements: Tango Red color, Price $57,200");
        Car customer2Audi = CarRegistry.getCar("AUDI_CABRIOLET"); // Another independent clone
        customer2Audi.setColor("Tango Red");
        customer2Audi.setPrice(57200.0);
        customer2Audi.displayCarInfo();

        // 4. Verification that original base prototype in the Registry is completely unchanged
        System.out.println("\n[4] Verifying Base Prototype in Registry remains intact and unchanged:");
        Car freshBaseAudi = CarRegistry.getCar("AUDI_CABRIOLET");
        freshBaseAudi.displayCarInfo();

        // 5. Electric Car Prototype (Tesla Model 3) customization
        System.out.println("\n[5] Customizing an Electric Vehicle Prototype (Tesla Model 3):");
        Car customTesla = CarRegistry.getCar("TESLA_SEDAN");
        customTesla.setColor("Midnight Silver Metallic");
        customTesla.setEngineType("Performance Plaid Dual Motor");
        customTesla.setPrice(52990.0);
        customTesla.displayCarInfo();

        System.out.println("\n========================================================");
        System.out.println("Practical 10 Execution Completed Successfully!");
        System.out.println("========================================================");
    }
}
