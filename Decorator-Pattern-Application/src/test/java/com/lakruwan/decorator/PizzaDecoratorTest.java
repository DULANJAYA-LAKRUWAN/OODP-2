package com.lakruwan.decorator;

import com.lakruwan.decorator.component.ChickenPizza;
import com.lakruwan.decorator.component.FishPizza;
import com.lakruwan.decorator.component.VeggiePizza;
import com.lakruwan.decorator.decorator.BBQSauce;
import com.lakruwan.decorator.decorator.FriedOnions;
import com.lakruwan.decorator.decorator.Mayonnaise;
import com.lakruwan.decorator.decorator.SwissCheese;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PizzaDecoratorTest {

    @Test
    @DisplayName("Should return base price and description for plain pizzas")
    void testBasePizzas() {
        Pizza chicken = new ChickenPizza();
        assertEquals("Chicken Pizza", chicken.getDescription());
        assertEquals(850.0, chicken.getPrice(), 0.001);

        Pizza fish = new FishPizza();
        assertEquals("Fish Pizza", fish.getDescription());
        assertEquals(720.0, fish.getPrice(), 0.001);

        Pizza veggie = new VeggiePizza();
        assertEquals("Veggie Pizza", veggie.getDescription());
        assertEquals(650.0, veggie.getPrice(), 0.001);
    }

    @Test
    @DisplayName("Practical Scenario 1: Fish Pizza with BBQ Sauce & Swiss Cheese")
    void testOrderScenario1() {
        Pizza pizza = new FishPizza(); // 720
        pizza = new BBQSauce(pizza);   // + 50
        pizza = new SwissCheese(pizza); // + 80

        assertEquals("Fish Pizza, BBQ Sauce, Swiss Cheese", pizza.getDescription());
        assertEquals(850.0, pizza.getPrice(), 0.001);
    }

    @Test
    @DisplayName("Practical Scenario 2: Chicken Pizza with BBQ Sauce, Fried Onions & Mayonnaise")
    void testOrderScenario2() {
        Pizza pizza = new ChickenPizza(); // 850
        pizza = new BBQSauce(pizza);      // + 50
        pizza = new FriedOnions(pizza);   // + 20
        pizza = new Mayonnaise(pizza);    // + 35

        assertEquals("Chicken Pizza, BBQ Sauce, Fried Onions, Mayonnaise", pizza.getDescription());
        assertEquals(955.0, pizza.getPrice(), 0.001);
    }

    @Test
    @DisplayName("Practical Scenario 3: Veggie Pizza with Swiss Cheese & Fried Onions")
    void testOrderScenario3() {
        Pizza pizza = new VeggiePizza();   // 650
        pizza = new SwissCheese(pizza);    // + 80
        pizza = new FriedOnions(pizza);    // + 20

        assertEquals("Veggie Pizza, Swiss Cheese, Fried Onions", pizza.getDescription());
        assertEquals(750.0, pizza.getPrice(), 0.001);
    }

    @Test
    @DisplayName("Should correctly support multiple layers of toppings (e.g. Double Swiss Cheese)")
    void testMultipleSameToppings() {
        Pizza pizza = new ChickenPizza();
        pizza = new SwissCheese(pizza);
        pizza = new SwissCheese(pizza);

        assertEquals("Chicken Pizza, Swiss Cheese, Swiss Cheese", pizza.getDescription());
        assertEquals(850.0 + 80.0 + 80.0, pizza.getPrice(), 0.001);
    }

    @Test
    @DisplayName("Should reject null component in Decorator constructor")
    void testNullPizzaSafety() {
        assertThrows(IllegalArgumentException.class, () -> new BBQSauce(null));
        assertThrows(IllegalArgumentException.class, () -> new SwissCheese(null));
        assertThrows(IllegalArgumentException.class, () -> new FriedOnions(null));
        assertThrows(IllegalArgumentException.class, () -> new Mayonnaise(null));
    }
}

