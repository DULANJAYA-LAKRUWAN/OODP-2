package org.example;

// Refined Abstraction 2
public class Commando extends Soldier {
    public Commando() {
        super();
    }

    public Commando(Weapon weapon) {
        super(weapon);
    }

    @Override
    public void run() {
        System.out.println("Running");
    }

    @Override
    public void shoot() {
        if (weapon != null) {
            weapon.shoot();
        } else {
            System.out.println("No weapon equipped to shoot!");
        }
    }
}
