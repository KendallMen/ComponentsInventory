package controller;

import model.Product;
import model.ProductType;
import service.ProductService;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.UUID;

public class ProductController {

    @FXML private TextField txtNombre;
    @FXML private ComboBox<ProductType> cbTipo;
    @FXML private TextField txtMarca;
    @FXML private TextField txtPrecio;
    @FXML private TextField txtStock;

    @FXML private TableView<Product> tablaProductos;
    @FXML private TableColumn<Product, String> colNombre;
    @FXML private TableColumn<Product, String> colTipo;
    @FXML private TableColumn<Product, String> colMarca;
    @FXML private TableColumn<Product, Double> colPrecio;
    @FXML private TableColumn<Product, Integer> colStock;
    private Product productoSeleccionado;

    private ProductService service = new ProductService();

    @FXML
    public void initialize() {

        cbTipo.getItems().setAll(ProductType.values());

        colNombre.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getName()));

        colTipo.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getType().name())); // enum

        colMarca.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getBrand()));

        colPrecio.setCellValueFactory(data ->
                new SimpleDoubleProperty(data.getValue().getPrice()).asObject());

        colStock.setCellValueFactory(data ->
                new SimpleIntegerProperty(data.getValue().getStock()).asObject());

        tablaProductos.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {

            if (newVal != null) {
                productoSeleccionado = newVal;

                txtNombre.setText(newVal.getName());
                cbTipo.setValue(newVal.getType());
                txtMarca.setText(newVal.getBrand());
                txtPrecio.setText(String.valueOf(newVal.getPrice()));
                txtStock.setText(String.valueOf(newVal.getStock()));
            }
        });

        cargarTabla();
    }

    @FXML
    public void handleAgregar() {
        try {

            // 🔴 Validación básica UI
            if (cbTipo.getValue() == null) {
                throw new IllegalArgumentException("Seleccione un tipo");
            }

            Product p = new Product(
                    UUID.randomUUID().toString(),
                    txtNombre.getText(),
                    cbTipo.getValue(), // 🔥 ENUM
                    txtMarca.getText(),
                    Double.parseDouble(txtPrecio.getText()),
                    Integer.parseInt(txtStock.getText()),
                    "nuevo"
            );

            service.addProduct(p);

            cargarTabla();
            limpiarCampos();

        } catch (Exception e) {
            mostrarError(e.getMessage());
        }
    }

    private void cargarTabla() {
        tablaProductos.getItems().setAll(service.getAllProducts());
    }

    private void limpiarCampos() {
        txtNombre.clear();
        cbTipo.setValue(null);
        txtMarca.clear();
        txtPrecio.clear();
        txtStock.clear();
    }

    @FXML
    public void handleEliminar() {

        Product seleccionado = tablaProductos.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarError("Seleccione un producto");
            return;
        }

        service.deleteProduct(seleccionado.getId());

        cargarTabla();
    }

    @FXML
    public void handleActualizar() {

        if (productoSeleccionado == null) {
            mostrarError("Seleccione un producto");
            return;
        }

        try {
            Product actualizado = new Product(
                    productoSeleccionado.getId(), // 🔥 MISMO ID
                    txtNombre.getText(),
                    cbTipo.getValue(),
                    txtMarca.getText(),
                    Double.parseDouble(txtPrecio.getText()),
                    Integer.parseInt(txtStock.getText()),
                    "nuevo"
            );

            service.updateProduct(actualizado);

            cargarTabla();
            limpiarCampos();
            productoSeleccionado = null;

        } catch (Exception e) {
            mostrarError(e.getMessage());
        }
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}