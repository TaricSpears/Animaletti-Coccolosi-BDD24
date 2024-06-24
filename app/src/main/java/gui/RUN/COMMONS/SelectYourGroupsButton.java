package gui.RUN.COMMONS;

import java.sql.ResultSet;
import java.sql.Statement;

import database.MySQLConnect;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

public class SelectYourGroupsButton extends Button {
    public SelectYourGroupsButton(final String email) {
        this.setText("Visualizza i tuoi gruppi");
        this.setOnAction(e -> {
            try {
                Statement stmt = MySQLConnect.getConnection().createStatement();
                String query = "SELECT g.Nome, g.Data_apertura, g.privato, g.ID_Gruppo FROM gruppo g JOIN partecipazione p ON g.ID_Gruppo = p.ID_Gruppo WHERE p.email = '"
                        + email + "'";
                ResultSet rs = stmt.executeQuery(query);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(null);
                alert.setHeaderText("Gruppi");
                String content = "";

                content += "Nome" + "\t" +
                        "Data_apertura" + "\t" +
                        "ID_Gruppo" + "\t" +
                        "Destinatario" + "\n";
                String ID_Gruppo;
                boolean stato;
                while (rs.next()) {
                    content += rs.getString("Nome") + "\t";
                    content += rs.getString("Data_apertura") + "\t";
                    ID_Gruppo = rs.getString("ID_Gruppo");
                    content += ID_Gruppo + "\t";
                    stato = Boolean.parseBoolean(rs.getString("privato"));
                    if (stato) { // se è privato mostra email altro partecipante
                        String query2 = "SELECT email FROM partecipazione WHERE ID_Gruppo = '" + ID_Gruppo
                                + "' AND email != '" + email + "'";
                        ResultSet rs2 = stmt.executeQuery(query2);
                        rs2.next();
                        content += rs2.getString("email") + "\n";
                    } else { // se è pubblico
                        content += "Pubblico" + "\n";
                    }
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
