package gui.START;

import database.*;

//import the classes necessery to work with the database

// create a class called RegistrationTab built like LoginTab which asks for name, surname,
// email, password, date of birth. it must have a register button and a back button to go back to InitialTab.

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class RegistrationTab {
    MySQLConnect connect = new MySQLConnect();

    private final Stage primaryStage;

    public RegistrationTab(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void show() {
        // Create a VBox layout
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(10);

        // Create a title
        Text title = new Text("Animaletti Coccolosi");
        title.setFont(Font.font(24));

        // Create name field
        TextField nameField = new TextField();
        nameField.setPromptText("Nome");

        // Create surname field
        TextField surnameField = new TextField();
        surnameField.setPromptText("Cognome");

        // Create email field
        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        // Create password field
        TextField passwordField = new TextField();
        passwordField.setPromptText("Password");

        // Create date of birth field
        DatePicker dobField = new DatePicker();
        dobField.setPromptText("Data_di_nascita");

        // Create register button
        Button registerButton = new Button("Register");
        registerButton.setOnAction(e -> {
            // insert datas into the database
            // if the registration is successful, show the LoginTab
            // if the registration is not successful, show an error message
            boolean registrationSuccessful = false;
            LocalDateTime Data_di_iscrizione = LocalDateTime.now();
            System.out.println(Data_di_iscrizione);

            if (nameField.getText().isEmpty() || nameField.getText().length() > 32 || surnameField.getText().isEmpty()
                    || surnameField.getText().length() > 32 || emailField.getText().isEmpty()
                    || emailField.getText().length() > 25
                    || passwordField.getText().isEmpty() || dobField.getValue() == null) {
                // alert with error message

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Errore");
                alert.setHeaderText("Registrazione fallita");
                alert.setContentText("Inserire tutti i campi");
                alert.showAndWait();
                return;
            }
            try {

                PreparedStatement preparedStatement = MySQLConnect.getConnection().prepareStatement(
                        "INSERT INTO utente (Nome, Cognome, Email, Password, Data_di_nascita,Data_di_iscrizione) VALUES (?, ?, ?, ?, ?,?)");
                preparedStatement.setString(1, nameField.getText());
                preparedStatement.setString(2, surnameField.getText());
                preparedStatement.setString(3, emailField.getText());
                preparedStatement.setString(4, passwordField.getText());
                preparedStatement.setString(5, dobField.getValue().toString());
                preparedStatement.setString(6, Data_di_iscrizione.toString());
                preparedStatement.executeUpdate();
                registrationSuccessful = true;

            } catch (SQLException ex) {
                ex.printStackTrace();
                registrationSuccessful = false;
            }

            if (registrationSuccessful) {
                LoginTab loginTab = new LoginTab(primaryStage);
                loginTab.show();
            } else {
                Text errorMessage = new Text("Registration failed");
                root.getChildren().add(errorMessage);
            }

        });

        // Create back button
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            InitialTab initialTab = new InitialTab(primaryStage);
            initialTab.show();
        });

        // Add components to the layout
        root.getChildren().addAll(title, nameField, surnameField, emailField, passwordField, dobField, registerButton,
                backButton);

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setTitle("Animaletti Coccolosi");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}