// Component Interface
public interface Employee {
    String getName();
    double getSalary();
    double getTotalSalary(); // operation() - calculates total salary of self + subordinates
    void showDetails(String indent);
}
