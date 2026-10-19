package com.lakruwan.decorator;

import com.lakruwan.decorator.component.ChickenPizza;
import com.lakruwan.decorator.component.FishPizza;
import com.lakruwan.decorator.component.VeggiePizza;
import com.lakruwan.decorator.decorator.BBQSauce;
import com.lakruwan.decorator.decorator.FriedOnions;
import com.lakruwan.decorator.decorator.Mayonnaise;
import com.lakruwan.decorator.decorator.SwissCheese;

import java.util.ArrayList;
import java.util.List;

/**
 * Console demonstration for Practical 01: Decorator Design Pattern.
 * Fulfills all requirements from the "Pizza Blast" scenario.
 */
public class DecoratorApp {

    public static void main(String[] args) {
        System.out.println("======================================================================");
        System.out.println("               WELCOME TO 'PIZZA BLAST' ORDER SYSTEM                  ");
        System.out.println("              Practical 01: Decorator Design Pattern                  ");
        System.out.println("======================================================================\n");

        List<Pizza> customerOrder = new ArrayList<>();

        // Order 1: Fish Pizza with BBQ Sauce & Swiss Cheese
        System.out.println("--------------------------------------------------");
        System.out.println("Item 1: Ordering Fish Pizza with BBQ Sauce & Swiss Cheese");
        System.out.println("--------------------------------------------------");
        Pizza order1 = new FishPizza(); // Base Rs. 720
        order1 = new BBQSauce(order1);  // + Rs. 50
        order1 = new SwissCheese(order1); // + Rs. 80
        customerOrder.add(order1);
        displayPizzaItem(1, order1);

        // Order 2: Chicken Pizza with BBQ Sauce, Fried Onions & Mayonnaise
        System.out.println("\n--------------------------------------------------");
        System.out.println("Item 2: Ordering Chicken Pizza with BBQ Sauce, Fried Onions & Mayonnaise");
        System.out.println("--------------------------------------------------");
        Pizza order2 = new ChickenPizza(); // Base Rs. 850
        order2 = new BBQSauce(order2);     // + Rs. 50
        order2 = new FriedOnions(order2);  // + Rs. 20
        order2 = new Mayonnaise(order2);   // + Rs. 35
        customerOrder.add(order2);
        displayPizzaItem(2, order2);

        // Order 3: Veggie Pizza with Swiss Cheese & Fried Onions
        System.out.println("\n--------------------------------------------------");
        System.out.println("Item 3: Ordering Veggie Pizza with Swiss Cheese & Fried Onions");
        System.out.println("--------------------------------------------------");
        Pizza order3 = new VeggiePizza();   // Base Rs. 650
        order3 = new SwissCheese(order3);   // + Rs. 80
        order3 = new FriedOnions(order3);   // + Rs. 20
        customerOrder.add(order3);
        displayPizzaItem(3, order3);

        // Calculate and display Grand Total
        System.out.println("\n======================================================================");
        System.out.println("                         CUSTOMER ORDER SUMMARY                       ");
        System.out.println("======================================================================");
        double grandTotal = 0.0;
        for (int i = 0; i < customerOrder.size(); i++) {
            Pizza p = customerOrder.get(i);
            System.out.printf(" %d. %-56s : Rs. %8.2f%n", (i + 1), p.getDescription(), p.getPrice());
            grandTotal += p.getPrice();
        }
        System.out.println("----------------------------------------------------------------------");
        System.out.printf(" GRAND TOTAL                                             : Rs. %8.2f%n", grandTotal);
        System.out.println("======================================================================\n");
    }

    private static void displayPizzaItem(int itemNo, Pizza pizza) {
        System.out.println(" Description : " + pizza.getDescription());
        System.out.printf(" Total Price : Rs. %.2f%n", pizza.getPrice());
    }
}

