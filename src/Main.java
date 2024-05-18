import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static String file = "src\\Users_information.csv";
    public static String StockFile ="src\\StocksFile.csv";
    @Override
    public void start(Stage primarystage) throws Exception {
        Parent root1 = FXMLLoader.load(getClass().getResource("fxmldocument.fxml"));
        primarystage.setTitle("login form");

        primarystage.setScene(new Scene(root1, 330, 550));

        primarystage.setFullScreen(false);
        primarystage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
