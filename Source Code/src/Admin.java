import javafx.beans.property.*;

public class Admin {
    private final StringProperty email = new SimpleStringProperty();
    private final StringProperty username = new SimpleStringProperty();
    private final StringProperty password = new SimpleStringProperty();
    private final DoubleProperty balance = new SimpleDoubleProperty();
    private final IntegerProperty premium = new SimpleIntegerProperty();

    public Admin(String email, String username, String password, double balance, int premium) {
        setEmail(email);
        setUsername(username);
        setPassword(password);
        setBalance(balance);
        setPremium(premium);
    }

    // Getters and setters for email
    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    // Getters and setters for username
    public String getUsername() {
        return username.get();
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    // Getters and setters for password
    public String getPassword() {
        return password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    // Getters and setters for balance
    public double getBalance() {
        return balance.get();
    }

    public DoubleProperty balanceProperty() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance.set(balance);
    }

    // Getters and setters for premium
    public int getPremium() {
        return premium.get();
    }

    public IntegerProperty premiumProperty() {
        return premium;
    }

    public void setPremium(int premium) {
        this.premium.set(premium);
    }
}
