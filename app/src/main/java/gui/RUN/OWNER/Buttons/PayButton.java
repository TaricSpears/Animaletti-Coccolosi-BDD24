package gui.RUN.OWNER.Buttons;

import java.sql.ResultSet;
import java.sql.Statement;

import java.sql.SQLException;

import database.MySQLConnect;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;

public class PayButton extends Button {
    public PayButton(String email) {
        this.setText("Paga");
        this.setOnAction(e -> {
            try {
                Statement stmt = MySQLConnect.getConnection().createStatement();
                ResultSet rs = stmt.executeQuery(
                        "SELECT * FROM pagamento WHERE Pagata = 0 AND Email = ' " + email + " ' ");
                if (!rs.next()) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Informazione");
                    alert.setHeaderText("Nessuna parcella non pagata");
                    alert.showAndWait();
                } else {
                    TextInputDialog dialog = new TextInputDialog();
                    dialog.setTitle("Pagamento");
                    dialog.setHeaderText("Inserisci l'ID della parcella da pagare");
                    dialog.setContentText("ID:");
                    dialog.showAndWait();
                    int id = Integer.parseInt(dialog.getResult());
                    stmt.executeUpdate("UPDATE pagamento SET Pagata = 1 WHERE id = " + id);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Informazione");
                    alert.setHeaderText("Pagamento effettuato");
                    alert.showAndWait();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
    }
}
