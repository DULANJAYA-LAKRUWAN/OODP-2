package org.example;

// Refined Abstraction 1
public class Ranger extends Soldier {
    public Ranger() {
        super();
    }

    public Ranger(Weapon weapon) {
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
