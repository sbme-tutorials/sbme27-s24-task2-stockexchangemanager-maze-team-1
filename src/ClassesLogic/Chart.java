package ClassesLogic;

import javafx.scene.chart.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.paint.Color;

//public class Chart {
//    private LineChart<String, Number> lineChart;
//
//    public Chart() {
//        this.lineChart = new LineChart<>(new CategoryAxis(), new NumberAxis());
//    }
//
//    public Node getLineChart(Stock stock) {
//        XYChart.Series<String, Number> series = new XYChart.Series<>();
//        series.setName(stock.getName());
//
//        // Assuming Stock class has a method getPriceHistory that returns a Map
//        stock.getPriceHistory().forEach((date, price) -> {
//            XYChart.Data<String, Number> data = new XYChart.Data<>(date.toString(), price);
//            series.getData().add(data);
//
//            // Change the color of the line based on price change
//            if (price > stock.getOpeningPrice()) {
//                data.getNode().setStyle("-fx-stroke: green;");
//            } else {
//                data.getNode().setStyle("-fx-stroke: red;");
//            }
//        });
//
//        lineChart.getData().add(series);
//        return lineChart;
//    }
//
//}
