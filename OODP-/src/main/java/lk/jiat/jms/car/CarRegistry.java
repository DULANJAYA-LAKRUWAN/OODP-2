package lk.jiat.jms.car;

import java.util.HashMap;
import java.util.Map;

public class CarRegistry {
    private static final Map<String, Car> registry = new HashMap<>();

    static {
        loadPrototypes();
    }

    private static void loadPrototypes() {
        // Base prototypes configured as per Practical 10 requirements
        // Prototype 1: Audi A5 Cabriolet (Matches the practical sheet diagram image!)
        registry.put("AUDI_CABRIOLET", new Car(
                "Audi",
                "A5 Cabriolet",
                "Deep Sea Blue",
                2024,
                55000.0,
                "Petrol",
                "Convertible",
                "2.0L TFSI Turbo"
        ));

        // Prototype 2: Tesla Model 3
        registry.put("TESLA_SEDAN", new Car(
                "Tesla",
                "Model 3",
                "Pearl White",
                2024,
                42000.0,
                "Electric",
                "Sedan",
                "Dual Motor AWD"
        ));

        // Prototype 3: Ford Mustang GT
        registry.put("MUSTANG_COUPE", new Car(
                "Ford",
                "Mustang GT",
                "Race Red",
                2024,
                48000.0,
                "Petrol",
                "Fastback Coupe",
                "5.0L V8"
        ));
    }

    public static void addCar(String key, Car car) {
        registry.put(key.toUpperCase(), car);
    }

    public static Car getCar(String key) {
        Car prototype = registry.get(key.toUpperCase());
        if (prototype != null) {
            return prototype.clone(); // Returns an independent cloned copy!
        }
        System.err.println("Car model key '" + key + "' not found in registry.");
        return null;
    }
}
