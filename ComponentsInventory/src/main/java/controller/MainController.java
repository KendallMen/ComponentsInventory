package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class MainController {

    @FXML
    private VBox contentArea;

    @FXML
    public void initialize() {
        showProducts(); // pantalla inicial es products
    }

    @FXML
    public void showDashboard() {
        loadView("/view/dashboard.fxml");
    }

    @FXML
    public void showProducts() {
        loadView("/view/product.fxml");
    }

    @FXML
    public void showCustomers() {
        loadView("/view/customer.fxml");
    }

    @FXML
    public void showSales() {
        loadView("/view/sale.fxml");
    }

    private void loadView(String path) {
        try {
            Node view = FXMLLoader.load(getClass().getResource(path));
            contentArea.getChildren().setAll(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
