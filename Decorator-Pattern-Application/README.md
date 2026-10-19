# Practical 01: Decorator Design Pattern — Pizza Blast

This project implements **Practical 01 (Decorator Design Pattern)** for the **JIAT OODP 2** module based on the **"Pizza Blast"** case study.

---

## 📐 UML Class Diagram Mapping

| UML Element | Implementation Class / Interface | Responsibility |
| :--- | :--- | :--- |
| `<<interface>> Component` | `com.lakruwan.decorator.Pizza` | Defines `getDescription()` and `getPrice()` operations. |
| `ConcreteComponent` | `ChickenPizza` (Rs. 850)<br>`FishPizza` (Rs. 720)<br>`VeggiePizza` (Rs. 650) | Base pizza types providing core pricing and descriptions. |
| `Decorator` | `PizzaToppingDecorator` | Abstract decorator maintaining `#component : Component` (`protected final Pizza pizza`). |
| `ConcreteDecorator` | `BBQSauce` (+Rs. 50)<br>`SwissCheese` (+Rs. 80)<br>`FriedOnions` (+Rs. 20)<br>`Mayonnaise` (+Rs. 35) | Dynamically adds extra toppings and accumulates price. |

---

## 🍕 Customer Order Scenarios

1. **Order 1: Fish Pizza with BBQ Sauce & Swiss Cheese**
   - Base Fish Pizza: Rs. 720
   - BBQ Sauce: Rs. 50
   - Swiss Cheese: Rs. 80
   - **Total**: **Rs. 850.00**

2. **Order 2: Chicken Pizza with BBQ Sauce, Fried Onions & Mayonnaise**
   - Base Chicken Pizza: Rs. 850
   - BBQ Sauce: Rs. 50
   - Fried Onions: Rs. 20
   - Mayonnaise: Rs. 35
   - **Total**: **Rs. 955.00**

3. **Order 3: Veggie Pizza with Swiss Cheese & Fried Onions**
   - Base Veggie Pizza: Rs. 650
   - Swiss Cheese: Rs. 80
   - Fried Onions: Rs. 20
   - **Total**: **Rs. 750.00**

- **Customer Grand Total**: **Rs. 2,555.00**

---

## 🚀 How to Run

### Option 1: Interactive Swing GUI
Run `PizzaBlastUI` or `Launcher`:
```powershell
javac -d target/classes src/main/java/com/lakruwan/decorator/*.java src/main/java/com/lakruwan/decorator/component/*.java src/main/java/com/lakruwan/decorator/decorator/*.java src/main/java/com/lakruwan/decorator/ui/*.java
java -cp target/classes com.lakruwan.decorator.ui.PizzaBlastUI
```

### Option 2: Console Practical Demonstration
```powershell
java -cp target/classes com.lakruwan.decorator.DecoratorApp
```

### Option 3: Standalone Classroom Demo (`lk.jiat.jms.Main`)
```powershell
javac -d target/classes src/main/java/lk/jiat/jms/Main.java
java -cp target/classes lk.jiat.jms.Main
```

