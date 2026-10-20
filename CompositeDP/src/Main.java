public class Main {
    public static void main(String[] args) {

        // ── Leaf nodes: Developers ──────────────────────────────────────────
        Developer ryan  = new Developer("Ryan",  80000.00);
        Developer jason = new Developer("Jason", 60000.00);
        Developer kevin = new Developer("Kevin", 60000.00);
        Developer alex  = new Developer("Alex",  70000.00);

        // ── Composite nodes: Project Leads ──────────────────────────────────
        ProjectLead henry   = new ProjectLead("Henry",   100000.00);
        ProjectLead matthew = new ProjectLead("Matthew",  90000.00);
        ProjectLead levi    = new ProjectLead("Levi",    100000.00);

        henry.add(ryan);        // Henry's team
        matthew.add(jason);     // Matthew's team
        levi.add(kevin);        // Levi's team
        levi.add(alex);

        // ── Composite root: Manager ─────────────────────────────────────────
        Manager kevinManager = new Manager("Kevin", 180000.00);
        kevinManager.add(henry);
        kevinManager.add(matthew);
        kevinManager.add(levi);

        // ── Display company hierarchy ───────────────────────────────────────
        System.out.println("========================================");
        System.out.println("       PIX Soft (Pvt) Ltd - Hierarchy  ");
        System.out.println("========================================");
        kevinManager.showDetails("");
        System.out.println();

        // ── Requirement 1: Total salary when requesting Manager's salary ────
        System.out.println("========================================");
        System.out.println("1) Total salary (via Manager Kevin):    ");
        System.out.println("========================================");
        System.out.printf("   Total Salary Paid = Rs. %.2f%n", kevinManager.getTotalSalary());
        System.out.println();

        // ── Requirement 2: Total salary of Project Lead Levi and his team ───
        System.out.println("========================================");
        System.out.println("2) Total salary of Levi and his team:  ");
        System.out.println("========================================");
        System.out.printf("   Total Salary Paid = Rs. %.2f%n", levi.getTotalSalary());
        System.out.println("========================================");
    }
}