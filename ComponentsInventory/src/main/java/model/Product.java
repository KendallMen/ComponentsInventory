package model;

public class Product {

    private String id;
    private String name;
    private ProductType type;
    private String brand;
    private double price;
    private int stock;
    private String state;

    public Product() {}

    public Product(String id, String name, ProductType type, String brand, double price, int stock, String state) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.brand = brand;
        this.price = price;
        this.stock = stock;
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProductType getType() {
        return type;
    }

    public void setType(ProductType type) {
        this.type = type;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        String n = name != null ? name.trim() : "";
        String b = brand != null ? brand.trim() : "";
        String p = String.format("%.2f", price);

        if (!n.isEmpty() && !b.isEmpty()) {
            return n + " - " + b + " ($" + p + ")";
        }
        return !n.isEmpty() ? n : (id != null ? id : "");
    }

}