package gui.RUN.ADMIN.Buttons;

import java.sql.ResultSet;
import java.sql.Statement;

import database.MySQLConnect;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import java.sql.SQLException;

public class ShowUserReportsButton extends Button {
    public ShowUserReportsButton() {
        this.setText("Visualizza tutte le segnalazioni");
        this.setOnAction(e -> {
            try {
                Statement stmt = MySQLConnect.getConnection().createStatement();
                String query = "SELECT * FROM segnalazione";
                ResultSet rs = stmt.executeQuery(query);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(null);
                alert.setHeaderText("Tabella segnalazione");
                String content = "";
                while (rs.next()) {
                    content += rs.getString("Email") + "\t\t" + rs.getString("ID_messaggio") + "\t\t"
                            + rs.getString("Motivazione")
                            + "\n";
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
