package lk.jiat.model;

// Concrete Implementor 1
public class WindowsOS implements OperatingSystem {
    @Override
    public void execute(String appName) {
        System.out.println("Running " + appName + " on Windows OS");
    }
}
