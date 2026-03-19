package controller;

import model.Product;
import model.ProductType;
import service.ProductService;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;
import java.util.UUID;

public class ProductController {

    @FXML private TextField txtName;
    @FXML private ComboBox<ProductType> cbType;
    @FXML private TextField txtBrand;
    @FXML private TextField txtPrice;
    @FXML private TextField txtStock;

    @FXML private TableView<Product> tableProducts;
    @FXML private TableColumn<Product, String> colName;
    @FXML private TableColumn<Product, String> colType;
    @FXML private TableColumn<Product, String> colBrand;
    @FXML private TableColumn<Product, Double> colPrice;
    @FXML private TableColumn<Product, Integer> colStock;
    private Product SelectedProduct;

    private ProductService service = new ProductService();

    @FXML
    public void initialize() {

        // Usar CONSTRAINED_RESIZE_POLICY para distribuir el espacio equitativamente
        tableProducts.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        cbType.getItems().setAll(ProductType.values());

        colName.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getName()));

        colType.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getType().name())); // enum

        colBrand.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getBrand()));

        colPrice.setCellValueFactory(data ->
                new SimpleDoubleProperty(data.getValue().getPrice()).asObject());

        colStock.setCellValueFactory(data ->
                new SimpleIntegerProperty(data.getValue().getStock()).asObject());

        tableProducts.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {

            if (newVal != null) {
                SelectedProduct = newVal;

                txtName.setText(newVal.getName());
                cbType.setValue(newVal.getType());
                txtBrand.setText(newVal.getBrand());
                txtPrice.setText(String.valueOf(newVal.getPrice()));
                txtStock.setText(String.valueOf(newVal.getStock()));
            }
        });

        chargeTable();
    }

    @FXML
    public void handleAdd() {
        try {

            if (txtName.getText() == null || txtName.getText().isEmpty()) {
                throw new IllegalArgumentException("Product name cannot be empty");
            }

            if (cbType.getValue() == null) {
                throw new IllegalArgumentException("Select product type");
            }

            if (txtBrand.getText() == null || txtBrand.getText().isEmpty()) {
                throw new IllegalArgumentException("Product brand cannot be empty");
            }

            if (txtPrice.getText() == null || txtPrice.getText().isEmpty()) {
                throw new IllegalArgumentException("Product price cannot be empty");
            }

            if (txtStock.getText() == null || txtStock.getText().isEmpty()) {
                throw new IllegalArgumentException("Product stock cannot be empty");
            }

            Product p = new Product(
                    UUID.randomUUID().toString(),
                    txtName.getText(),
                    cbType.getValue(),
                    txtBrand.getText(),
                    Double.parseDouble(txtPrice.getText()),
                    Integer.parseInt(txtStock.getText()),
                    "new"
            );

            service.addProduct(p);

            chargeTable();
            cleanFields();

        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    private void chargeTable() {
        tableProducts.getItems().setAll(service.getAllProducts());
    }

    private void cleanFields() {
        txtName.clear();
        cbType.setValue(null);
        txtBrand.clear();
        txtPrice.clear();
        txtStock.clear();
    }

    @FXML
    public void handleDelete() {

        Product seleccionado = tableProducts.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            showError("Select product");
            return;
        }

        service.deleteProduct(seleccionado.getId());

        chargeTable();
    }

    @FXML
    public void handleUpdate() {

        if (SelectedProduct == null) {
            showError("Select product");
            return;
        }

        try {

            if (txtName.getText() == null || txtName.getText().isBlank()) {
                throw new IllegalArgumentException("Product name cannot be empty");
            }

            if (cbType.getValue() == null) {
                throw new IllegalArgumentException("Select product type");
            }

            if (txtBrand.getText() == null || txtBrand.getText().isBlank()) {
                throw new IllegalArgumentException("Product brand cannot be empty");
            }

            if (txtPrice.getText() == null || txtPrice.getText().isBlank()) {
                throw new IllegalArgumentException("Product price cannot be empty");
            }

            if (txtStock.getText() == null || txtStock.getText().isBlank()) {
                throw new IllegalArgumentException("Product stock cannot be empty");
            }
            Product update = new Product(
                    SelectedProduct.getId(),
                    txtName.getText(),
                    cbType.getValue(),
                    txtBrand.getText(),
                    Double.parseDouble(txtPrice.getText()),
                    Integer.parseInt(txtStock.getText()),
                    "new"
            );

            service.updateProduct(update);

            chargeTable();
            cleanFields();

            SelectedProduct = null;

        } catch (NumberFormatException e) {
            showError("Price must be a decimal number and stock must be an integer");
        } catch (IllegalArgumentException e) {
            showError(e.getMessage());
        } catch (Exception e) {
            showError("Unexpected error updating product");
        }
    }

    private void showError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}