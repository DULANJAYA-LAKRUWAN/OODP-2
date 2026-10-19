package lk.jiat.jms;

// Component Interface
interface Pizza {
    String getDescription();
    double getPrice();
}

// Concrete Components
class ChickenPizza implements Pizza {
    @Override
    public String getDescription() {
        return "Chicken Pizza";
    }

    @Override
    public double getPrice() {
        return 850.0;
    }
}

class VeggiePizza implements Pizza {
    @Override
    public String getDescription() {
        return "Veggie Pizza";
    }

    @Override
    public double getPrice() {
        return 650.0;
    }
}

class FishPizza implements Pizza {
    @Override
    public String getDescription() {
        return "Fish Pizza";
    }

    @Override
    public double getPrice() {
        return 720.0;
    }
}

// Abstract Decorator
abstract class PizzaDecorator implements Pizza {
    protected Pizza pizza;

    public PizzaDecorator(Pizza pizza) {
        this.pizza = pizza;
    }

    @Override
    public String getDescription() {
        return pizza.getDescription();
    }

    @Override
    public double getPrice() {
        return pizza.getPrice();
    }
}

// Concrete Decorators
class BBQSauce extends PizzaDecorator {
    public BBQSauce(Pizza pizza) {
        super(pizza);
    }

    @Override
    public String getDescription() {
        return pizza.getDescription() + ", BBQ Sauce";
    }

    @Override
    public double getPrice() {
        return pizza.getPrice() + 50.0;
    }
}

class SwissCheese extends PizzaDecorator {
    public SwissCheese(Pizza pizza) {
        super(pizza);
    }

    @Override
    public String getDescription() {
        return pizza.getDescription() + ", Swiss Cheese";
    }

    @Override
    public double getPrice() {
        return pizza.getPrice() + 80.0;
    }
}

class FriedOnions extends PizzaDecorator {
    public FriedOnions(Pizza pizza) {
        super(pizza);
    }

    @Override
    public String getDescription() {
        return pizza.getDescription() + ", Fried Onions";
    }

    @Override
    public double getPrice() {
        return pizza.getPrice() + 20.0;
    }
}

class Mayonnaise extends PizzaDecorator {
    public Mayonnaise(Pizza pizza) {
        super(pizza);
    }

    @Override
    public String getDescription() {
        return pizza.getDescription() + ", Mayonnaise";
    }

    @Override
    public double getPrice() {
        return pizza.getPrice() + 35.0;
    }
}

public class Main {
    public static void main(String[] args) {
        System.out.println("=============================================================");
        System.out.println("  Practical 01: Decorator Pattern Demonstration (Pizza Blast) ");
        System.out.println("=============================================================");

        // 1. Fish Pizza with BBQ Sauce & Swiss Cheese
        Pizza order1 = new FishPizza();
        order1 = new BBQSauce(order1);
        order1 = new SwissCheese(order1);
        System.out.println("\nOrder 1: " + order1.getDescription());
        System.out.println("Price  : Rs. " + order1.getPrice());

        // 2. Chicken Pizza with BBQ Sauce, Fried Onions & Mayonnaise
        Pizza order2 = new ChickenPizza();
        order2 = new BBQSauce(order2);
        order2 = new FriedOnions(order2);
        order2 = new Mayonnaise(order2);
        System.out.println("\nOrder 2: " + order2.getDescription());
        System.out.println("Price  : Rs. " + order2.getPrice());

        // 3. Veggie Pizza with Swiss Cheese & Fried Onions
        Pizza order3 = new VeggiePizza();
        order3 = new SwissCheese(order3);
        order3 = new FriedOnions(order3);
        System.out.println("\nOrder 3: " + order3.getDescription());
        System.out.println("Price  : Rs. " + order3.getPrice());

        double total = order1.getPrice() + order2.getPrice() + order3.getPrice();
        System.out.println("\n-------------------------------------------------------------");
        System.out.println("Total Price for All Orders: Rs. " + total);
        System.out.println("=============================================================");
    }
}

