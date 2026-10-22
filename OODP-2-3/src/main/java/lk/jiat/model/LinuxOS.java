package lk.jiat.model;

// Concrete Implementor 2
public class LinuxOS implements OperatingSystem {
    @Override
    public void execute(String appName) {
        System.out.println("Running " + appName + " on Linux OS");
    }
}
