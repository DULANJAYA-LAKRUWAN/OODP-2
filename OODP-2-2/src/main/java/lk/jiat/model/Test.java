package lk.jiat.model;

import java.util.ArrayList;

// Component Interface
interface Employee {
    String getName();
    double getSalary();
    double getTotalSalary(); // operation()
}

// Leaf Class
class Developer implements Employee {

    private String name;
    private double salary;

    public Developer(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getSalary() {
        return salary;
    }

    @Override
    public double getTotalSalary() {
        return salary;
    }
}

// Composite Base Class
class TeamLead implements Employee {

    private String name;
    private double salary;
    private ArrayList<Employee> team = new ArrayList<>();

    public TeamLead(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }

    public void add(Employee employee) {
        team.add(employee);
    }

    public void remove(Employee employee) {
        team.remove(employee);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getSalary() {
        return salary;
    }

    @Override
    public double getTotalSalary() {
        double total = salary;
        for (Employee employee : team) {
            total += employee.getTotalSalary();
        }
        return total;
    }
}

// Composite: Project Lead
class ProjectLead extends TeamLead {
    public ProjectLead(String name, double salary) {
        super(name, salary);
    }
}

// Composite: Manager
class Manager extends TeamLead {
    public Manager(String name, double salary) {
        super(name, salary);
    }
}

public class Test {
    public static void main(String[] args) {
        // Manager (Root)
        Manager manager = new Manager("John", 150000);

        // Project Leads (Intermediate Composites)
        ProjectLead henry = new ProjectLead("Henry", 100000);
        henry.add(new Developer("Ryan", 80000));

        ProjectLead matthew = new ProjectLead("Matthew", 90000);
        matthew.add(new Developer("Jason", 60000));

        ProjectLead levi = new ProjectLead("Levi", 100000);
        levi.add(new Developer("Kevin", 60000));
        levi.add(new Developer("Alex", 70000));

        // Add Project Leads to Manager
        manager.add(henry);
        manager.add(matthew);
        manager.add(levi);

        // 1. Calculate the total salary paid to all the employees when requesting the manager's salary
        System.out.printf("Total salary for Manager %s and all teams: Rs. %.2f%n",
                manager.getName(), manager.getTotalSalary());

        // 2. Calculate the total salary paid to the project lead ("Levi") and his team
        System.out.printf("Total salary for Project Lead %s and his team: Rs. %.2f%n",
                levi.getName(), levi.getTotalSalary());
    }
}
