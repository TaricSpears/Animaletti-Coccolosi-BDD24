package gui.RUN.OWNER.Buttons;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import java.sql.SQLException;

import database.MySQLConnect;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;

public class PayButton extends Button {
    public PayButton(final String email) {
        this.setText("Paga");
        this.setOnAction(e -> {
            try {
                Statement stmt = MySQLConnect.getConnection().createStatement();
                ResultSet rs = stmt.executeQuery(
                        "SELECT * FROM pagamento WHERE Pagata = 0 AND Email = '" + email + "'");
                if (!rs.next()) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Informazione");
                    alert.setHeaderText("Nessuna parcella non pagata");
                    alert.showAndWait();
                    return;
                }
            } catch (SQLException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Errore");
                alert.setHeaderText("Errore");
                alert.setContentText("Errore durante la visualizzazione delle parcelle non pagate");
                alert.showAndWait();
                ex.printStackTrace();
            }
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Pagamento");
            dialog.setHeaderText("Inserisci l'ID della parcella da pagare");
            dialog.setContentText("ID:");
            dialog.showAndWait().ifPresent(id -> {
                if (id.length() <= 0 || id.length() > 4 || !id.matches("[0-9]+")) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Errore");
                    alert.setHeaderText("Errore");
                    alert.setContentText("ID dev'essere un intero di massimo 4 cifre!");
                    alert.showAndWait();
                    return;
                }
                String query = "SELECT * FROM pagamento WHERE CodParcella = ? AND Email = ? AND Pagata = 0";
                try {
                    PreparedStatement preparedStatement = MySQLConnect.getConnection().prepareStatement(query);
                    preparedStatement.setString(1, id);
                    preparedStatement.setString(2, email);
                    ResultSet rs = preparedStatement.executeQuery();
                    if (!rs.next()) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Errore");
                        alert.setHeaderText("Errore");
                        alert.setContentText("Parcella non trovata o gia' pagata");
                        alert.showAndWait();
                        return;
                    }
                } catch (Exception ex) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Errore");
                    alert.setHeaderText("Errore");
                    alert.setContentText("Errore durante la ricerca delle parcelle");
                    alert.showAndWait();
                    ex.printStackTrace();
                }
                String query2 = "UPDATE pagamento SET Pagata = 1 WHERE CodParcella = ?";
                try {
                    PreparedStatement preparedStatement = MySQLConnect.getConnection().prepareStatement(query2);
                    preparedStatement.setString(1, id);
                    int result = preparedStatement.executeUpdate();
                    if (result == 0) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Errore");
                        alert.setHeaderText("Errore");
                        alert.setContentText("Errore di aggiornamento della parcella pagata");
                        alert.setResizable(true);
                        alert.showAndWait();
                        return;
                    }
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("OK");
                    alert.setHeaderText("OK");
                    alert.setContentText("Parcella pagata correttamente");
                    alert.showAndWait();
                    return;
                } catch (Exception ex) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Errore");
                    alert.setHeaderText("Errore");
                    alert.setContentText("Errore durante l'aggiornamento della parcella pagata");
                    alert.setResizable(true);
                    alert.showAndWait();
                    return;
                }
            });

        });
    }
}
