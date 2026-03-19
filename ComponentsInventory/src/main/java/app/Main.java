package app;

import model.*;
import service.CostumerService;
import service.ProductService;
import service.SaleService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        ProductService productoService = new ProductService();
        CostumerService clienteService = new CostumerService();
        SaleService ventaService = new SaleService();

        // 🔹 1. Crear producto
        Product p1 = new Product("p1", "SSD Kingston", ProductType.SSD, "Kingston", 35000, 10, "nuevo");
        productoService.addProduct(p1);

        Product p2 = new Product("p2", "RTX 3060", ProductType.GPU, "Nvidia", 250000, 5, "nuevo");
        productoService.addProduct(p2);

        System.out.println("Productos:");
        productoService.getAllProducts().forEach(System.out::println);

        // 🔹 2. Crear cliente
        Costumer c1 = new Costumer("c1", "Juan", "88888888");
        clienteService.addCostumer(c1);

        System.out.println("\nClientes:");
        clienteService.getAllCostumers().forEach(System.out::println);

        // 🔹 3. Crear venta
        List<ItemSale> items = new ArrayList<>();
        items.add(new ItemSale("p1", 2, 0)); // precio se asigna en service
        items.add(new ItemSale("p2", 1, 0));

        Sale venta = new Sale(
                "v1",
                "c1",
                items,
                0,
                LocalDate.now()
        );

        ventaService.performSale(venta);

        System.out.println("\nVenta realizada:");

        // 🔹 4. Verificar productos después de venta
        System.out.println("\nProductos después de venta:");
        productoService.getAllProducts().forEach(System.out::println);
    }
}