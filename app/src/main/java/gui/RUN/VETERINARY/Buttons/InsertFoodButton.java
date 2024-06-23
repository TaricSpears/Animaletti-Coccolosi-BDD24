package gui.RUN.VETERINARY.Buttons;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import database.MySQLConnect;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;

public class InsertFoodButton extends Button {
    public InsertFoodButton() {
        this.setText("Inserisci cibo");
        this.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Inserisci cibo");
            dialog.setHeaderText("Inserisci il nome del cibo");
            dialog.setContentText("Nome:");
            dialog.showAndWait().ifPresent(nome -> {
                if (nome.length() == 0 || nome.length() > 20) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle(null);
                    alert.setHeaderText("Errore");
                    alert.setContentText("Il nome del cibo deve essere compreso tra 1 e 20 caratteri");
                    alert.showAndWait();
                    return;
                } else {
                    String query1 = "SELECT c.nome FROM cibo c WHERE c.nome = ?";
                    String query2 = "INSERT INTO cibo (nome) VALUES (?)";
                    try {
                        PreparedStatement stmt1 = MySQLConnect.getConnection().prepareStatement(query1);
                        stmt1.setString(1, nome);
                        ResultSet rs = stmt1.executeQuery();
                        if (rs.isBeforeFirst()) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle(null);
                            alert.setHeaderText("Errore");
                            alert.setContentText("Il cibo esiste gia'");
                            alert.showAndWait();
                            return;
                        } else {
                            PreparedStatement stmt2 = MySQLConnect.getConnection().prepareStatement(query2);
                            stmt2.setString(1, nome);
                            stmt2.executeUpdate();
                            stmt2.close();
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle(null);
                            alert.setHeaderText("Successo");
                            alert.setContentText("Cibo inserito correttamente");
                            alert.showAndWait();
                            return;
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
        });
    }
}
