package lk.jiat.jms;

public class Main {
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("       ZOMBIE GAME - PROTOTYPE PATTERN DEMO       ");
        System.out.println("==================================================");

        // 1. Spawning basic zombies by cloning from the registry
        System.out.println("\n[1] Spawning standard zombies from Registry (Clones):");
        Zombie zombie1 = ZombieRegistry.getZombie("WALKER");
        Zombie zombie2 = ZombieRegistry.getZombie("WALKER");
        Zombie runner1 = ZombieRegistry.getZombie("RUNNER");

        zombie1.display();
        zombie2.display();
        runner1.display();

        System.out.println("Notice different HashCodes (IDs) above -> each is an independent clone!\n");

        // 2. Customizing a cloned zombie without modifying the original prototype
        System.out.println("[2] Customizing a clone into an 'Elite Runner Zombie':");
        Zombie eliteRunner = ZombieRegistry.getZombie("RUNNER");
        eliteRunner.setType("Elite Armored Runner Zombie");
        eliteRunner.setAttackPower(40); // boosted from 25
        eliteRunner.setHealth(120);     // boosted from 60
        eliteRunner.display();

        System.out.println("[3] Verifying original prototype remains untouched in Registry:");
        Zombie freshRunner = ZombieRegistry.getZombie("RUNNER");
        freshRunner.display();

        // 3. Spawning and customizing a Boss Zombie from a Tank prototype
        System.out.println("[4] Spawning Tank Zombie and customizing into 'Gargantuar Boss':");
        Zombie bossZombie = ZombieRegistry.getZombie("TANK");
        bossZombie.setType("Gargantuar Mega Boss Zombie");
        bossZombie.setAttackPower(90);
        bossZombie.setHealth(850);
        bossZombie.display();
    }
}