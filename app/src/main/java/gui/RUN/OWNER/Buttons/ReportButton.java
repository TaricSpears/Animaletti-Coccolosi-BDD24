//segnalazione utente: inserisci IDmessaggio, nella segnalazione viene usato anche idprop

package gui.RUN.OWNER.Buttons;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import database.MySQLConnect;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;

public class ReportButton extends Button {
    public ReportButton(String email) {
        this.setText("Segnala utente");
        this.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Segnalazione");
            dialog.setHeaderText("Inserisci l'ID del messaggio da segnalare");
            dialog.setContentText("ID:");
            dialog.showAndWait().ifPresent(id -> {
                if (id.length() <= 0 || id.length() > 10) {
                    new Alert(Alert.AlertType.ERROR, "ID non valido o troppo lungo").showAndWait();
                } else {
                    try {
                        Integer.parseInt(id);
                    } catch (NumberFormatException ex) {
                        new Alert(Alert.AlertType.ERROR, "ID non valido").showAndWait();
                        return;
                    }
                    int idInt = Integer.parseInt(id);
                    TextInputDialog dialog2 = new TextInputDialog();
                    dialog2.setTitle("Segnalazione");
                    dialog2.setHeaderText("Inserisci il motivo della segnalazione");
                    dialog2.setContentText("Motivo:");
                    dialog2.showAndWait();
                    String motivo = dialog2.getResult();
                    if (motivo.length() <= 0 || motivo.length() > 200) {
                        new Alert(Alert.AlertType.ERROR, "Motivo non valido o troppo lungo").showAndWait();
                    } else {
                        String query1 = "SELECT * FROM messaggio WHERE ID_messaggio = ?";
                        try {
                            PreparedStatement stmt1 = MySQLConnect.getConnection().prepareStatement(query1);
                            stmt1.setInt(1, idInt);
                            if (!stmt1.executeQuery().next()) {
                                new Alert(Alert.AlertType.ERROR, "ID messaggio non valido").showAndWait();
                                return;
                            }
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                        String query = "INSERT INTO segnalazione (Motivazione, Email, ID_messaggio) VALUES (?, ?, ?)";
                        try {
                            PreparedStatement stmt = MySQLConnect.getConnection().prepareStatement(query);
                            stmt.setString(1, motivo);
                            stmt.setString(2, email);
                            stmt.setInt(3, idInt);
                            stmt.executeUpdate();
                            new Alert(Alert.AlertType.INFORMATION, "Segnalazione effettuata").showAndWait();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            });

        });
    }
}
