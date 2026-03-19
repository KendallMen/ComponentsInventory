package repository;

import com.fasterxml.jackson.core.type.TypeReference;
import model.Product;
import util.JsonUtil;

import java.util.List;

public class ProductRepository {

    private final String FILE_PATH = "data/products.json";

    public List<Product> getAllProducts(){
        return JsonUtil.readList(
                FILE_PATH,
                new TypeReference<List<Product>>() {}
        );
    }

    public void saveProducts(List<Product> products){
        JsonUtil.writeJSON(FILE_PATH, products);
    }

    public void eliminateProduct(String id) {
        List<Product> products = getAllProducts();
        products.removeIf(product -> product.getId().equals(id));
        saveProducts(products);
    }

    public void updateProduct(Product updatedProduct) {
        List<Product> products = getAllProducts();
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId().equals(updatedProduct.getId())) {
                products.set(i, updatedProduct);
                break;
            }
        }
        saveProducts(products);
    }

    public Product searchById(String id){
        return getAllProducts()
                .stream()
                .filter(product -> product.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
