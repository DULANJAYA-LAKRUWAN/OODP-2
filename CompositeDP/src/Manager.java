import java.util.ArrayList;
import java.util.Collection;

// Composite class - Manager (top-level, can have subordinate ProjectLeads)
public class Manager implements Employee {
    private String name;
    private double salary;
    private Collection<Employee> subordinates = new ArrayList<>(); // collection : Collection<Component>

    public Manager(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }

    // add(component : Component) : void
    public void add(Employee employee) {
        subordinates.add(employee);
    }

    // remove(component : Component) : void
    public void remove(Employee employee) {
        subordinates.remove(employee);
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
        // operation() - own salary + sum of all subordinates' total salaries
        double total = salary;
        for (Employee e : subordinates) {
            total += e.getTotalSalary();
        }
        return total;
    }

    @Override
    public void showDetails(String indent) {
        System.out.println(indent + "Manager: " + name + " | Salary: Rs. " + String.format("%.2f", salary));
        for (Employee e : subordinates) {
            e.showDetails(indent + "    ");
        }
    }
}
