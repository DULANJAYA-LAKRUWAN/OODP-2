package lk.jiat.jms.car;

import lk.jiat.jms.Prototype;

public class Car implements Prototype {
    private String brand;
    private String model;
    private String color;
    private int year;
    private double price;
    private String fuelType;
    private String bodyType;
    private String engineType;

    // Default / Parameterized Constructor
    public Car(String brand, String model, String color, int year, double price, String fuelType, String bodyType, String engineType) {
        this.brand = brand;
        this.model = model;
        this.color = color;
        this.year = year;
        this.price = price;
        this.fuelType = fuelType;
        this.bodyType = bodyType;
        this.engineType = engineType;
    }

    // Copy Constructor for Prototype cloning
    public Car(Car other) {
        this.brand = other.brand;
        this.model = other.model;
        this.color = other.color;
        this.year = other.year;
        this.price = other.price;
        this.fuelType = other.fuelType;
        this.bodyType = other.bodyType;
        this.engineType = other.engineType;
    }

    @Override
    public Car clone() {
        return new Car(this);
    }

    // Getters and Setters for Customization
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getBodyType() {
        return bodyType;
    }

    public void setBodyType(String bodyType) {
        this.bodyType = bodyType;
    }

    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    public void displayCarInfo() {
        System.out.println("+------------------------------------------------------+");
        System.out.printf("| %-20s : %-30s |\n", "Brand", brand);
        System.out.printf("| %-20s : %-30s |\n", "Model", model);
        System.out.printf("| %-20s : %-30s |\n", "Color", color);
        System.out.printf("| %-20s : %-30d |\n", "Year", year);
        System.out.printf("| %-20s : $%-29.2f |\n", "Price", price);
        System.out.printf("| %-20s : %-30s |\n", "Fuel Type", fuelType);
        System.out.printf("| %-20s : %-30s |\n", "Body Type", bodyType);
        System.out.printf("| %-20s : %-30s |\n", "Engine Type", engineType);
        System.out.printf("| %-20s : %-30s |\n", "Instance ID (Hash)", Integer.toHexString(System.identityHashCode(this)));
        System.out.println("+------------------------------------------------------+");
    }
}
