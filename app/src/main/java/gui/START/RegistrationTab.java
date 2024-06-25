package gui.START;

import database.*;

//import the classes necessery to work with the database

// create a class called RegistrationTab built like LoginTab which asks for name, surname,
// email, password, date of birth. it must have a register button and a back button to go back to InitialTab.

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

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

        // create 3 check boxes for the user to select the role
        // Create a check box for the user to select the propietario role
        // Create a check box for the user to select the veterinario role
        // Create a check box for the user to select the admin role
        CheckBox proprietario = new CheckBox("Proprietario");
        CheckBox veterinario = new CheckBox("Veterinario");
        CheckBox admin = new CheckBox("Admin");

        TextField curriculumField = new TextField();
        curriculumField.setPromptText("Curriculum (opzionale, per i veterinari)");

        // Create register button
        Button registerButton = new Button("Register");
        registerButton.setOnAction(register -> {
            // insert datas into the database
            // if the registration is successful, show the LoginTab
            // if the registration is not successful, show an error message
            LocalDateTime Data_di_iscrizione = LocalDateTime.now();
            boolean proprietarioSelected = proprietario.isSelected();
            boolean veterinarioSelected = veterinario.isSelected();
            boolean adminSelected = admin.isSelected();

            if (veterinarioSelected) {
            }

            if (nameField.getText().isEmpty() || nameField.getText().length() > 32 || surnameField.getText().isEmpty()
                    || surnameField.getText().length() > 32 || !isValid(emailField.getText())
                    || passwordField.getText().isEmpty() || dobField.getValue() == null
                    || (!proprietarioSelected && !veterinarioSelected && !adminSelected)
                    || (veterinarioSelected && curriculumField.getText().isEmpty())
                    || (veterinarioSelected && curriculumField.getText().length() > 200)) {
                // alert with error message

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Errore");
                alert.setHeaderText("Registrazione fallita");
                alert.setContentText("Manca un campo, oppure l'email e' gia' in uso");
                alert.showAndWait();
                return;
            }
            // create an arraylist of 4 booleans
            ArrayList<Boolean> steps = new ArrayList<Boolean>();
            for (int i = 0; i < 4; i++) {
                steps.add(false);
            }
            try {
                PreparedStatement preparedStatement = MySQLConnect.getConnection().prepareStatement(
                        "INSERT INTO utente (Nome, Cognome, Email, Password, Data_di_nascita, Data_di_iscrizione) VALUES (?, ?, ?, ?, ?, ?)");
                preparedStatement.setString(1, nameField.getText());
                preparedStatement.setString(2, surnameField.getText());
                preparedStatement.setString(3, emailField.getText());
                preparedStatement.setString(4, passwordField.getText());
                preparedStatement.setString(5, dobField.getValue().toString());
                preparedStatement.setString(6, Data_di_iscrizione.toString());
                preparedStatement.executeUpdate();
                steps.set(0, true);
                if (proprietarioSelected) {
                    preparedStatement = MySQLConnect.getConnection().prepareStatement(
                            "INSERT INTO proprietario (Email, Bloccato) VALUES (?, 0)");
                    preparedStatement.setString(1, emailField.getText());
                    preparedStatement.executeUpdate();
                    steps.set(1, true);
                }
                if (veterinarioSelected) {
                    preparedStatement = MySQLConnect.getConnection().prepareStatement(
                            "INSERT INTO veterinario (Email, Curriculum) VALUES (?, ?)");
                    preparedStatement.setString(1, emailField.getText());
                    preparedStatement.setString(2, curriculumField.getText());
                    preparedStatement.executeUpdate();
                    steps.set(2, true);
                }
                if (adminSelected) {
                    preparedStatement = MySQLConnect.getConnection().prepareStatement(
                            "INSERT INTO amministratore (Email) VALUES (?)");
                    preparedStatement.setString(1, emailField.getText());
                    preparedStatement.executeUpdate();
                    steps.set(3, true);
                }
            } catch (SQLException ex) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Errore");
                alert.setHeaderText("Registrazione fallita");
                alert.setContentText("Errore durante la registrazione");
                alert.showAndWait();
                if (steps.get(3)) {
                    try {
                        PreparedStatement preparedStatement = MySQLConnect.getConnection().prepareStatement(
                                "DELETE FROM admin WHERE Email = ?");
                        preparedStatement.setString(1, emailField.getText());
                        preparedStatement.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if (steps.get(2)) {
                    try {
                        PreparedStatement preparedStatement = MySQLConnect.getConnection().prepareStatement(
                                "DELETE FROM veterinario WHERE Email = ?");
                        preparedStatement.setString(1, emailField.getText());
                        preparedStatement.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if (steps.get(1)) {
                    try {
                        PreparedStatement preparedStatement = MySQLConnect.getConnection().prepareStatement(
                                "DELETE FROM proprietario WHERE Email = ?");
                        preparedStatement.setString(1, emailField.getText());
                        preparedStatement.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if (steps.get(0)) {
                    try {
                        PreparedStatement preparedStatement = MySQLConnect.getConnection().prepareStatement(
                                "DELETE FROM utente WHERE Email = ?");
                        preparedStatement.setString(1, emailField.getText());
                        preparedStatement.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                ex.printStackTrace();
                return;
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Successo");
            alert.setHeaderText("Registrazione effettuata");
            alert.setContentText("Registrazione effettuata con successo");
            alert.showAndWait();
            InitialTab initialTab = new InitialTab(primaryStage);
            initialTab.show();
        });

        // Create back button
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            InitialTab initialTab = new InitialTab(primaryStage);
            initialTab.show();
        });

        // Add components to the layout
        root.getChildren().addAll(title, nameField, surnameField, emailField, passwordField, dobField, proprietario,
                veterinario, admin, curriculumField,
                registerButton, backButton);

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setTitle("Animaletti Coccolosi");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private boolean isValid(String email) {
        String query = "SELECT * FROM utente WHERE Email = ?";
        try {
            PreparedStatement preparedStatement = MySQLConnect.getConnection().prepareStatement(query);
            preparedStatement.setString(1, email);
            return !preparedStatement.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}