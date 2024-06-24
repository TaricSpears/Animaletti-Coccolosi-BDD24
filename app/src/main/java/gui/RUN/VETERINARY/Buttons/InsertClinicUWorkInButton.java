package gui.RUN.VETERINARY.Buttons;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import database.MySQLConnect;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;

public class InsertClinicUWorkInButton extends Button {
    public InsertClinicUWorkInButton(String email) {
        this.setText("Inserisci l'ambulatorio in cui lavori");
        this.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Inserisci ambulatorio in cui lavori");
            dialog.setHeaderText("Inserisci il nome dell'ambulatorio in cui lavori");
            dialog.setContentText("Nome:");
            dialog.showAndWait().ifPresent(nome -> {
                if (!nomeAmbIsValid(nome)) { // SE L AMBULATORIO NON ESISTE O IL NOME NON E VALIDO
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle(null);
                    alert.setHeaderText("Errore");
                    alert.setContentText(
                            "Il nome dell'ambulatorio deve essere compreso tra 1 e 50 caratteri e dev'essere gia' presente nel database");
                    alert.setResizable(true);
                    alert.showAndWait();
                    return;
                } else { // VERIFICA CHE LO USER NON LAVORI GIA NELL AMBULATORIO
                    String query0 = "SELECT c.* FROM collocazione c WHERE c.email = ? AND c.nome = ?";
                    String query1 = "INSERT INTO collocazione (email, nome) VALUES (?, ?)";
                    try {
                        PreparedStatement stmt0 = MySQLConnect.getConnection().prepareStatement(query0);
                        stmt0.setString(1, email);
                        stmt0.setString(2, nome);
                        ResultSet rs = stmt0.executeQuery();
                        if (rs.isBeforeFirst()) {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle(null);
                            alert.setHeaderText("Conferma");
                            alert.setContentText("L'ambulatorio e' gia' presente tra quelli in cui lavori");
                            alert.showAndWait();
                            return;
                        } else {
                            PreparedStatement stmt1 = MySQLConnect.getConnection().prepareStatement(query1);
                            stmt1.setString(1, email);
                            stmt1.setString(2, nome);
                            stmt1.executeUpdate();
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle(null);
                            alert.setHeaderText("Conferma");
                            alert.setContentText("Ambulatorio inserito correttamente tra quelli in cui lavori");
                            alert.showAndWait();
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
        });
    }

    private boolean nomeAmbIsValid(String nome) {
        String query = "SELECT a.nome FROM ambulatorio a WHERE a.nome = ?";
        boolean flag = false;
        try {
            PreparedStatement stmt = MySQLConnect.getConnection().prepareStatement(query);
            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();
            if (rs.isBeforeFirst()) {
                flag = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return nome.length() > 0 && nome.length() <= 50 && flag;
    }
}