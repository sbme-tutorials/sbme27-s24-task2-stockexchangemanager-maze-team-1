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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    @FXML
    private TableView<Admin> adminTable;
    @FXML
    private TableColumn<Admin, String> emailColumn;
    @FXML
    private TableColumn<Admin, String> usernameColumn;
    @FXML
    private TableColumn<Admin, String> passwordColumn;
    @FXML
    private TableColumn<Admin, Double> balanceColumn;
    @FXML
    private TableColumn<Admin, Integer> premiumColumn;
    @FXML
    private TableColumn<Admin, Void> deleteColumn;
    @FXML
    private Button stockManegeButton;
    @FXML
    private Button addButton;
    @FXML
    private Button stockOrderButton;
    @FXML
    private Button approvalButton;
    @FXML
    private Button tradingSessionButton;

    private Stage stage;

    private final ObservableList<Admin> adminList = FXCollections.observableArrayList();
    private final String csvFilePath = "src//Users_information.csv";

    public void switchtostockmanege(ActionEvent event) {
        if (event.getSource() == stockManegeButton) {

            try {
                Parent root = FXMLLoader.load(getClass().getResource("AdminStocksScene.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

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
                Parent root = FXMLLoader.load(getClass().getResource("ApprovalSystem.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                stage.setScene(new Scene(root));
                stage.show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void switchtotradingsession(ActionEvent event) {
        if (event.getSource() ==  tradingSessionButton) {

            try {
                Parent root = FXMLLoader.load(getClass().getResource("tradingSrssionscreen.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setTitle("Approval System screen section");
                stage.setScene(new Scene(root));
                stage.show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Linking table columns with Admin properties
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        balanceColumn.setCellValueFactory(new PropertyValueFactory<>("balance"));
        premiumColumn.setCellValueFactory(new PropertyValueFactory<>("premium"));

        // Create delete button column
        deleteColumn.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("Delete");

            {
                deleteButton.setOnAction(event -> {
                    Admin admin = getTableView().getItems().get(getIndex());
                    adminList.remove(admin);
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
        adminTable.setItems(adminList);

        // Add button event handler
        addButton.setOnAction(event -> addEntry());
    }

    private void readData() {
        String line;
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {
            while ((line = reader.readLine()) != null) {
                String[] row = line.split(",");
                if (row.length == 5) {
                    String email = row[0];
                    String username = row[1];
                    String password = row[2];
                    double balance = Double.parseDouble(row[3]);
                    int premium = Integer.parseInt(row[4]);
                    Admin admin = new Admin(email, username, password, balance, premium);
                    adminList.add(admin);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addEntry() {
        // Create a dialog to prompt the user for new admin details
        Dialog<Admin> dialog = new Dialog<>();
        dialog.setTitle("Add New Admin");
        dialog.setHeaderText(null);

        // Set the button types
        ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        // Create text fields and labels for the new entry
        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        TextField passwordField = new TextField();
        passwordField.setPromptText("Password");
        TextField balanceField = new TextField();
        balanceField.setPromptText("Balance");
        TextField premiumField = new TextField();
        premiumField.setPromptText("Premium");

        // Add text fields to a VBox
        VBox vbox = new VBox(10, new Label("Enter Admin Details:"),
                emailField, usernameField, passwordField, balanceField, premiumField);
        dialog.getDialogPane().setContent(vbox);

        // Request focus on the email field by default
        dialog.setOnShown(event -> emailField.requestFocus());

        // Convert the result to an Admin object when the add button is clicked
        dialog.setResultConverter(buttonType -> {
            if (buttonType == addButtonType) {
                try {
                    String email = emailField.getText();
                    String username = usernameField.getText();
                    String password = passwordField.getText();
                    double balance = Double.parseDouble(balanceField.getText());
                    int premium = Integer.parseInt(premiumField.getText());
                    return new Admin(email, username, password, balance, premium);
                } catch (NumberFormatException e) {
                    showErrorDialog("Invalid Input", "Please enter valid numbers for balance and premium.");
                }
            }
            return null;
        });

        // Show the dialog and handle the result
        Optional<Admin> result = dialog.showAndWait();
        result.ifPresent(admin -> {
            adminList.add(admin);
            writeEntryToCSV(admin);
        });
    }

    private void writeEntryToCSV(Admin admin) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(csvFilePath, true))) {
            writer.println(String.join(",", admin.getEmail(), admin.getUsername(), admin.getPassword(),
                    String.valueOf(admin.getBalance()), String.valueOf(admin.getPremium())));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showErrorDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}