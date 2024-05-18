import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import ClassesLogic.Deposit;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ApprovalSystemController implements Initializable {
    @FXML
    private TableView<Deposit> depositRequestsTable;
    @FXML
    private TableColumn<Deposit, String> usernameColumn;
    @FXML
    private TableColumn<Deposit, Double> amountDepositColumn;
    @FXML
    private TableColumn<Deposit, Double> amountWithdrawColumn;
    @FXML
    private TableColumn<Deposit, Button> approveColumn;
    @FXML
    private TableColumn<Deposit, Button> rejectColumn;
    @FXML
    private TableColumn<Deposit, Button> deleteColumn;
    @FXML
    private Button stockmanagement;
    private Stage stage;
    @FXML
    private Button usermanagementbtn;

    @FXML
    public void switchtostockscreen(ActionEvent event) {
        if (event.getSource() == stockmanagement) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("AdminStocksScene.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setTitle("Stock screen section");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void switchtostousermanagement(ActionEvent event) {
        if (event.getSource() == usermanagementbtn) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("AdminScene.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setTitle("Stock screen section");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        amountDepositColumn.setCellValueFactory(new PropertyValueFactory<>("amountDeposit"));
        amountWithdrawColumn.setCellValueFactory(new PropertyValueFactory<>("amountWithdraw"));

        approveColumn.setCellFactory(col -> new TableCell<Deposit, Button>() {
            private final Button approveButton = new Button("Approve");

            {
                approveButton.setOnAction(e -> {
                    Deposit deposit = getTableView().getItems().get(getIndex());
                    handleApproveAction(deposit);
                });
            }

            @Override
            protected void updateItem(Button item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(approveButton);
                }
            }
        });

        rejectColumn.setCellFactory(col -> new TableCell<Deposit, Button>() {
            private final Button rejectButton = new Button("Reject");

            {
                rejectButton.setOnAction(e -> {
                    Deposit deposit = getTableView().getItems().get(getIndex());
                    handleRejectAction(deposit);
                });
            }

            @Override
            protected void updateItem(Button item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(rejectButton);
                }
            }
        });

        depositRequestsTable.setItems(depositRequests());
    }

    private ObservableList<Deposit> depositRequests() {
        ObservableList<Deposit> data = FXCollections.observableArrayList();
        try (BufferedReader br1 = new BufferedReader(new FileReader("src\\AprovalSystem.csv"))) {
            String line1;
            while ((line1 = br1.readLine()) != null) {

                if (line1 == null)
                    break;
                String[] rows1 = line1.split(",");
                String username = "";
                double amountDeposit = 0.0;
                double amountWithdraw = 0.0;
                if (rows1.length >= 1 && rows1[0] != null && !rows1[0].isEmpty()) {
                    username = rows1[0];
                }
                if (rows1.length >= 2 && rows1[1] != null && !rows1[1].isEmpty()) {
                    try {
                        amountDeposit = Double.parseDouble(rows1[1]);
                    } catch (NumberFormatException e) {
                        amountDeposit = 0.0;
                    }
                }
                if (rows1.length >= 3 && rows1[2] != null && !rows1[2].isEmpty()) {
                    try {
                        amountWithdraw = Double.parseDouble(rows1[2]);
                    } catch (NumberFormatException e) {
                        amountWithdraw = 0.0;
                    }
                }
                data.add(new Deposit(username, amountDeposit, amountWithdraw, new Button("Approve"),
                        new Button("Reject")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    private void handleApproveAction(Deposit deposit) {
        String username = deposit.getUsername();
        double amountDeposit = deposit.getAmountDeposit();
        double amountWithdraw = deposit.getAmountWithdraw();
        updateBalance(username, amountDeposit, amountWithdraw);
        removeFromApprovalSystem(deposit);
        depositRequestsTable.getItems().remove(deposit);
    }

    private void handleRejectAction(Deposit deposit) {
        removeFromApprovalSystem(deposit);
        depositRequestsTable.getItems().remove(deposit);
    }

    private void updateBalance(String username, double amountDeposit, double amountWithdraw) {
        try {
            List<String> lines = Files.readAllLines(Paths.get("src\\AprovalSystem.csv"));
            for (int i = 0; i < lines.size(); i++) {
                String[] columns = lines.get(i).split(",");
                if (columns.length > 3 && columns[0].equals(username)) {
                    double currentBalance = Double.parseDouble(columns[3]);
                    double newBalance = currentBalance + amountDeposit - amountWithdraw;
                    columns[3] = String.valueOf(newBalance);
                    lines.set(i, String.join(",", columns));
                    break;
                }
            }
            Files.write(Paths.get("src\\AprovalSystem.csv"), lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void removeFromApprovalSystem(Deposit deposit) {
        try {
            List<String> lines = Files.readAllLines(Paths.get("src\\AprovalSystem.csv"));
            List<String> filteredLines = lines.stream()
                    .filter(line -> {
                        String[] columns = line.split(",");
                        if (columns.length < 3) {
                            return true;
                        }
                        String username = columns[0];
                        double amountDeposit = 0.0;
                        double amountWithdraw = 0.0;
                        try {
                            if (!columns[1].isEmpty()) {
                                amountDeposit = Double.parseDouble(columns[1]);
                            }
                            if (!columns[2].isEmpty()) {
                                amountWithdraw = Double.parseDouble(columns[2]);
                            }
                        } catch (NumberFormatException e) {
                            // Handle the exception if needed
                            return true; // Don't filter out the line if parsing fails
                        }
                        return !(username.equals(deposit.getUsername()) &&
                                amountDeposit == deposit.getAmountDeposit() &&
                                amountWithdraw == deposit.getAmountWithdraw());
                    })
                    .collect(Collectors.toList());
            Files.write(Paths.get("src\\AprovalSystem.csv"), filteredLines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void switchtotradingsession(ActionEvent event) {

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
    public void switchtoApproval(ActionEvent event) {

            try {
                Parent root = FXMLLoader.load(getClass().getResource("ApprovalSystem.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                stage.setScene(new Scene(root));
                stage.show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    public void switchtostockmanege(ActionEvent event) {

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