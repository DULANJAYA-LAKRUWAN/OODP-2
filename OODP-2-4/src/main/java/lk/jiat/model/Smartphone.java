package lk.jiat.model;

public class Smartphone {
    private String brand;
    private String model;
    private int ram;
    private double price;
    private int camera;
    private boolean nfc;
    private double displaySize;
    private int storage;

    private Smartphone(Builder builder) {
        this.brand = builder.brand;
        this.model = builder.model;
        this.ram = builder.ram;
        this.price = builder.price;
        this.camera = builder.camera;
        this.nfc = builder.nfc;
        this.displaySize = builder.displaySize;
        this.storage = builder.storage;
    }

    @Override
    public String toString() {
        return "Smartphone Details:\n" +
                "  Brand        : " + brand + "\n" +
                "  Model        : " + model + "\n" +
                "  RAM          : " + ram + " GB\n" +
                "  Storage      : " + storage + " GB\n" +
                "  Display Size : " + displaySize + " inches\n" +
                "  Camera       : " + camera + " MP\n" +
                "  NFC          : " + (nfc ? "Supported" : "Not Supported") + "\n" +
                "  Price        : $" + price + "\n";
    }

    public static class Builder {
        private String brand;
        private String model;
        private int ram;
        private double price;
        private int camera;
        private boolean nfc;
        private double displaySize;
        private int storage;

        public Builder setBrand(String brand) {
            this.brand = brand;
            return this;
        }

        public Builder setModel(String model) {
            this.model = model;
            return this;
        }

        public Builder setRam(int ram) {
            this.ram = ram;
            return this;
        }

        public Builder setPrice(double price) {
            this.price = price;
            return this;
        }

        public Builder setCamera(int camera) {
            this.camera = camera;
            return this;
        }

        public Builder setNfc(boolean nfc) {
            this.nfc = nfc;
            return this;
        }

        public Builder setDisplaySize(double displaySize) {
            this.displaySize = displaySize;
            return this;
        }

        public Builder setStorage(int storage) {
            this.storage = storage;
            return this;
        }

        public Smartphone build() {
            return new Smartphone(this);
        }
    }
}
