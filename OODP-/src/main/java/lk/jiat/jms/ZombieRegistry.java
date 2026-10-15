package lk.jiat.jms;

import java.util.HashMap;
import java.util.Map;

public class ZombieRegistry {
    private static final Map<String, Zombie> registry = new HashMap<>();

    static {
        loadPrototypes();
    }

    private static void loadPrototypes() {
        // Base prototypes registered with default attributes
        registry.put("WALKER", new Zombie("Standard Walker Zombie", 15, 100));
        registry.put("RUNNER", new Zombie("Fast Runner Zombie", 25, 60));
        registry.put("TANK", new Zombie("Heavy Tank Zombie", 45, 300));
    }

    public static void addPrototype(String key, Zombie prototype) {
        registry.put(key, prototype);
    }

    public static Zombie getZombie(String key) {
        Zombie prototype = registry.get(key.toUpperCase());
        if (prototype != null) {
            return prototype.clone(); // Return a clone, never the original prototype!
        }
        System.err.println("Zombie type '" + key + "' not found in registry!");
        return null;
    }
}
