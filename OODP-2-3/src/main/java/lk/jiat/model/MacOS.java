package lk.jiat.model;

// Concrete Implementor 3
public class MacOS implements OperatingSystem {
    @Override
    public void execute(String appName) {
        System.out.println("Running " + appName + " on macOS");
    }
}
