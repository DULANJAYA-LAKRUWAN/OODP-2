package org.example;

// Concrete Implementor 3
public class Pistol implements Weapon {
    @Override
    public void shoot() {
        System.out.println("Shoot");
    }

    @Override
    public String getName() {
        return "Pistol";
    }
}
