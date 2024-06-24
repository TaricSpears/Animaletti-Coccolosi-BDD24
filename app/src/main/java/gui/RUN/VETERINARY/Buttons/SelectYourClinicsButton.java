package gui.RUN.VETERINARY.Buttons;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import database.MySQLConnect;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

public class SelectYourClinicsButton extends Button {
    public SelectYourClinicsButton(String email) {
        this.setText("Visualizza ambulatori in cui lavori");
        this.setOnAction(e -> {
            try {
                String query = "SELECT a.* FROM ambulatorio a JOIN collocazione c ON a.Nome = c.Nome WHERE c.Email = ?";
                PreparedStatement stmt = MySQLConnect.getConnection().prepareStatement(query);
                stmt.setString(1, email);
                ResultSet rs = stmt.executeQuery();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(null);
                alert.setHeaderText("Tabella ambulatori in cui lavori");
                String content = "";
                while (rs.next()) {
                    content += rs.getString("Nome") + "\t\t" + rs.getString("Ind_via") + " "
                            + rs.getString("Ind_Numero_Civico") + " " + rs.getString("Descrizione") + "\n";
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
