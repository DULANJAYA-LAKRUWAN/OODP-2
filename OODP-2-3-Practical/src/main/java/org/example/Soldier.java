package org.example;

// Abstraction in Bridge Design Pattern
public abstract class Soldier {
    // Bridge to Implementor interface
    protected Weapon weapon;

    public Soldier() {
    }

    public Soldier(Weapon weapon) {
        this.weapon = weapon;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    // A soldier can hold only one weapon at a time
    public void takeWeapon(Weapon weapon) {
        if (this.weapon != null) {
            System.out.println("Cannot take " + weapon.getName() + "! A soldier can hold only one weapon at a time. Please drop the current weapon first.");
            return;
        }
        this.weapon = weapon;
        if ("Pistol".equalsIgnoreCase(weapon.getName()) || (this instanceof Commando && "Sniper".equalsIgnoreCase(weapon.getName()))) {
            System.out.println("Taking " + weapon.getName());
        } else {
            System.out.println("Taking the " + weapon.getName());
        }
    }

    public void dropWeapon() {
        if (this.weapon != null) {
            System.out.println("Drop the " + this.weapon.getName());
            this.weapon = null;
        } else {
            System.out.println("No weapon to drop!");
        }
    }

    // Operations to be implemented by Refined Abstractions
    public abstract void run();
    public abstract void shoot();
}
