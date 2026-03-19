package service;

import model.Product;
import repository.ProductRepository;

import java.util.List;

public class ProductService {

    private ProductRepository repo = new ProductRepository();

    public void addProduct(Product newProduct) {
        if (newProduct.getName() == null || newProduct.getName().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }

        if (newProduct.getPrice() <= 0) {
            throw new IllegalArgumentException("Product price must be greater than zero");
        }

        if (newProduct.getStock() < 0) {
            throw new IllegalArgumentException("Product stock cannot be negative");
        }

        List<Product> products = repo.getAllProducts();

        for (Product p : products){
            if (p.getName().equalsIgnoreCase(newProduct.getName()) &&
                p.getBrand().equalsIgnoreCase(newProduct.getBrand()) &&
                p.getType() == newProduct.getType()) {

                p.setStock(p.getStock() + newProduct.getStock());
                repo.saveAllProducts(products);
                return;

            }
        }

        repo.saveProducts(newProduct);
    }

    public List<Product> getAllProducts() {
        return repo.getAllProducts();
    }

    public void deleteProduct(String id) {

        if (repo.searchById(id) == null) {
            throw new IllegalArgumentException("Product with id " + id + " does not exist");
        }

        repo.eliminateProduct(id);
    }

    public void updateProduct(Product updatedProduct) {
        if (repo.searchById(updatedProduct.getId()) == null) {
            throw new IllegalArgumentException("Product with id " + updatedProduct.getId() + " does not exist");
        }

        repo.updateProduct(updatedProduct);
    }

    public void reduceStock(String id, int quantity) {
        Product product = repo.searchById(id);

        if (product == null) {
            throw new IllegalArgumentException("Product with id " + id + " does not exist");
        }

        if (product.getStock() < quantity) {
            throw new IllegalArgumentException("Not enough stock for product with id " + id);
        }

        product.setStock(product.getStock() - quantity);
        repo.updateProduct(product);
    }

}
