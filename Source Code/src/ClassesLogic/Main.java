package ClassesLogic;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static String file = "src\\1_deliverable\\Users_information.csv";
    public static String StockFile ="src\\1_deliverable\\StocksFile.csv";
    @Override
    public void start(Stage primarystage) throws Exception {
        Parent root1 = FXMLLoader.load(getClass().getResource("1_deliverable\\fxmldocument.fxml"));
        primarystage.setTitle("login form");

        primarystage.setScene(new Scene(root1, 330, 550));

        primarystage.setFullScreen(false);
        primarystage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
