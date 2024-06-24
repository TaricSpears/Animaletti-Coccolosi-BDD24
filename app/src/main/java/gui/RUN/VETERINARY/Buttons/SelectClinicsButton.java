package gui.RUN.VETERINARY.Buttons;

import java.sql.ResultSet;
import java.sql.Statement;

import database.MySQLConnect;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

public class SelectClinicsButton extends Button {
    public SelectClinicsButton() {
        this.setText("Visualizza ambulatori");
        this.setOnAction(e -> {
            try {
                Statement stmt = MySQLConnect.getConnection().createStatement();
                String query = "SELECT * FROM ambulatorio";
                ResultSet rs = stmt.executeQuery(query);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(null);
                alert.setHeaderText("Tabella ambulatori");
                String content = "";
                while (rs.next()) {
                    content += rs.getString("Nome") + "\t\t" + rs.getString("Ind_via") + " "
                            + rs.getString("Ind_Numero_Civico") + rs.getString("Descrizione") + "\n";
                }
                alert.setContentText(content);
                alert.setResizable(true);
                alert.showAndWait();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }
}
