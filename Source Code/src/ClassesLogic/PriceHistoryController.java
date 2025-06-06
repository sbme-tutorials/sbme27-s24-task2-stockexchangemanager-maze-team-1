package ClassesLogic;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PriceHistoryController implements Initializable {
    @FXML
    private LineChart<String, Number> Pricechart;
    @FXML
    private Button UserPricehistory;
    @FXML
    private Button UserScreen2_orderstocks;
    @FXML
    private Button userscreen2_deposit;
    @FXML
    private CategoryAxis x;
    @FXML
    private NumberAxis y;
    @FXML
    private ComboBox<String> comboBox;
    private Stage stage;

    public PriceHistoryController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<String> stockNames = CSVHandler.readFirstColumnFromCsv("src\\1_deliverable\\StocksFile.csv");
        this.comboBox.setItems(FXCollections.observableArrayList(stockNames));
    }

    public void switchtostockscreen(ActionEvent event) {
        if (event.getSource() == this.UserScreen2_orderstocks) {
            loadScene(event, "1_deliverable\\stockscreen.fxml", "Stock screen section");
        }
    }

    public void switchtodepositscreen(ActionEvent event) {
        if (event.getSource() == this.userscreen2_deposit) {
            loadScene(event, "1_deliverable\\DepositeScene.fxml", "Deposit screen section");
        }
    }

    @FXML
    public void switchtoPriceHistoryScreen(ActionEvent event) {
        loadScene(event, "1_deliverable\\PriceHistoryScreen.fxml", "Price History");
    }

    @FXML
    void SwitchToHomeScene(ActionEvent event) {
        loadScene(event, "1_deliverable\\HomeScreen.fxml", "Home Screen");
    }

    @FXML
    void getCombo(ActionEvent event) {
        // Clear the chart data before adding new data
        this.Pricechart.getData().clear();

        String stockname = this.comboBox.getValue();
        if (stockname != null) {
            File file = new File("src\\1_deliverable\\StocksCsv/" + stockname + ".csv");

            // Read float values from CSV
            ArrayList<Float> f = (ArrayList<Float>) CSVHandler.readFloatValuesFromCsv(file.getPath());
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            System.out.println(f);

            // Read hour and minute values from CSV
            ArrayList<String> mh = (ArrayList<String>) CSVHandler.readHourMinuteValuesFromCsv(file.getPath());
            System.out.println(mh);
            System.out.println(stockname);

            // Set the name of the series
            series.setName(stockname);

            // Add data points to the series
            int size = Math.min(f.size(), mh.size());
            for (int i = 0; i < size; ++i) {
                XYChart.Data<String, Number> dataPoint = new XYChart.Data<>(mh.get(i), f.get(i));
                series.getData().add(dataPoint);
            }

            // Add the series to the chart
            this.Pricechart.getData().add(series);
        }
    }

    private void loadScene(ActionEvent event, String fxmlFile, String title) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
            this.stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            this.stage.setTitle(title);
            this.stage.setScene(new Scene(root));
            this.stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void switchToTransaction(ActionEvent event) {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("1_deliverable\\TransactionScreen.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Stock screen section");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}