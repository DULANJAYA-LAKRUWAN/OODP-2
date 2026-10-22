package org.example;

// Concrete Implementor 2
public class Shotgun implements Weapon {
    @Override
    public void shoot() {
        System.out.println("Shoot");
    }

    @Override
    public String getName() {
        return "Shotgun";
    }
}
