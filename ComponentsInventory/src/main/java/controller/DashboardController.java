package controller;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import service.DashboardService;

import java.util.Map;

public class DashboardController {

    @FXML private VBox dashboardContainer;

    private DashboardService service = new DashboardService();

    @FXML
    public void initialize() {
        loadDashboard();
    }

    private void loadDashboard() {
        dashboardContainer.getChildren().clear();

        Label titulo = new Label("Inventory Dashboard");
        titulo.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #60a5fa;");
        titulo.setPadding(new Insets(20, 0, 20, 0));

        HBox metricsBox = createMetricsBox();

        HBox chartsBox = createChartsBox();

        dashboardContainer.getChildren().addAll(titulo, metricsBox, chartsBox);
    }

    private HBox createMetricsBox() {
        HBox box = new HBox(15);
        box.setPadding(new Insets(20));
        box.setStyle("-fx-background-color: #1a1f2e; -fx-border-radius: 12; -fx-background-radius: 12; -fx-border-color: #3b82f6; -fx-border-width: 1;");
        box.setSpacing(15);

        VBox metric1 = createMetricCard(
                "[CUSTOMERS]",
                String.valueOf(service.getTotalCustomers()),
                "#60a5fa"
        );
        HBox.setHgrow(metric1, Priority.ALWAYS);
        box.getChildren().add(metric1);

        VBox metric2 = createMetricCard(
                "[PRODUCTS]",
                String.valueOf(service.getTotalProducts()),
                "#34d399"
        );
        HBox.setHgrow(metric2, Priority.ALWAYS);
        box.getChildren().add(metric2);

        VBox metric3 = createMetricCard(
                "[REVENUE]",
                String.format("$%.2f", service.getTotalSalesRevenue()),
                "#fbbf24"
        );
        HBox.setHgrow(metric3, Priority.ALWAYS);
        box.getChildren().add(metric3);

        VBox metric4 = createMetricCard(
                "[STOCK]",
                String.valueOf(service.getTotalStock()),
                "#f87171"
        );
        HBox.setHgrow(metric4, Priority.ALWAYS);
        box.getChildren().add(metric4);

        return box;
    }

    private VBox createMetricCard(String title, String value, String colorHex) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(20));
        card.setStyle("-fx-background-color: #1a1f2e; -fx-border-radius: 8; -fx-background-radius: 8; -fx-border-color: " + colorHex + "; -fx-border-width: 2;");
        card.setMinHeight(120);

        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: 600; -fx-text-fill: #cbd5e1;");

        Label valueLabel = new Label(value);
        valueLabel.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: " + colorHex + ";");
        VBox.setVgrow(valueLabel, Priority.ALWAYS);

        card.getChildren().addAll(titleLabel, valueLabel);
        return card;
    }

    private HBox createChartsBox() {
        HBox box = new HBox(15);
        box.setPadding(new Insets(20));
        box.setStyle("-fx-background-color: transparent;");
        box.setSpacing(15);

        VBox chart1 = createSalesTrendChart();
        HBox.setHgrow(chart1, Priority.ALWAYS);

        VBox chart2 = createStockChart();
        HBox.setHgrow(chart2, Priority.ALWAYS);

        box.getChildren().addAll(chart1, chart2);
        return box;
    }

    private VBox createSalesTrendChart() {
        VBox container = new VBox(10);
        container.setPadding(new Insets(20));
        container.setStyle("-fx-background-color: #1a1f2e; -fx-border-radius: 12; -fx-background-radius: 12; -fx-border-color: #3b82f6; -fx-border-width: 1;");
        container.setPrefHeight(400);

        Label titleLabel = new Label("Sales Trend");
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #60a5fa;");

        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Mes");
        yAxis.setLabel("Ventas ($)");
        xAxis.setStyle("-fx-text-fill: #cbd5e1;");
        yAxis.setStyle("-fx-text-fill: #cbd5e1;");

        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setStyle("-fx-background-color: #252d3d; -fx-legend-visible: true;");
        lineChart.setLegendVisible(true);
        VBox.setVgrow(lineChart, Priority.ALWAYS);

        Map<String, Double> trendData = service.getSalesTrend();
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Ventas");

        int mes = 1;
        for (Double valor : trendData.values()) {
            series.getData().add(new XYChart.Data<>(mes++, valor));
        }

        lineChart.getData().add(series);

        container.getChildren().addAll(titleLabel, lineChart);
        return container;
    }

    private VBox createStockChart() {
        VBox container = new VBox(10);
        container.setPadding(new Insets(20));
        container.setStyle("-fx-background-color: #1a1f2e; -fx-border-radius: 12; -fx-background-radius: 12; -fx-border-color: #3b82f6; -fx-border-width: 1;");
        container.setPrefHeight(400);

        Label titleLabel = new Label("Sales by Product Type");
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #60a5fa;");

        // Crear BarChart
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Tipo de Producto");
        yAxis.setLabel("Ventas Totales ($)");
        xAxis.setStyle("-fx-text-fill: #cbd5e1; -fx-font-size: 11px;");
        yAxis.setStyle("-fx-text-fill: #cbd5e1; -fx-font-size: 11px;");
        xAxis.setTickLabelRotation(45);

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setStyle("-fx-background-color: #252d3d;");
        barChart.setLegendVisible(true);
        barChart.setAnimated(false);
        VBox.setVgrow(barChart, Priority.ALWAYS);

        Map<String, Double> salesByType = service.getSalesByProductType();

        if (salesByType.isEmpty()) {
            salesByType.put("Electrónica", 500.0);
            salesByType.put("Herramientas", 350.0);
            salesByType.put("Accesorios", 250.0);
            salesByType.put("Otros", 150.0);
        }

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Ventas");

        for (Map.Entry<String, Double> entry : salesByType.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        barChart.getData().add(series);

        container.getChildren().addAll(titleLabel, barChart);
        return container;
    }
}

