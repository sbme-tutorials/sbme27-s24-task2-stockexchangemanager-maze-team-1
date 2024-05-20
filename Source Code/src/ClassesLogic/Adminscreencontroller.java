package ClassesLogic;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class Adminscreencontroller implements Initializable {

    @FXML
    private TableColumn<Stocks2, String> stockColumn;

    @FXML
    private TableColumn<Stocks2, Float> tradingpriceColumn;

    @FXML
    private TableColumn<Stocks2, Integer> avaliblestocksColumn;

    @FXML
    private TableColumn<Stocks2, Void> changePriceColumn;

    @FXML
    private TableColumn<Stocks2, Void> changeStockNumberColumn;

    @FXML
    private TableColumn<Stocks2, Void> deleteColumn;

    @FXML
    private TableView<Stocks2> stockTable;
    @FXML
    private Button approvalButton;

    @FXML
    private Button addButton;
    @FXML
    private Button usermanagementbtn;
    private Stage stage;
    private final ObservableList<Stocks2> stockList = FXCollections.observableArrayList();
//    private final String csvFilePath = "src//testfile.csv";

    public void switchtostousermanagement(ActionEvent event) {
        if (event.getSource() == usermanagementbtn) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("1_deliverable\\AdminScene.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setTitle("Stock screen section");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void switchtoApproval(ActionEvent event) {
        if (event.getSource() == approvalButton) {

            try {
                Parent root = FXMLLoader.load(getClass().getResource("1_deliverable\\ApprovalSystem.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                stage.setScene(new Scene(root));
                stage.show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Linking table columns with Stocks properties
        stockColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStockName()));
        tradingpriceColumn.setCellValueFactory(
                cellData -> new SimpleFloatProperty(cellData.getValue().getTradingPrice()).asObject());
        avaliblestocksColumn.setCellValueFactory(
                cellData -> new SimpleIntegerProperty(cellData.getValue().getAvaliblestocks()).asObject());

        // Create change price button column
        changePriceColumn.setCellFactory(param -> new TableCell<>() {
            private final Button changePriceButton = new Button("Change Price");

            {
                changePriceButton.setOnAction(event -> {
                    Stocks2 stock = getTableView().getItems().get(getIndex());
                    TextInputDialog dialog = new TextInputDialog();
                    dialog.setTitle("Change Price");
                    dialog.setHeaderText(null);
                    dialog.setContentText("Enter new price:");
                    Optional<String> result = dialog.showAndWait();
                    result.ifPresent(price -> {
                        float newPrice = Float.parseFloat(price);
                        stock.setTradingPrice(newPrice);
                        updateCSV();
                    });
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(changePriceButton);
                }
            }
        });

        // Create change stock number button column
        changeStockNumberColumn.setCellFactory(param -> new TableCell<>() {
            private final Button changeStockNumberButton = new Button("Change Stock Number");

            {
                changeStockNumberButton.setOnAction(event -> {
                    Stocks2 stock = getTableView().getItems().get(getIndex());
                    TextInputDialog dialog = new TextInputDialog();
                    dialog.setTitle("Change Stock Number");
                    dialog.setHeaderText(null);
                    dialog.setContentText("Enter new stock number:");
                    Optional<String> result = dialog.showAndWait();
                    result.ifPresent(number -> {
                        int newStockNumber = Integer.parseInt(number);
                        stock.setAvailableStocks(newStockNumber);
                        updateCSV();
                    });
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(changeStockNumberButton);
                }
            }
        });

        // Create delete button column
        deleteColumn.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("Delete");

            {
                deleteButton.setOnAction(event -> {
                    Stocks2 stock = getTableView().getItems().get(getIndex());
                    stockList.remove(stock);
                    updateCSV();
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });

        // Populate table with data
        readData();
        stockTable.setItems(stockList);

        // Add button event handler
        addButton.setOnAction(event -> addEntry());
    }

    private void readData() {
        String line;
        try (BufferedReader reader = new BufferedReader(new FileReader(Main.StockFile))) {
            while ((line = reader.readLine()) != null) {
                String[] row = line.split(",");
                if (row.length == 3) { // Ensure all columns are present
                    String stockName = row[0];
                    float tradingPrice = Float.parseFloat(row[1]);
                    int availableStocks = Integer.parseInt(row[2]);
                    Stocks2 stock = new Stocks2(stockName, tradingPrice, availableStocks);
                    stockList.add(stock);
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
            showErrorDialog("Error", "Failed to read data from CSV file.");
        }
    }

    private void addEntry() {
        // Create a dialog to prompt the user for new stock details
        Dialog<Stocks2> dialog = new Dialog<>();
        dialog.setTitle("Add New Stock");
        dialog.setHeaderText(null);

        // Set the button types
        ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        // Create text fields and labels for stock details
        TextField stockNameField = new TextField();
        stockNameField.setPromptText("Stock Name");
        TextField tradingPriceField = new TextField();
        tradingPriceField.setPromptText("Trading Price");
        TextField availableStocksField = new TextField();
        availableStocksField.setPromptText("Available Stocks");

        // Add text fields to a VBox
        VBox vbox = new VBox(10, new Label("Enter Stock Details:"), stockNameField, tradingPriceField,
                availableStocksField);
        dialog.getDialogPane().setContent(new ScrollPane(vbox));

        // Request focus on the stock name field by default
        dialog.setOnShown(event -> stockNameField.requestFocus());

        // Convert the result to a stock when the addButton is clicked
        dialog.setResultConverter(buttonType -> {
            if (buttonType == addButtonType) {
                try {
                    String stockName = stockNameField.getText();
                    Float tradingPrice = Float.parseFloat(tradingPriceField.getText());
                    int availableStocks = Integer.parseInt(availableStocksField.getText());
                    return new Stocks2(stockName, tradingPrice, availableStocks);
                } catch (NumberFormatException e) {
                    showErrorDialog("Invalid Input",
                            "Please enter valid numbers for trading price and available stocks.");
                }
            }
            return null;
        });

        // Show the dialog and handle the result
        Optional<Stocks2> result = dialog.showAndWait();
        result.ifPresent(stock -> {
            stockList.add(stock);
            writeEntryToCSV(stock);
        });
    }

    private void writeEntryToCSV(Stocks2 stock) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(Main.StockFile, true))) {
            writer.println(stock.getStockName() + "," + stock.getTradingPrice() + "," + stock.getAvaliblestocks());
        } catch (IOException e) {
            e.printStackTrace();
            showErrorDialog("Error", "Failed to write data to CSV file.");
        }
    }

    private void updateCSV() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(Main.StockFile))) {
            for (Stocks2 stock : stockList) {
                writer.println(stock.getStockName() + "," + stock.getTradingPrice() + "," + stock.getAvaliblestocks());
            }
        } catch (IOException e) {
            e.printStackTrace();
            showErrorDialog("Error", "Failed to update data in CSV file.");
        }
    }

    private void showErrorDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }  public void switchtostockmanege(ActionEvent event) {

            try {
                Parent root = FXMLLoader.load(getClass().getResource("1_deliverable\\AdminStocksScene.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                stage.setScene(new Scene(root));
                stage.show();

            } catch (Exception e) {
                e.printStackTrace();
            }

    }


    public void switchtotradingsession(ActionEvent event) {

            try {
                Parent root = FXMLLoader.load(getClass().getResource("1_deliverable\\tradingSrssionscreen.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setTitle("Approval System screen section");
                stage.setScene(new Scene(root));
                stage.show();

            } catch (Exception e) {
                e.printStackTrace();
            }

    }
}