# Practical 06: Flyweight Design Pattern - "NanoPaint"

## 1. Problem Overview
"NanoPaint" is a car painting shop. The goal is to demonstrate the car painting process by creating different car objects and painting them using different colors while utilizing the **Flyweight Design Pattern**.

Color is shared as an **intrinsic state** (Flyweight object) to drastically minimize memory consumption.

---

## 2. UML Diagram Mapping

| UML Element | Implementation Class | Role in "NanoPaint" |
| :--- | :--- | :--- |
| **`Context`** | `Context` interface | Defines `+operation()` for car actions. |
| **`ConcreteContext`** | `ConcreteContext` | Represents the Car. Stores **extrinsic state** (Model, License Plate) and holds a reference to `SharedContext`. |
| **`SharedContext`** | `SharedContext` | The **Flyweight**. Stores **intrinsic state** (Color: Blue, Black, Red, Silver) that is immutable and shared. |
| **`SharedContextPool`** | `SharedContextPool` | The **Flyweight Factory**. Maintains `sharedContextCollection` (cache/map) to reuse existing color instances. |

---

## 3. Intrinsic vs. Extrinsic State

* **Intrinsic State (`SharedContext`)**:
  - The Paint Color (e.g., `Blue`, `Black`, `Red`, `Silver`).
  - Independent of the car context, immutable, and shared across all cars of the same color.
* **Extrinsic State (`ConcreteContext`)**:
  - The Car specifics (e.g., Car Model, License Plate Number, Registration ID).
  - Unique to each individual car instance.

---

## 4. How to Run

### Option 1: Console Demonstration
```bash
cd "d:\JIAT\OODP 2\NanoPaint-Flyweight-Application"
java -cp target/classes com.nanopaint.NanoPaintApp
```

### Option 2: Graphical User Interface (Swing)
```bash
cd "d:\JIAT\OODP 2\NanoPaint-Flyweight-Application"
java -cp target/classes com.nanopaint.NanoPaintUI
```
