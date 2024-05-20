package ClassesLogic;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class TransactionController implements Initializable {

    @FXML
    private Button Deposit;

    @FXML
    private Button Homebutton;

    @FXML
    private TableColumn<Transactions, Float> PriceCol;

    @FXML
    private TableColumn<Transactions, String> StateCol;

    @FXML
    private TableColumn<Transactions, Integer> StockNumCol;

    @FXML
    private TableColumn<Transactions, String> StocknameCol;
    @FXML
    private TableColumn<Transactions, Float> totalPriceCol;

    @FXML
    private TableColumn<Transactions, Date> TimeCol;

    @FXML
    private TableView<Transactions> TransactionTable;

    @FXML
    private Button Transactionburron;

    @FXML
    private Button UserPricehistory;

    @FXML
    private Button UserScreen2_orderstocks;
    @FXML
    private Stage stage;
    @FXML
    private Parent root;
    ObservableList<Transactions>  TransactionRow = FXCollections.observableArrayList();

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // Set cell value factories
        TimeCol.setCellValueFactory(new PropertyValueFactory<>("timestamp")); // Use "timestamp" instead of "Date"
        StockNumCol.setCellValueFactory(new PropertyValueFactory<>("stockNumber"));
        StocknameCol.setCellValueFactory(new PropertyValueFactory<>("stockName"));
        PriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        totalPriceCol.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        StateCol.setCellValueFactory(new PropertyValueFactory<>("state"));

        // Read data from file and populate the table
        readDataFromFile(controller.transactionFilename, TransactionTable, TransactionRow);
    }




    public void readDataFromFile(String filename, TableView<Transactions> table, ObservableList<Transactions> data) {
        String line;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            while ((line = reader.readLine()) != null) {
                String[] row = line.split(",");
                if (row.length == 6) { // Ensure the row has 6 elements
                    String state = row[0];
                    String stockName = row[1];
                    float stockPrice = Float.parseFloat(row[2]);
                    int stockNumber = Integer.parseInt(row[3]);
                    float totalPrice = Float.parseFloat(row[4]);
                    Date timestamp = dateFormat.parse(row[5]); // Parse the date

                    Transactions transactions = new Transactions(state, stockName, stockPrice, stockNumber, totalPrice, timestamp);
                    data.add(transactions); // Add transaction to the observable list
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        table.setItems(data); // Set items to the table
    }
    public void switchToTransaction(ActionEvent event) {

            try {
                Parent root = FXMLLoader.load(getClass().getResource("src\\TransactionScreen.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setTitle("Stock screen section");
                stage.setScene(new Scene(root));
                stage.show();

            } catch (Exception e) {
                e.printStackTrace();
            }

    }
    public void switchtostockscreen(ActionEvent event) {
        if (event.getSource() == UserScreen2_orderstocks) {

            try {
                Parent root = FXMLLoader.load(getClass().getResource("src\\stockscreen.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setTitle("Stock screen section");
                stage.setScene(new Scene(root));
                stage.show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void switchtodepositscreen(ActionEvent event) {

            try {
                Parent root = FXMLLoader.load(getClass().getResource("src\\DepositeScene.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                stage.setTitle("Stock screen section");
                stage.setScene(new Scene(root));
                stage.show();

            } catch (Exception e) {
                e.printStackTrace();
            }

    }
    @FXML
    public void switchtoPriceHistoryScreen(ActionEvent event) {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("src\\PriceHistoryScreen.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setTitle("Price History");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    void SwitchToHomeScene(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("src\\HomeScreen.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setTitle("Price History");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
