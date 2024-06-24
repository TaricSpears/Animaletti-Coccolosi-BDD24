package gui.RUN.COMMONS;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import database.MySQLConnect;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;

public class InsertNewZoneButton extends Button {
    public InsertNewZoneButton() {
        this.setText("Inserisci una nuova zona");
        this.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Inserisci zona");
            dialog.setHeaderText("Inserisci il nome della zona");
            dialog.setContentText("Nome:");
            dialog.showAndWait().ifPresent(nome -> {
                if (nome.length() == 0 || nome.length() > 50) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle(null);
                    alert.setHeaderText("Errore");
                    alert.setContentText("Il nome della zona deve essere compreso tra 1 e 50 caratteri");
                    alert.showAndWait();
                    return;
                } else {
                    String query0 = "SELECT z.nome FROM zona z WHERE z.nome = ?";
                    String query1 = "INSERT INTO zona (nome) VALUES (?)";
                    try {
                        PreparedStatement stmt0 = MySQLConnect.getConnection().prepareStatement(query0);
                        stmt0.setString(1, nome);
                        ResultSet rs = stmt0.executeQuery();
                        if (rs.isBeforeFirst()) {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle(null);
                            alert.setHeaderText("Conferma");
                            alert.setContentText("La zona esiste gia'");
                            alert.showAndWait();
                            return;
                        } else {
                            PreparedStatement stmt1 = MySQLConnect.getConnection().prepareStatement(query1);
                            stmt1.setString(1, nome);
                            stmt1.executeUpdate();
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle(null);
                            alert.setHeaderText("Conferma");
                            alert.setContentText("Zona inserita correttamente");
                            alert.showAndWait();
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });

        });
    }
}