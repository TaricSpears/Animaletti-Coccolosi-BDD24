//visualizza parcelle non pagate: visualizza parcelle afferenti al prop con pagata a false
package gui.RUN.OWNER.Buttons;

import java.sql.ResultSet;
import java.sql.Statement;

import java.sql.SQLException;

import database.MySQLConnect;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

public class ParcelleButton extends Button {
    public ParcelleButton(java.lang.String email) {
        this.setText("Visualizza parcelle non pagate");
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
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Informazione");
                    alert.setHeaderText("Elenco delle parcelle non pagate");
                    java.lang.String s = "";
                    do {
                        s += "ID: " + rs.getInt("id") + " - Importo: " + rs.getDouble("importo") + " - Data: "
                                + rs.getDate("data") + "\n";
                    } while (rs.next());
                    alert.setContentText(s);
                    alert.showAndWait();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
    }

}