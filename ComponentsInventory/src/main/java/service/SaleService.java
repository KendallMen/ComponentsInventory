package service;

import model.ItemSale;
import model.Product;
import model.Sale;
import repository.ProductRepository;
import repository.SaleRepository;

public class SaleService {

    private SaleRepository saleRepo = new SaleRepository();
    private ProductService productService = new ProductService();
    private ProductRepository productRepo = new ProductRepository();

    public void performSale(Sale sale) {

        if (sale.getItems() == null || sale.getItems().isEmpty()) {
            throw new IllegalArgumentException("Venta sin productos");
        }

        double total = 0;

        for (ItemSale item : sale.getItems()) {

            Product producto = productRepo.searchById(item.getProductId());

            if (producto == null) {
                throw new IllegalArgumentException("Producto no existe");
            }

            if (producto.getStock() < item.getAmount()) {
                throw new IllegalArgumentException("Stock insuficiente");
            }

            producto.setStock(producto.getStock() - item.getAmount());
            productRepo.updateProduct(producto);

            total += item.getAmount() * item.getUnitPrice();
        }

        sale.setTotal(total);

        saleRepo.saveSale(sale);
    }
}
