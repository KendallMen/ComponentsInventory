package model;

public class ItemSale {

    private String productoId;
    private int cantidad;
    private double precioUnitario;

    public ItemSale() {}

    public ItemSale(String productoId, int cantidad, double precioUnitario) {
        this.productoId = productoId;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }

    // getters y setters
}
