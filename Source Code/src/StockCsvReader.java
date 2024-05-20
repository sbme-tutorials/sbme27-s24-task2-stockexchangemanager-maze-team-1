import ClassesLogic.CSVHandler;
import ClassesLogic.Noti;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;

public class StockCsvReader implements Initializable {
    ObservableList<Stocks> stocksRow = FXCollections.observableArrayList();
    ObservableList<Stocks> userData = FXCollections.observableArrayList();
    private int purchasedQuantity;
    private Map<Stocks, TextField> quantityFieldsMap = new HashMap<>();
    @FXML
    private TableView<Stocks> tableView;
    @FXML
    private TableColumn<Stocks, Button> BuyColumn;
    @FXML
    private TableColumn<Stocks, TextField> QuantityCol;
    @FXML
    private TableColumn<Stocks, Float> PriceColumn;
    @FXML
    private TableColumn<Stocks, String> StockColumn;
    @FXML
    private TableColumn<Stocks, Integer> Stocksnumber;
    @FXML
    private Button userscreen2_deposit;
    @FXML
    private TableColumn<Stocks, String> UserStockCol;
    @FXML
    private TableColumn<Stocks, Button> UserStockSellCol;
    @FXML
    private TableColumn<Stocks, Integer> UserStocknumCol;
    @FXML
    private TableColumn<Stocks, TextField> UserStockquiaCol;
    @FXML
    private TableColumn<Stocks, Float> UserPrice;
    @FXML
    private TableView<Stocks> UserTable;
    @FXML
    private Stage stage;
    @FXML
    private Parent root;
    float writePirce;

    CSVHandler csv = new CSVHandler();
    public StockCsvReader() throws FileNotFoundException {
    }
    Noti noti = new Noti();
    // Set column widths
    public void readDataFromFile(String filename, TableView<Stocks> table, ObservableList<Stocks> data) {
        String line;
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            while ((line = reader.readLine()) != null) {
                String[] row = line.split(",");
                if (row.length == 3) {
                    String stockName = row[0];
                    int stocksNumber = Integer.parseInt(row[2]);
                    float price = Float.parseFloat(row[1]);
                    Stocks stocks = new Stocks(stockName, price, stocksNumber);
                    data.add(stocks); // Add stock to the appropriate observable list
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        table.setItems(data); // Set items to the table
    }
    public void initialize(URL arg0, ResourceBundle arg1) {
        // Set cell value factories
        PriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        Stocksnumber.setCellValueFactory(new PropertyValueFactory<>("stocknumber"));
        StockColumn.setCellValueFactory(new PropertyValueFactory<>("stockname"));
        UserPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        UserStocknumCol.setCellValueFactory(new PropertyValueFactory<>("stocknumber"));
        UserStockCol.setCellValueFactory(new PropertyValueFactory<>("stockname"));
        setupQuantityColumn(QuantityCol);
        setupQuantityColumn(UserStockquiaCol);
        setupBuyColumn(BuyColumn, "      Buy     ");
        setupBuyColumn(UserStockSellCol, "      Sell     ");
        // Read data for tableView
        readDataFromFile(Main.StockFile, tableView, stocksRow);

        // Read data for UserTable
        readDataFromFile(controller.userFilename, UserTable, userData);
    }

    private void setupQuantityColumn(TableColumn TextBoxCol) {
        TextBoxCol.setCellFactory(column -> {
            return new TableCell<Stocks, TextField>() {
                final TextField quantityField = new TextField();

                {
                    // Add event handler to the TextField
                    quantityField.setOnAction(event -> {
                        Stocks stock = getTableView().getItems().get(getIndex());
                        // Define the action to take when Enter is pressed in the TextField
                        // For example, you can retrieve the entered quantity
                        String quantity = quantityField.getText();
                        System.out.println("Buying " + quantity + " of " + stock.getStockname());
                    });
                }

                @Override
                protected void updateItem(TextField item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(quantityField);
                        Stocks stock = getTableView().getItems().get(getIndex());
                        quantityFieldsMap.put(stock, quantityField);
                    }
                }
            };
        });
    }

    // Method to read balance from user information
    public float readBalance(String username) {
        float balance = 0.0f;
        try (BufferedReader reader = new BufferedReader(new FileReader(Main.file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4 && parts[1].equals(username)) {
                    // Assuming balance is in the fourth column (index 3)
                    balance = Float.parseFloat(parts[3]);
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return balance;
    }
    // Method to update balance in user information
    public void updateBalance(String username, double newBalance) {
        try {
            File userFile = new File(Main.file);
            // Check if the file exists
            if (userFile.exists()) {
                // Read the file to find the user and update the balance
                Scanner scanner = new Scanner(userFile);
                StringBuilder fileContent = new StringBuilder();
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] parts = line.split(",");
                    if (parts.length >= 4 && parts[1].equals(username)) {
                        // Update the balance
                        parts[3] = Double.toString(newBalance);
                        line = String.join(",", parts);
                    }
                    fileContent.append(line).append("\n");
                }
                scanner.close();

                // Write the updated content back to the file
                FileWriter fileWriter = new FileWriter(Main.file);
                fileWriter.write(fileContent.toString());
                fileWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setupBuyColumn(TableColumn ButtonCol, String text) {
        // Read balance for a user
        double Balance = readBalance(controller.Username);
        System.out.println(Balance);
        System.out.println(controller.Username);
// Update balance for a user

        ButtonCol.setCellFactory(column -> {
            return new TableCell<Stocks, Button>() {
                final Button button = new Button(text);
                {
                    // Add event handler to the Buy button
                    button.setOnAction(event -> {
                        double newBalance;
                        Stocks stock = getTableView().getItems().get(getIndex());
                        // Retrieve the quantity text field associated with the stock
                        TextField quantityField = quantityFieldsMap.get(stock);
                        if (quantityField != null) {
                            // Define the action to take when Buy button is clicked
                            // For example, you can retrieve the entered quantity
                            String quantity = quantityField.getText();
                            if (!quantity.isEmpty()) {
                                try {
                                    float pastPrice=0;
                                    purchasedQuantity = Integer.parseInt(quantity);
                                    float quantityPrice = stock.getPrice() * purchasedQuantity;
                                    if ((purchasedQuantity <= stock.getStocknumber()) && (Balance >= quantityPrice)) {// Ensure enough stock is available
                                        int remainingStock;
                                        if (Objects.equals(text, "      Buy     "))
                                        {
                                            remainingStock = stock.getStocknumber() - purchasedQuantity;
                                            newBalance = Balance - quantityPrice;
                                            pastPrice=stock.getPrice();
                                            writePirce=stock.getPrice() +quantityPrice * 0.000002f;
                                            stock.setPrice(writePirce);
                                            if (CSVHandler.checkUserPremiumStatus("src//Users_information.csv",controller.Username)){
                                            noti.displayStockPriceChange(stock.getStockname(),pastPrice,writePirce);}
                                        } else {
                                            pastPrice=stock.getPrice();
                                            remainingStock = stock.getStocknumber() - purchasedQuantity;
                                            newBalance = Balance + quantityPrice;
                                            writePirce=stock.getPrice() - quantityPrice * 0.0000021f;
                                            stock.setPrice(writePirce);
                                            if (CSVHandler.checkUserPremiumStatus("src//Users_information.csv", controller.Username)){
                                                noti.displayStockPriceChange(stock.getStockname(),pastPrice,writePirce);}
                                        }
                                        writeTransaction(stock.getStockname(),pastPrice,purchasedQuantity,  quantityPrice  ,new Date(),text);
                                        System.out.println(text + purchasedQuantity + " of " + stock.getStockname());
                                        System.out.println("Remaining stock of " + stock.getStockname() + ": " + remainingStock);
                                        stock.setStocknumber(remainingStock);
                                        writePriceToStockFile(writePirce, stock.getStockname(), new Date());
                                        // Write data to CSV file
                                        writeDataToFile(stock, purchasedQuantity, text);
                                        updateBalance(controller.Username, newBalance);
                                        UserTable.refresh();
                                        tableView.refresh();
                                    } else {
                                        System.out.println("Not enough stock available.");
                                    }
                                } catch (Exception e) {
                                    System.out.println("You Entered invalid input");
                                }
                            } else {
                                System.out.println("Please enter a quantity.");
                            }
                        }
                    });
                }

                @Override
                protected void updateItem(Button item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(button);
                    }
                }
            };
        });
    }
    public void writeDataToUserFile(Stocks stock, int boughtStocks, String text) {
        try {
            File userFile = new File(controller.userFilename);
            // Check if the file exists
            if (userFile.exists()) {
                // Read the file to check if the stock already exists
                Scanner scanner = new Scanner(userFile);
                StringBuilder fileContent = new StringBuilder();
                boolean stockExists = false;
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] parts = line.split(",");
                    if (parts.length >= 2 && parts[0].equals(stock.getStockname())) {
                        // Update the quantity if the stock already exists
                        fileContent.append(stock.getStockname()).append(",");
                        fileContent.append(stock.getPrice()).append(",");
                        if (Objects.equals(text, "      Buy     "))
                        {
                            fileContent.append(Integer.parseInt(parts[2]) + boughtStocks); // Update quantity
                        } else
                        {
                            fileContent.append(Integer.parseInt(parts[2]) - boughtStocks); // Update quantity
//                            writeDataToFile(stock, purchasedQuantity, "      Sell     ");
                        }
                        fileContent.append("\n");
                        stockExists = true;
                    } else {
                        fileContent.append(line).append("\n");
                    }
                }
                scanner.close();
                // If stock doesn't exist, append it as a new entry
                if (!stockExists) {
                    fileContent.append(stock.getStockname()).append(",");
                    fileContent.append(stock.getPrice()).append(",");
                    fileContent.append(boughtStocks);
                    fileContent.append("\n");
                }
                // Write the updated content back to the file
                FileWriter fileWriter = new FileWriter(controller.userFilename);
                fileWriter.write(fileContent.toString());
                fileWriter.close();
            } else {
                // If file doesn't exist, create a new file and write the stock data
                PrintWriter writer = new PrintWriter(new FileWriter(controller.userFilename));
                writer.print(stock.getStockname());
                writer.print(",");
                writer.print(stock.getPrice());
                writer.print(",");
                writer.println(boughtStocks);
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    public void writeDataToFile(Stocks stock, int boughtStocks, String text) {
        try {
            File StockFile = new File(Main.StockFile);
            // Check if the file exists
            // Read the file to check if the stock already exists
            Scanner scanner = new Scanner(StockFile);
            StringBuilder fileContent = new StringBuilder();
            boolean stockExists = false;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length >= 2 && parts[0].equals(stock.getStockname())) {
                    // Update the quantity if the stock already exists
                    fileContent.append(stock.getStockname()).append(",");
                    fileContent.append(stock.getPrice()).append(",");
                    if (Objects.equals(text, "      Buy     ")) {
                        fileContent.append(Integer.parseInt(parts[2]) - boughtStocks); // Update quantity
                        writeDataToUserFile(stock, boughtStocks, text);

                        //                        fileContent.append(Integer.parseInt(parts[1])
                    } else
                    {
                        fileContent.append(Integer.parseInt(parts[2]) + boughtStocks);
                        writeDataToUserFile(stock, boughtStocks, text);

                    }
                    fileContent.append("\n");
                    stockExists = true;
                } else {
                    fileContent.append(line).append("\n");
                }
            }
            scanner.close();
            // If stock doesn't exist, append it as a new entry
            if (!stockExists) {
                fileContent.append(stock.getStockname()).append(",");
                fileContent.append(stock.getPrice()).append(",");
                fileContent.append(boughtStocks);
                fileContent.append("\n");
            }

            // Write the updated content back to the file
            FileWriter fileWriter = new FileWriter(Main.StockFile);
            fileWriter.write(fileContent.toString());
            fileWriter.close();

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    public void writePriceToStockFile(float stockPrice, String stockName, Date timestamp) {
        try {
            String directoryPath = "src//StocksCsv//";
            String stockPath = directoryPath + stockName + ".csv";

            // Create FileWriter in append mode to append to existing file content
            FileWriter fileWriter = new FileWriter(stockPath, true);
            PrintWriter writer = new PrintWriter(fileWriter);

            // Format the timestamp
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedTimestamp = dateFormat.format(timestamp);

            // Append the timestamp and price to the file
            writer.print(stockPrice + "," + formattedTimestamp);
            writer.println(); // Move to the next line for cleanliness (optional)
            writer.close();
            fileWriter.close(); // Close FileWriter after PrintWriter

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    public void writeTransaction(String stockName,float stockPrice,int Quantity,float quantityPrice,Date timestamp,String state){
        try {
//            String transactionPath = "src/UserTransaction/";
//            controller.transactionFilename = transactionPath + usernameInput + ".csv";

            // Create FileWriter in append mode to append to existing file content
            FileWriter fileWriter = new FileWriter( controller.transactionFilename, true);
            PrintWriter writer = new PrintWriter(fileWriter);

            // Format the timestamp
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedTimestamp = dateFormat.format(timestamp);

            // Append the timestamp and price to the file
            writer.print(state+","+stockName+","+stockPrice +","+Quantity+","+quantityPrice+","+formattedTimestamp);
            writer.println(); // Move to the next line for cleanliness (optional)
            writer.close();
            fileWriter.close(); // Close FileWriter after PrintWriter

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    @FXML
    public void switchtodepositscreen(ActionEvent event) {
        if (event.getSource() == userscreen2_deposit) {
            try {
                root = FXMLLoader.load(getClass().getResource("DepositeScene.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setTitle("Stock screen section");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    @FXML
    public void switchtoPriceHistoryScreen(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("PriceHistoryScreen.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setTitle("Price History");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @FXML
    public void switchtostockscreen(ActionEvent event) {

        // Get the current time
        LocalTime currentTime = LocalTime.now();

        // Check if it's within the trading hours (6am to 6pm)
        if (currentTime.isAfter(LocalTime.of(8, 0)) && currentTime.isBefore(LocalTime.of(20, 0))) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("stockscreen.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                stage.setTitle("Stock Screen Section");
                stage.setScene(new Scene(root));
                stage.show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // If trading is closed, print a message
            System.out.println("Trading is closed.");
        }
    }
    @FXML
    void SwitchToHomeScene(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("HomeScreen.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setTitle("Price History");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void switchToTransaction(ActionEvent event) {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("TransactionScreen.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Stock screen section");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}