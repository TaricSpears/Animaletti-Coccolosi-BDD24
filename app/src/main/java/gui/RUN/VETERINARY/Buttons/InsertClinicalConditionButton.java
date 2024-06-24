package gui.RUN.VETERINARY.Buttons;

import java.sql.Connection;
import java.sql.PreparedStatement;

import database.MySQLConnect;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;

public class InsertClinicalConditionButton extends Button {
    public InsertClinicalConditionButton() {
        this.setText("Inserisci condizione clinica");
        this.setOnAction(click -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Inserisci condizione clinica");
            dialog.setHeaderText("Inserisci il nome della nuova condizione clinica");
            dialog.setContentText("Nome:");
            dialog.showAndWait().ifPresent(nome -> {
                if (isValid(nome)) {
                    String query = "INSERT INTO condizione_clinica (Nome) VALUES (?)";
                    try {
                        Connection connection = MySQLConnect.getConnection();
                        PreparedStatement preparedStatement = connection.prepareStatement(query);
                        preparedStatement.setString(1, nome);
                        preparedStatement.executeUpdate();
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Inserimento condizione clinica");
                        alert.setHeaderText("Inserimento condizione clinica");
                        alert.setContentText("Condizione clinica inserita correttamente");
                        alert.showAndWait();
                    } catch (Exception e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Errore");
                        alert.setHeaderText("Errore");
                        alert.setContentText("Errore durante l'inserimento della condizione clinica");
                        alert.showAndWait();
                        e.printStackTrace();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Errore");
                    alert.setHeaderText("Errore");
                    alert.setContentText("Nome condizione clinica non valido");
                    alert.showAndWait();
                }
            });

        });
    }

    private boolean isValid(String nomeCond) {
        boolean flag = false;
        if (nomeCond.length() > 0 && nomeCond.length() < 50) {
            String query = "SELECT * FROM condizione_clinica WHERE Nome = ?";
            try {
                Connection connection = MySQLConnect.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, nomeCond);
                if (preparedStatement.executeQuery().next()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Errore");
                    alert.setHeaderText("Errore");
                    alert.setContentText("Condizione clinica già esistente");
                    alert.showAndWait();
                } else {
                    flag = true;
                }
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Errore");
                alert.setHeaderText("Errore");
                alert.setContentText("Errore durante la verifica dell'esistenza della condizione clinica");
                alert.showAndWait();
                e.printStackTrace();
            }
        }
        return flag;
    }
}