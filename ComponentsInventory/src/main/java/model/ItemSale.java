package model;

public class ItemSale {

    private String productId;
    private int amount;
    private double unitPrice;

    public ItemSale() {}

    public ItemSale(String productId, int amount, double unitPrice) {
        this.productId = productId;
        this.amount = amount;
        this.unitPrice = unitPrice;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitePrice) {
        this.unitPrice = unitePrice;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
