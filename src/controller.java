import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class controller implements Initializable {

    public static String Username;
    public static String userFilename;
    public static  String transactionFilename;
    @FXML
    private CheckBox login_checkBox;
    @FXML
    private AnchorPane login_form;
    @FXML
    private Button login_login;
    @FXML
    private PasswordField login_password;
    @FXML
    private Hyperlink login_registerhere;
    @FXML
    private TextField login_showpassword;
    @FXML
    private TextField login_username;
    @FXML
    private AnchorPane main_form;
    @FXML
    private CheckBox register_checkBox;
    @FXML
    private TextField register_email;
    @FXML
    private AnchorPane register_form;
    @FXML
    private Hyperlink register_loginhere;
    @FXML
    private PasswordField register_password;
    @FXML
    private TextField register_showpassword;
    @FXML
    private Button register_signup;
    @FXML
    private TextField register_username;
    private Stage stage;
    private Parent root;
    private boolean userFound;
    private Alertmessage alert = new Alertmessage();

    public controller() throws FileNotFoundException {
    }

    public static void createUserCSV(String username) {
        // Directory where user CSV files will be stored
        String directoryPath = "src/UserStocks/";
        String transactionPath = "src/UserTransaction/";
        String transactionFilename = transactionPath + username + ".csv";

        // Generate the filename using the user's username
        String userFilename = directoryPath + username + ".csv";

        try {
            // Create the directory if it doesn't exist
            File directory = new File(directoryPath);
            File transactionDirectory = new File(transactionPath);

            if (!directory.exists()) {
                directory.mkdirs(); // Create parent directories if they don't exist
            }
            if (!transactionDirectory.exists()) {
                transactionDirectory.mkdirs(); // Create parent directories if they don't exist
            }

            // Create the user CSV file
            File userFile = new File(userFilename);
            if (userFile.createNewFile()) {
                System.out.println("CSV file created for user: " + username);
            } else {
                System.out.println("CSV file already exists for user: " + username);
            }

            // Create the transaction CSV file
            File transactionFile = new File(transactionFilename);
            if (transactionFile.createNewFile()) {
                System.out.println("Transaction CSV file created for user: " + username);
            } else {
                System.out.println("Transaction CSV file already exists for user: " + username);
            }
        } catch (IOException e) {
            System.err.println("Error creating CSV files for user: " + username);
            e.printStackTrace();
        }
    }

    // login button action
    public void registerAccount() {

        if (register_email.getText().isEmpty() || register_username.getText().isEmpty()
                || register_password.getText().isEmpty()) {
            alert.errorMessage("Please fill all blank fields");
        } else {

            alert.confirmationMessage("successful register");
        }

    }
    public void registershowpassword() {
        if (register_checkBox.isSelected()) {
            register_showpassword.setText(register_password.getText());
            register_showpassword.setVisible(true);
            register_password.setVisible(false);
        } else {
            register_showpassword.setText(register_password.getText());
            register_showpassword.setVisible(false);
            register_password.setVisible(true);
        }
    }

    // show password of login form
    public void login_showpassword() {
        if (login_checkBox.isSelected()) {
            login_showpassword.setText(login_password.getText());
            login_showpassword.setVisible(true);
            login_password.setVisible(false);
        } else {
            login_showpassword.setText(login_password.getText());
            login_showpassword.setVisible(false);
            login_password.setVisible(true);
        }
    }

    public void switchform(ActionEvent event) {
        if (event.getSource() == login_registerhere) {
            login_form.setVisible((false));
            register_form.setVisible(true);
        } else if (event.getSource() == register_loginhere) {
            login_form.setVisible((true));
            register_form.setVisible(false);
        }
    }

    public void onlogin(ActionEvent event) throws IOException {

        if (login_username.getText().equals("z") && login_password.getText().equals("123")) {
            root = FXMLLoader.load(getClass().getResource("AdminScene.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            alert.confirmationMessage("you are now in admin screen");
            Username = login_username.getText();
        } else {
            String line;
            try (BufferedReader reader = new BufferedReader(new FileReader(Main.file))) {
                while ((line = reader.readLine()) != null) {
                    String[] row = line.split(",");
                    if (row.length >= 3) { // Ensure row has at least 3 elements (username, email, password)
                        String usernameFromFile = row[0]; // Trim to remove leading/trailing whitespace
                        String passwordFromFile = row[2];
                        String usernameInput = login_username.getText();
                        String passwordInput = login_password.getText();
                        String showedpasswordInput = login_showpassword.getText();
                        if ((usernameFromFile.equals(usernameInput) && (passwordFromFile.equals(passwordInput) || passwordFromFile.equals(showedpasswordInput))) ||
                                (usernameFromFile.equals("\"" + usernameInput + "\"") && ((passwordFromFile.equals("\"" + passwordInput + "\""))
                                        || (passwordFromFile.equals("\"" + showedpasswordInput + "\""))))) {
                            String directoryPath = "src\\UserStocks\\";
                            userFilename = directoryPath + usernameInput + ".csv";
                            String transactionPath = "src/UserTransaction/";
                            transactionFilename = transactionPath + usernameInput + ".csv";
                            userFound = true;
                            Username = login_username.getText();
                            alert.confirmationMessage("successful login");
                            break;
                        }
                    } else {
                        System.err.println("Invalid row in CSV file: " + line);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (userFound) {
                root = FXMLLoader.load(getClass().getResource("HomeScreen.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } else {
                alert.errorMessage("Invalid username or password");
            }

        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {}

    @FXML
    public void registerAccount(ActionEvent event) {
        String emailInput = register_email.getText();
        String passwordInput = register_password.getText();
        String usernameInput = register_username.getText();
//        String filePath = "path_to_your_csv_file.csv"; // Specify the path to your CSV file

        try (PrintWriter writer = new PrintWriter(new FileWriter(Main.file, true))) {
            if (!emailInput.isEmpty() && !usernameInput.isEmpty() && !passwordInput.isEmpty()) {
                // Write user information to the CSV file
                writer.println(emailInput + "," + usernameInput + "," + passwordInput + "," + 1000000 + "," + 0);
                System.out.println("New user added successfully.");
                createUserCSV(usernameInput);
                login_form.setVisible(true);
                register_form.setVisible(false);
            } else {
                System.out.println("Please fill in all fields.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//    @FXML
//    public void switchtoPriceHistoryScreen(ActionEvent event) {
//
//        // Get the current time
//        LocalTime currentTime = LocalTime.now();
//
//        // Check if it's within the trading hours (6am to 6pm)
////        if (currentTime.isAfter(LocalTime.of(0, 0)) && currentTime.isBefore(LocalTime.of(24, 0))) {
//            try {
//                Parent root = FXMLLoader.load(getClass().getResource("PriceHistoryScreen.fxml"));
//                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//
//                stage.setTitle("Price History");
//                stage.setScene(new Scene(root));
//                stage.show();
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
////        } else {
////            // If trading is closed, print a message
////            System.out.println("Trading is closed.");
////            System.err.println("Trading is closed.");
////
////        }
//    }
}