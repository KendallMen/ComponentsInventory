package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Customer;
import service.CustomerService;

import java.util.List;
import java.util.UUID;

public class CustomerController {

    @FXML private TextField txtName;
    @FXML private TextField txtPhone;

    @FXML private TableView<Customer> tableCustomers;
    @FXML private TableColumn<Customer, String> colName;
    @FXML private TableColumn<Customer, String> colPhone;

    private CustomerService service = new CustomerService();
    private Customer selectedCustomer;

    @FXML
    public void initialize() {

        // Configurar política de redimensionamiento de columnas
        tableCustomers.setColumnResizePolicy(javafx.scene.control.TableView.CONSTRAINED_RESIZE_POLICY);

        colName.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getName()));

        colPhone.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getPhoneNumber()));

        tableCustomers.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                selectedCustomer = newVal;
                txtName.setText(newVal.getName());
                txtPhone.setText(newVal.getPhoneNumber());
            }
        });

        loadTable();
    }

    @FXML
    public void handleAdd() {
        try {
            Customer c = new Customer(
                    UUID.randomUUID().toString(),
                    txtName.getText(),
                    txtPhone.getText()
            );

            service.addCustomer(c);
            loadTable();
            clear();

        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    @FXML
    public void handleUpdate() {
        try {
            if (selectedCustomer == null) {
                throw new IllegalArgumentException("Select a customer");
            }

            Customer updated = new Customer(
                    selectedCustomer.getId(),
                    txtName.getText(),
                    txtPhone.getText()
            );

            service.updateCustomer(updated);
            loadTable();
            clear();
            selectedCustomer = null;

        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    @FXML
    public void handleDelete() {
        try {
            if (selectedCustomer == null) {
                throw new IllegalArgumentException("Select a customer");
            }

            service.deleteCustomer(selectedCustomer.getId());
            loadTable();
            clear();
            selectedCustomer = null;

        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    private void loadTable() {
        List<Customer> customers = service.getAllCustomers();
        tableCustomers.getItems().clear();
        tableCustomers.getItems().addAll(customers);
    }


    private void clear() {
        txtName.clear();
        txtPhone.clear();
    }

    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}