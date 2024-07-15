//visualizza parcelle non pagate: visualizza parcelle afferenti al prop con pagata a false
package gui.RUN.OWNER.Buttons;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import java.sql.SQLException;

import database.MySQLConnect;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import java.util.HashSet;
import java.util.Set;

public class ParcelleButton extends Button {
    public ParcelleButton(final String email) {
        this.setText("Visualizza parcelle non pagate");
        this.setOnAction(e -> {
            try {
                Set<Integer> codParcella = new HashSet<>();
                Statement stmt = MySQLConnect.getConnection().createStatement();
                ResultSet rs = stmt.executeQuery(
                        "SELECT * FROM pagamento WHERE Pagata = 0 AND Email = '" + email + "'");
                if (!rs.next()) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Informazione");
                    alert.setHeaderText("Nessuna parcella non pagata");
                    alert.showAndWait();
                } else {
                    while (rs.next()) {
                        codParcella.add(rs.getInt("CodParcella"));
                    }
                    String query = "SELECT * FROM parcella WHERE CodParcella = ?";
                    PreparedStatement preparedStatement = MySQLConnect.getConnection().prepareStatement(query);
                    String text = "Le parcelle non pagate sono:\n";
                    while (!codParcella.isEmpty()) {
                        int cod = codParcella.iterator().next();
                        preparedStatement.setInt(1, cod);
                        ResultSet rs1 = preparedStatement.executeQuery();
                        if (rs1.next()) {
                            text += "Codice parcella: " + rs1.getInt("CodParcella") + "\n";
                            text += "Importo: " + rs1.getString("Importo") + "\n";
                            text += "Descrizione: " + rs1.getString("Descrizione") + "\n";
                            text += "Email: " + rs1.getString("Email") + "\n\n";
                        }
                        codParcella.remove(cod);
                    }
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Informazione");
                    alert.setHeaderText("Parcelle non pagate");
                    alert.setContentText(text);
                    alert.setResizable(true);
                    alert.showAndWait();
                }
            } catch (SQLException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Errore");
                alert.setHeaderText("Errore");
                alert.setContentText("Errore durante la visualizzazione delle parcelle non pagate");
                alert.showAndWait();
                ex.printStackTrace();
            }
        });
    }

}