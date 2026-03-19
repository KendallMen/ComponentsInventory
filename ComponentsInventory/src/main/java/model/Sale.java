package model;

import java.time.LocalDate;
import java.util.List;

public class Sale {

    private String id;
    private String costumerId; // referencia
    private List<ItemSale> items;
    private double total;
    private LocalDate date;

    public Sale() {}

    public Sale(String id, String costumerId, List<ItemSale> items, double total, LocalDate date) {
        this.id = id;
        this.costumerId = costumerId;
        this.items = items;
        this.total = total;
        this.date = date;
    }

    // getters y setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCostumerId() {
        return costumerId;
    }

    public void setCostumerId(String costumerId) {
        this.costumerId = costumerId;
    }

    public List<ItemSale> getItems() {
        return items;
    }

    public void setItems(List<ItemSale> items) {
        this.items = items;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        String d = date != null ? date.toString() : "N/A";
        String t = String.format("%.2f", total);
        return d + " - $" + t;
    }
}
