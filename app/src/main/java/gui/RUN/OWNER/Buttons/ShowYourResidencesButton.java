package gui.RUN.OWNER.Buttons;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

import database.MySQLConnect;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

public class ShowYourResidencesButton extends Button {
    public ShowYourResidencesButton(String email) {
        this.setText("Mostra le tue residenze");
        this.setOnAction(e -> {
            try {
                Statement stmt = MySQLConnect.getConnection().createStatement();
                String query = "SELECT r.Ind_Via, r.Ind_Numero_Civico, r.ZONA_Nome FROM residenza r WHERE Email = '"
                        + email + "'";
                ResultSet rs = stmt.executeQuery(query);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(null);
                alert.setHeaderText("Tabella residenze");
                String content = "";
                content += "Via" + " " + "Civico" + "\t\t"
                        + "ZONA" + "\n";
                while (rs.next()) {
                    content += rs.getString("Ind_Via") + " " + rs.getString("Ind_Numero_Civico") + "\t\t"
                            + rs.getString("ZONA_Nome") + "\n";
                }
                alert.setContentText(content);
                alert.setResizable(true);
                alert.showAndWait();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });
    }
}
