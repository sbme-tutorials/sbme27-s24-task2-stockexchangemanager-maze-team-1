package ClassesLogic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public abstract class User {
    private SimpleStringProperty username;
    private SimpleStringProperty password;
    private SimpleStringProperty email;

    List<RegularUser> Users = new ArrayList<>();

    public User(String username, String password, String email) {
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
        this.email = new SimpleStringProperty(email);

    }

    // Getters and Setters
    public String getUsername() {
        return username.get();
    }

    public void setUsername(String username) {
        this.username = new SimpleStringProperty(username);
    }

    public String getPassword() {
        return password.get();
    }

    public void setPassword(String password) {
        this.password = new SimpleStringProperty(password);
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email = new SimpleStringProperty(email);
    }

    public void createUser(RegularUser user) {
        Users.add(user);
        System.out.println(" User " + user.getUsername() + " created successfully");
    }

    //
    private static final String CSV_FILE = "src\\1_deliverable\\Users_information.csv";

    public static void createUser(User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE, true))) {
            writer.write(user.getEmail() + "," + user.getUsername() + "," + user.getPassword() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // public static List<User> retrieveUsers() {
    // List<User> users = new ArrayList<>();
    // try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE))) {
    // String line;
    // while ((line = reader.readLine()) != null) {
    // String[] data = line.split(",");
    // User user = new User(Integer.parseInt(data[0]), data[1], data[2]);
    // users.add(user);
    // }
    // } catch (IOException e) {
    // e.printStackTrace();
    // }
    // return users;
    // }

    // public static void updateUser(int userId, User updatedUser) {
    // List<User> users = retrieveUsers();
    // for (User user : users) {
    // if (user.getEmail() == userEmail) {
    // user.setUsername(updatedUser.getUsername());
    // user.setEmail(updatedUser.getEmail());
    // break;
    // }
    // }
    // writeUsers(users);
    // }

    // public static void deleteUser(int userId) {
    // List<User> users = retrieveUsers();
    // users.removeIf(user -> user.getEmail() == userEmail);
    // writeUsers(users);
    // }

    // private static void writeUsers(List<User> users) {
    // try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE))) {
    // for (User user : users) {
    // writer.write(user.getPassword() + "," + user.getUsername() + "," +
    // user.getEmail() + "\n");
    // }
    // } catch (IOException e) {
    // e.printStackTrace();
    // }
    // }
  
}
