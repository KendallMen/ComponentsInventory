package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Customer;
import model.ItemSale;
import model.Product;
import model.Sale;
import service.*;

import java.time.LocalDate;
import java.util.*;

public class SaleController {

    @FXML private ComboBox<Customer> cbCustomer;
    @FXML private ComboBox<Product> cbProduct;
    @FXML private TextField txtQuantity;

    @FXML private TableView<ItemSale> tableItems;
    @FXML private TableColumn<ItemSale, String> colProduct;
    @FXML private TableColumn<ItemSale, Integer> colQuantity;
    @FXML private TableColumn<ItemSale, Double> colPrice;

    private ProductService productService = new ProductService();
    private CustomerService customerService = new CustomerService();
    private SaleService saleService = new SaleService();

    private List<ItemSale> items = new ArrayList<>();

    @FXML
    public void initialize() {

        // Usar CONSTRAINED_RESIZE_POLICY para distribuir el espacio equitativamente
        tableItems.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        cbCustomer.getItems().setAll(customerService.getAllCustomers());
        cbProduct.getItems().setAll(productService.getAllProducts());

        cbCustomer.setCellFactory(cb -> new ListCell<>() {
            @Override
            protected void updateItem(Customer item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item.getName());
            }
        });

        cbProduct.setCellFactory(cb -> new ListCell<>() {
            @Override
            protected void updateItem(Product item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item.getName());
            }
        });

        colProduct.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getProductId()));

        colQuantity.setCellValueFactory(data ->
                new javafx.beans.property.SimpleIntegerProperty(data.getValue().getAmount()).asObject());

        colPrice.setCellValueFactory(data ->
                new javafx.beans.property.SimpleDoubleProperty(data.getValue().getUnitPrice()).asObject());
    }

    @FXML
    public void handleAddItem() {

        try {
            Product product = cbProduct.getValue();
            int quantity = Integer.parseInt(txtQuantity.getText());

            if (product == null) throw new IllegalArgumentException("Select a product");
            if (quantity <= 0) throw new IllegalArgumentException("Invalid quantity");

            ItemSale item = new ItemSale(
                    product.getId(),
                    quantity,
                    product.getPrice()
            );

            items.add(item);
            tableItems.getItems().setAll(items);


            txtQuantity.clear();

        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    @FXML
    public void handleConfirmSale() {

        try {
            Customer customer = cbCustomer.getValue();

            if (customer == null) throw new IllegalArgumentException("Select a customer");
            if (items.isEmpty()) throw new IllegalArgumentException("No items in sale");

            Sale sale = new Sale(
                    UUID.randomUUID().toString(),
                    customer.getId(),
                    items,
                    0,
                    LocalDate.now()
            );

            saleService.performSale(sale);

            items.clear();
            tableItems.getItems().clear();

            showInfo("Sale completed successfully");

        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void showInfo(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
