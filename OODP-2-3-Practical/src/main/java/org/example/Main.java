package org.example;

public class Main {
    public static void main(String[] args) {
        // --- Ranger Sequence ---
        System.out.println("Ranger");
        Soldier ranger = new Ranger();
        ranger.run();
        ranger.takeWeapon(new Sniper());
        ranger.shoot();
        ranger.dropWeapon();
        ranger.takeWeapon(new Pistol());
        ranger.shoot();

        System.out.println();

        // --- Commando Sequence ---
        System.out.println("Commando");
        Soldier commando = new Commando();
        commando.run();
        commando.takeWeapon(new Shotgun());
        commando.shoot();
        commando.dropWeapon();
        commando.takeWeapon(new Sniper());
        commando.shoot();
    }
}