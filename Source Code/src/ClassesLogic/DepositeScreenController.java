package ClassesLogic;
import java.io.FileWriter;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import java.util.Optional;

public class DepositeScreenController {
    @FXML
    private Button Deposit_button;
    @FXML
    private Button withdraw_button;
    @FXML
    private Button UserScreen2_orderstocks;
    @FXML
    private Button userscreen2_deposit;
    @FXML
    private TextField deposit_withdraw_textfield;
    private Stage stage;
    private Parent root;

    public void switchtostockscreen(ActionEvent event) {
        if (event.getSource() == UserScreen2_orderstocks) {

            try {
                Parent root = FXMLLoader.load(getClass().getResource("1_deliverable\\stockscreen.fxml"));
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
        if (event.getSource() == userscreen2_deposit) {

            try {
                Parent root = FXMLLoader.load(getClass().getResource("1_deliverable\\DepositeScene.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                stage.setTitle("Stock screen section");
                stage.setScene(new Scene(root));
                stage.show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void deposit_Withdraw() {

        Deposit_button.setOnAction(event -> {
            String amount = deposit_withdraw_textfield.getText();
            if (amount == null || amount.trim().isEmpty()) {
                showAlert("Input Error", "Please enter an amount to deposit.");
            } else {
                boolean confirmed = showConfirmationDialog("Deposit Confirmation",
                        "Are you sure you want to deposit $" + amount + "?");
                if (confirmed) {
                    writeValueToCSV(amount, 1); // Write to column 2
                    showrequestadmin("Admin Request", "Deposit request for $" + amount + " pending admin approval.");
                    System.out.println("Deposit Amount: " + amount); // Debugging print statement
                }
            }
        });

        withdraw_button.setOnAction(event -> {
            String amount = deposit_withdraw_textfield.getText();
            if (amount == null || amount.trim().isEmpty()) {
                showAlert("Input Error", "Please enter an amount to withdraw.");
            } else {
                boolean confirmed = showConfirmationDialog("Withdraw Confirmation",
                        "Are you sure you want to withdraw $" + amount + "?");
                if (confirmed) {
                    writeValueToCSV(amount, 2); // Write to column 3
                    showrequestadmin("Admin Request", "Withdraw request for $" + amount + " pending admin approval.");
                    System.out.println("Withdraw Amount: " + amount); // Debugging print statement
                }
            }
        }
        );
    }

    private void writeValueToCSV(String value, int column) {
        String csvFile = "src\\1_deliverable\\AprovalSystem.csv";

        try (FileWriter writer = new FileWriter(csvFile, true)) {
            if (column > 0) {
                writer.append(controller.Username);
                writer.append(",");
            }
            for (int i = 1; i <= 3; i++) {
                if (i == column) {
                    writer.append(value);
                }
                if (i < 3) {
                    writer.append(",");
                }
            }
            writer.append("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean showrequestadmin(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
        return alert.getResult() == ButtonType.OK;
    }

    private boolean showConfirmationDialog(String title, String message) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    public void switchtoPriceHistoryScreen(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("1_deliverable\\PriceHistoryScreen.fxml"));
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
            Parent root = FXMLLoader.load(getClass().getResource("1_deliverable\\HomeScreen.fxml"));
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
