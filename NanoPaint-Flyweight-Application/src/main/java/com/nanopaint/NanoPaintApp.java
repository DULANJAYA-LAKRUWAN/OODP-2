package com.nanopaint;

import java.util.HashMap;
import java.util.Map;

/**
 * Practical 06: Flyweight Pattern
 * "NanoPaint" Car Painting Shop Demonstration
 * 
 * UML Mapping:
 * - Context              -> Context interface with operation()
 * - ConcreteContext      -> ConcreteContext (represents Car with extrinsic state & shared color)
 * - SharedContext        -> SharedContext (Flyweight object holding intrinsic color state)
 * - SharedContextPool    -> SharedContextPool (Flyweight Factory managing cached shared contexts)
 */

// 1. Context Interface
interface Context {
    void operation();
}

// 2. SharedContext (Flyweight Object - Intrinsic State)
// Holds intrinsic state that is common and shared across multiple cars (e.g. Paint Color)
class SharedContext {
    private final String intrinsicState; // Color name (e.g., "Blue", "Black", "Red", "Silver")

    public SharedContext(String intrinsicState) {
        this.intrinsicState = intrinsicState;
        System.out.println("[POOL] Creating new SharedContext (Color) instance for: " + intrinsicState);
    }

    public String getIntrinsicState() {
        return intrinsicState;
    }

    public String getColorName() {
        return intrinsicState;
    }
}

// 3. SharedContextPool (Flyweight Factory / Cache)
// Manages the shared collection of SharedContext (Color) objects
class SharedContextPool {
    // Collection to store and reuse shared flyweight contexts
    private static final Map<String, SharedContext> sharedContextCollection = new HashMap<>();

    // Returns an existing SharedContext if available, otherwise creates and caches a new one
    public static SharedContext getSharedContext(String colorName) {
        String key = colorName.trim().toUpperCase();
        if (!sharedContextCollection.containsKey(key)) {
            sharedContextCollection.put(key, new SharedContext(colorName));
        }
        return sharedContextCollection.get(key);
    }

    public static int getTotalSharedContexts() {
        return sharedContextCollection.size();
    }
}

// 4. ConcreteContext (Context Object - Extrinsic State)
// Stores extrinsic state unique to each car (model, license plate) and holds a reference to the SharedContext
class ConcreteContext implements Context {
    private final String extrinsicState;       // Unique Car details: Model & Plate Number
    private final SharedContext sharedContext; // Reference to the shared Flyweight (Color)

    public ConcreteContext(String extrinsicState, SharedContext sharedContext) {
        this.extrinsicState = extrinsicState;
        this.sharedContext = sharedContext;
    }

    @Override
    public void operation() {
        System.out.printf("  -> Painted Car: %-25s | Color: %-10s | Color HashCode: %d%n",
                extrinsicState,
                sharedContext.getIntrinsicState(),
                System.identityHashCode(sharedContext));
    }

    public String getExtrinsicState() {
        return extrinsicState;
    }

    public SharedContext getSharedContext() {
        return sharedContext;
    }
}

// 5. Main Application ("NanoPaint" Car Painting Shop)
public class NanoPaintApp {
    public static void main(String[] args) {
        System.out.println("=====================================================================");
        System.out.println("            WELCOME TO 'NANOPAINT' CAR PAINTING SHOP                 ");
        System.out.println("                   FLYWEIGHT PATTERN DEMONSTRATION                   ");
        System.out.println("=====================================================================\n");

        System.out.println("--- STEP 1: Painting Cars with Various Colors ---\n");

        // Painting multiple cars with the 4 colors shown in the diagram:
        // Blue, Black, Red, Silver

        // 1. Blue Cars
        SharedContext blueColor = SharedContextPool.getSharedContext("Blue");
        Context car1 = new ConcreteContext("Toyota Corolla [WP-CA-1024]", blueColor);
        Context car2 = new ConcreteContext("Nissan Leaf    [WP-CB-5542]", blueColor);

        // 2. Black Cars
        SharedContext blackColor = SharedContextPool.getSharedContext("Black");
        Context car3 = new ConcreteContext("BMW 520d       [WP-KM-9988]", blackColor);
        Context car4 = new ConcreteContext("Audi A4        [WP-KH-7711]", blackColor);

        // 3. Red Cars
        SharedContext redColor = SharedContextPool.getSharedContext("Red");
        Context car5 = new ConcreteContext("Mazda 3        [WP-PJ-3321]", redColor);
        Context car6 = new ConcreteContext("Honda Civic    [WP-PG-4412]", redColor);

        // 4. Silver Cars
        SharedContext silverColor = SharedContextPool.getSharedContext("Silver");
        Context car7 = new ConcreteContext("Mercedes C200  [WP-KO-2255]", silverColor);
        Context car8 = new ConcreteContext("Hyundai Tucson [WP-KV-1188]", silverColor);

        // More cars using existing colors (reusing shared flyweight objects from the pool)
        Context car9 = new ConcreteContext("Suzuki Swift   [WP-CE-6644]", SharedContextPool.getSharedContext("Blue"));
        Context car10 = new ConcreteContext("Ford Mustang  [WP-KT-0007]", SharedContextPool.getSharedContext("Red"));

        System.out.println("\n--- STEP 2: Executing Paint Operations on All Cars ---\n");
        Context[] workshopCars = {car1, car2, car3, car4, car5, car6, car7, car8, car9, car10};

        for (Context car : workshopCars) {
            car.operation();
        }

        System.out.println("\n=====================================================================");
        System.out.println("                     MEMORY USAGE ANALYSIS                           ");
        System.out.println("=====================================================================");
        System.out.println("Total Car Objects Created (Extrinsic State)   : " + workshopCars.length);
        System.out.println("Total Color Objects In Memory (Intrinsic State): " + SharedContextPool.getTotalSharedContexts());
        System.out.println("Memory Savings: 10 cars painted using only " + SharedContextPool.getTotalSharedContexts() + " shared Color instances in the pool!");
        System.out.println("Notice that cars sharing the same color share the exact same Color HashCode!");
        System.out.println("=====================================================================");
    }
}
