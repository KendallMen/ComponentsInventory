package app;

import model.Product;
import model.ProductType;
import repository.ProductRepository;

import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        ProductRepository repo = new ProductRepository();

        Product p = new Product(
                "p1",
                "SSD Kingston",
                ProductType.SSD,
                "Kingston",
                35000,
                10,
                "nuevo"
        );

        repo.saveProducts(p);

        System.out.println(repo.getAllProducts());
    }

}
