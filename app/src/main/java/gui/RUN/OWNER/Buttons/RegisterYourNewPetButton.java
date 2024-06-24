package gui.RUN.OWNER.Buttons;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.MySQLConnect;
import gui.Tab;

// Create a new class called LoginTab built like InitialTab which asks for email and password.
// it must also have a button to login and a button to go back to InitialTab.

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

import java.time.LocalDate;
import java.time.LocalDateTime;

public class RegisterYourNewPetButton extends Button {

    public RegisterYourNewPetButton(final Stage primaryStage, final String email, final Tab previousTab) {
        this.setText("Registra un nuovo animale");
        this.setOnAction(action -> {
            // Create a VBox layout
            VBox root = new VBox();
            root.setAlignment(Pos.CENTER);
            root.setSpacing(20);

            // Create a title
            Text title = new Text("Animaletti Coccolosi");
            title.setFont(Font.font(24));

            // Create nome field
            TextField nomeField = new TextField();
            nomeField.setPromptText("Nome");

            // Create peso field
            TextField pesoField = new TextField();
            pesoField.setPromptText("Peso");

            // Create specie field
            TextField nomeSpecieField = new TextField();
            nomeSpecieField.setPromptText("Nome specie");

            // Create date of birth field
            DatePicker dobField = new DatePicker();
            dobField.setPromptText("Data_di_nascita");

            // Create login button
            Button inserisciButton = new Button("Inserisci");
            inserisciButton.setOnAction(e -> {
                String nome = nomeField.getText();
                String peso = pesoField.getText();
                String nomeSpecie = nomeSpecieField.getText();
                LocalDate dobDate = dobField.getValue();
                if (isValid(nome, peso, dobDate) && isNomeSpecieValid(nomeSpecie)) {
                    // inserisci il nuovo animale
                    String dob = dobDate.toString();
                    String query = "INSERT INTO animale (Nome, Peso, Data_di_Nascita, APP_Nome, Email) VALUES (?, ?, ?, ?, ?)";
                    try {
                        Connection connection = MySQLConnect.getConnection();
                        PreparedStatement preparedStatement = connection.prepareStatement(query);
                        preparedStatement.setString(1, nome);
                        preparedStatement.setString(2, peso);
                        preparedStatement.setString(3, dob);
                        preparedStatement.setString(4, nomeSpecie);
                        preparedStatement.setString(5, email);
                        preparedStatement.executeUpdate();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    // crea la cartella clinica dell'animale
                    String query2 = "SELECT a.Codice_Identificativo FROM animale a ORDER BY a.Codice_Identificativo DESC LIMIT 1";
                    try {
                        Connection connection = MySQLConnect.getConnection();
                        PreparedStatement preparedStatement2 = connection.prepareStatement(query2);
                        ResultSet rs = preparedStatement2.executeQuery();
                        rs.next();
                        String codice = rs.getString(1);
                        String query3 = "INSERT INTO cartella_clinica (Codice_Identificativo, Data_di_Creazione) VALUES (?, ?)";
                        PreparedStatement preparedStatement3 = connection.prepareStatement(query3);
                        preparedStatement3.setString(1, codice);
                        preparedStatement3.setString(2, LocalDateTime.now().toString());
                        preparedStatement3.executeUpdate();
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Successo");
                        alert.setHeaderText(null);
                        alert.setContentText("Animale inserito con successo");
                        alert.showAndWait();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });

            // Create back button
            Button backButton = new Button("Back");
            backButton.setOnAction(e -> {
                try {
                    previousTab.show();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            });

            // Add components to the layout
            root.getChildren().addAll(title, nomeField, pesoField, nomeSpecieField, dobField, inserisciButton,
                    backButton);

            Scene scene = new Scene(root, 400, 300);
            primaryStage.setTitle("Animaletti Coccolosi");
            primaryStage.setScene(scene);
            primaryStage.show();
        });

    }

    private boolean isNomeSpecieValid(final String nomeSpecie) {
        boolean flag = false;
        if (nomeSpecie.length() > 0 && nomeSpecie.length() <= 20) {
            String query = "SELECT * FROM specie WHERE Nome = ?";
            try {
                Connection connection = MySQLConnect.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, nomeSpecie);
                ResultSet rs = preparedStatement.executeQuery();
                if (!rs.next()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setHeaderText(null);
                    alert.setContentText("Nome specie non esistente");
                    alert.showAndWait();
                } else {
                    flag = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Lunghezza nome specie non valida");
            alert.showAndWait();
        }
        return flag;
    }

    private boolean isValid(final String nome, final String peso, final LocalDate dob) {
        boolean flag = false;
        if (nome.length() > 0 && nome.length() <= 20 && peso.length() > 0 && peso.length() <= 3
                && peso.matches("[0-9]+") && dob != null) {
            flag = true;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Lunghzza nome, peso non valide o data di nascita non inserita correttamente");
            alert.showAndWait();
        }
        return flag;
    }
}