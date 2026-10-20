// Leaf class - Developer (has no subordinates)
public class Developer implements Employee {
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
        return salary; // Leaf has no subordinates, returns own salary only
    }

    @Override
    public void showDetails(String indent) {
        System.out.println(indent + "Developer: " + name + " | Salary: Rs. " + String.format("%.2f", salary));
    }
}
