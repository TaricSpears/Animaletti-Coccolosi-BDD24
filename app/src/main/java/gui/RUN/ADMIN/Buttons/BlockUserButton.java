package gui.RUN.ADMIN.Buttons;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import database.MySQLConnect;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;

public class BlockUserButton extends Button {
    public BlockUserButton() {
        this.setText("Blocca Utente");
        this.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Blocca Utente");
            dialog.setHeaderText("Inserisci l'email del proprietario");
            dialog.setContentText("Email:");
            dialog.showAndWait().ifPresent(email -> {
                String query1 = "SELECT p.email FROM proprietario p WHERE p.email = ? AND p.bloccato = 0";
                String query2 = "UPDATE proprietario SET bloccato = 1 WHERE email = ?";
                try {
                    PreparedStatement stmt = MySQLConnect.getConnection().prepareStatement(query1);
                    stmt.setString(1, email);
                    ResultSet rs = stmt.executeQuery();
                    if (!rs.isBeforeFirst()) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle(null);
                        alert.setHeaderText("Errore");
                        alert.setContentText("L'utente non esiste o e' gia' bloccato");
                        alert.showAndWait();
                    } else {
                        stmt = MySQLConnect.getConnection().prepareStatement(query2);
                        stmt.setString(1, email);
                        stmt.executeUpdate();
                    }
                    stmt.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        });
    }
}
