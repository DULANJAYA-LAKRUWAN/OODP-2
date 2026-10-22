package org.example;

// Refined Abstraction 3
public class Infantry extends Soldier {
    public Infantry() {
        super();
    }

    public Infantry(Weapon weapon) {
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
