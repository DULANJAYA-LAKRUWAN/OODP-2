package com.lakruwan.mediator;

/**
 * Console demonstration corresponding directly to Page 11 of the course curriculum.
 */
public class AirportMediator {
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("  Airport Traffic Control - Mediator Pattern Demo ");
        System.out.println("==================================================");

        ATCTower tower = new Controller();

        Plane plan1 = new PrivateJet(tower, "PrivateJet-01");
        Plane plan2 = new PrivateJet(tower, "PrivateJet-02");
        Plane plan3 = new PrivateJet(tower, "PrivateJet-03");

        System.out.println("\n--- Step 1: Plane 1 landing request ---");
        plan1.landing();

        System.out.println("\n--- Step 2: Plane 2 takeoff request ---");
        plan2.takeOff();

        System.out.println("\n--- Step 3: Plane 3 landing request (Checking queue handling) ---");
        plan3.landing();

        System.out.println("\n--- Step 4: Runway clearance by Controller ---");
        if (tower instanceof Controller) {
            ((Controller) tower).clearRunway("Runway 01L");
        }

        System.out.println("==================================================");
    }
}
