package service;

import model.Customer;
import model.Product;
import model.Sale;
import repository.CustomerRepository;
import repository.ProductRepository;
import repository.SaleRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DashboardService {

    private CustomerRepository customerRepo = new CustomerRepository();
    private ProductRepository productRepo = new ProductRepository();
    private SaleRepository saleRepo = new SaleRepository();

    public int getTotalCustomers() {
        return customerRepo.getAllCustomers().size();
    }

    public int getTotalProducts() {
        return productRepo.getAllProducts().size();
    }

    public double getTotalSalesRevenue() {
        return saleRepo.getAllSales().stream()
                .mapToDouble(Sale::getTotal)
                .sum();
    }

    public int getTotalSalesCount() {
        return saleRepo.getAllSales().size();
    }

    public double getAverageSaleValue() {
        List<Sale> sales = saleRepo.getAllSales();
        if (sales.isEmpty()) return 0;
        return getTotalSalesRevenue() / sales.size();
    }

    public int getTotalStock() {
        return productRepo.getAllProducts().stream()
                .mapToInt(Product::getStock)
                .sum();
    }

    public String getMostSoldProduct() {
        return "Top Producto"; // Placeholder
    }

    public Map<String, Double> getSalesByProductType() {
        Map<String, Double> salesByType = new HashMap<>();

        try {
            List<Sale> sales = saleRepo.getAllSales();
            List<Product> products = productRepo.getAllProducts();

            Map<String, String> productTypeMap = new HashMap<>();
            for (Product p : products) {
                productTypeMap.put(p.getId(), p.getType().toString());
            }

            for (Sale sale : sales) {
                for (var itemSale : sale.getItems()) {
                    String productId = itemSale.getProductId();
                    String type = productTypeMap.getOrDefault(productId, "Desconocido");
                    double saleAmount = itemSale.getAmount() * itemSale.getUnitPrice();
                    salesByType.put(type, salesByType.getOrDefault(type, 0.0) + saleAmount);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return salesByType;
    }

    public Map<String, Integer> getTopStockProducts() {
        Map<String, Integer> stockByProduct = new HashMap<>();

        productRepo.getAllProducts().stream()
                .limit(5)
                .forEach(p -> stockByProduct.put(p.getName(), p.getStock()));

        return stockByProduct;
    }

    public Map<String, Double> getSalesTrend() {
        Map<String, Double> trend = new HashMap<>();
        trend.put("Enero", 500.0);
        trend.put("Febrero", 750.0);
        trend.put("Marzo", 1200.0);
        trend.put("Abril", 950.0);
        trend.put("Mayo", 1400.0);
        trend.put("Junio", 1100.0);
        return trend;
    }
}

