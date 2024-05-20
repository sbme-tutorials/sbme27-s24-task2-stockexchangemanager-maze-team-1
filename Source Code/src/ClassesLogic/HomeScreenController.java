package ClassesLogic;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class HomeScreenController {

    @FXML
    private Button UserPricehistory;

    @FXML
    private Button UserScreen2_orderstocks;

    @FXML
    private Button userscreen2_deposit;

    private Stage stage;
    private Parent root;
    @FXML
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
    @FXML
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
