package gui.START;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import database.MySQLConnect;

// Create a new class called LoginTab built like InitialTab which asks for email and password.
// it must also have a button to login and a button to go back to InitialTab.

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginTab {

    private final Stage primaryStage;

    public LoginTab(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void show() {
        // Create a VBox layout
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(20);

        // Create a title
        Text title = new Text("Animaletti Coccolosi");
        title.setFont(Font.font(24));

        // Create email field
        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        // Create password field
        TextField passwordField = new TextField();
        passwordField.setPromptText("Password");

        // Create login button
        Button loginButton = new Button("Login");
        loginButton.setOnAction(e -> {
            if (isLoginValid(emailField.getText(), passwordField.getText())) {
                LoginRolePicker loginRolePicker = new LoginRolePicker(primaryStage, emailField.getText());
                loginRolePicker.show();
            }
        });

        // Create back button
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            InitialTab initialTab = new InitialTab(primaryStage);
            initialTab.show();
        });

        // Add components to the layout
        root.getChildren().addAll(title, loginButton, emailField, passwordField, backButton);

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setTitle("Animaletti Coccolosi");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private boolean isLoginValid(String email, String password) {
        if (isEmailValid(email) && isPasswordValid(password)) {
            String query = "SELECT u.Password FROM utente u WHERE Email = ?";
            try {
                Connection connection = MySQLConnect.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, email);
                ResultSet rs = preparedStatement.executeQuery();
                if (!rs.next()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setHeaderText(null);
                    alert.setContentText("Invalid email");
                    alert.showAndWait();
                    return false;
                } else {
                    String dbPassword = rs.getString("Password");
                    if (!dbPassword.equals(password)) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Warning");
                        alert.setHeaderText(null);
                        alert.setContentText("Invalid password");
                        alert.showAndWait();
                        return false;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Invalid email or password length");
            alert.showAndWait();
            return false;
        }
    }

    private boolean isPasswordValid(final String password) {
        return password.length() > 0 && password.length() <= 15;
    }

    private boolean isEmailValid(final String email) {
        return email.length() > 0 && email.length() <= 25;
    }
}