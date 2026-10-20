package lk.jiat.model;

import java.util.ArrayList;
import java.util.List;

interface Employee {
    double getTotalSalary();
}

class Developer implements Employee {
    private final String name;
    private final double salary;

    public Developer(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }

    @Override
    public double getTotalSalary() {
        return salary;
    }
}

abstract class TeamLead implements Employee {
    private final String name;
    private final double salary;
    private final List<Employee> team = new ArrayList<>();

    protected TeamLead(String name, double salary) {
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
    public double getTotalSalary() {
        double total = salary;
        for (Employee employee : team) {
            total += employee.getTotalSalary();
        }
        return total;
    }

    protected String getName() {
        return name;
    }
}

class ProjectLead extends TeamLead {
    public ProjectLead(String name, double salary) {
        super(name, salary);
    }
}

class Manager extends TeamLead {
    public Manager(String name, double salary) {
        super(name, salary);
    }
}

public class Test {
    public static void main(String[] args) {
        Manager manager = new Manager("Kevin", 180000);

        ProjectLead henry = new ProjectLead("Henry", 100000);
        henry.add(new Developer("Ryan", 80000));

        ProjectLead matthew = new ProjectLead("Matthew", 90000);
        matthew.add(new Developer("Jason", 60000));

        ProjectLead levi = new ProjectLead("Levi", 100000);
        levi.add(new Developer("Kevin", 60000));
        levi.add(new Developer("Alex", 70000));

        manager.add(henry);
        manager.add(matthew);
        manager.add(levi);

        System.out.printf("Total salary for Manager %s and all teams: Rs. %.2f%n",
                manager.getName(), manager.getTotalSalary());
        System.out.printf("Total salary for Project Lead %s and his team: Rs. %.2f%n",
                levi.getName(), levi.getTotalSalary());
    }
}
