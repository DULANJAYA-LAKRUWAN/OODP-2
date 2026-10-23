package lk.jiat.model;

public class Test {

    public static void main(String[] args) {
        // Creating first Smartphone object using Builder pattern
        Smartphone phone1 = new Smartphone.Builder()
                .setBrand("Samsung")
                .setModel("Galaxy S24 Ultra")
                .setRam(12)
                .setStorage(512)
                .setDisplaySize(6.8)
                .setCamera(200)
                .setNfc(true)
                .setPrice(1299.99)
                .build();

        // Creating second Smartphone object using Builder pattern
        Smartphone phone2 = new Smartphone.Builder()
                .setBrand("Apple")
                .setModel("iPhone 15 Pro")
                .setRam(8)
                .setStorage(256)
                .setDisplaySize(6.1)
                .setCamera(48)
                .setNfc(true)
                .setPrice(999.00)
                .build();

        // Printing both Smartphone objects using their toString() method
        System.out.println("=== Smartphone 1 ===");
        System.out.println(phone1);

        System.out.println("=== Smartphone 2 ===");
        System.out.println(phone2);
    }
}