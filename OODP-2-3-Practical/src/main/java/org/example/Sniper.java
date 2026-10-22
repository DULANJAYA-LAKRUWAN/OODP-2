package org.example;

// Concrete Implementor 1
public class Sniper implements Weapon {
    @Override
    public void shoot() {
        System.out.println("Shoot");
    }

    @Override
    public String getName() {
        return "Sniper";
    }
}
