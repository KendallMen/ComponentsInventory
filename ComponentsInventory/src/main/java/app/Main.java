package app;

import model.Product;
import model.ProductType;
import repository.ProductRepository;
import service.ProductService;

import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        ProductService service = new ProductService();

        Product p = new Product(
                "p1",
                "SSD Kingston",
                ProductType.SSD,
                "Kingston",
                35000,
                5,
                "nuevo"
        );

        service.addProduct(p);
        System.out.println("Guardado");

        System.out.println(service.getAllProducts());
    }
}