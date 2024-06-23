package gui.RUN.VETERINARY.Buttons;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import database.MySQLConnect;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;

public class InsertExerciseButton extends Button {
    public InsertExerciseButton() {
        this.setText("Inserisci esercizio");
        this.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Inserisci esercizio");
            dialog.setHeaderText("Inserisci il nome dell'esercizio");
            dialog.setContentText("Nome:");
            dialog.showAndWait().ifPresent(nome -> {
                if (nome.length() == 0 || nome.length() > 20) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle(null);
                    alert.setHeaderText("Errore");
                    alert.setContentText("Il nome dell'esercizio deve essere compreso tra 1 e 20 caratteri");
                    alert.showAndWait();
                    return;
                } else {
                    String query1 = "SELECT e.nome FROM esercizio e WHERE e.nome = ?";
                    String query2 = "INSERT INTO esercizio (nome, descrizione) VALUES (?, ?)";
                    try {
                        PreparedStatement stmt1 = MySQLConnect.getConnection().prepareStatement(query1);
                        stmt1.setString(1, nome);
                        ResultSet rs = stmt1.executeQuery();
                        if (rs.isBeforeFirst()) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle(null);
                            alert.setHeaderText("Errore");
                            alert.setContentText("L'esercizio esiste gia'");
                            alert.showAndWait();
                            return;
                        } else {
                            TextInputDialog descDialog = new TextInputDialog();
                            descDialog.setTitle("Inserisci descrizione esercizio");
                            descDialog.setHeaderText("Inserisci descrizione esercizio");
                            descDialog.setContentText("Descrizione:");
                            descDialog.showAndWait().ifPresent(descrizione -> {
                                if (descrizione.length() == 0 || descrizione.length() > 50) {
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setTitle(null);
                                    alert.setHeaderText("Errore");
                                    alert.setContentText(
                                            "La descrizione dell'esercizio deve essere compresa tra 1 e 50 caratteri");
                                    alert.showAndWait();
                                    return;
                                } else {
                                    try {
                                        PreparedStatement stmt2 = MySQLConnect.getConnection().prepareStatement(query2);
                                        stmt2.setString(1, nome);
                                        stmt2.setString(2, descrizione);
                                        stmt2.executeUpdate();
                                        stmt2.close();
                                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                        alert.setTitle(null);
                                        alert.setHeaderText("Successo");
                                        alert.setContentText("Esercizio inserito correttamente");
                                        alert.showAndWait();
                                        return;
                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                    }
                                }
                            });
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
        });
    }
}
