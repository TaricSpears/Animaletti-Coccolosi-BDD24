package gui.RUN.ADMIN.Buttons;

import java.sql.ResultSet;
import java.sql.Statement;

import database.MySQLConnect;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import java.sql.SQLException;

public class ShowUserButton extends Button {
    public ShowUserButton() {
        this.setText("Visualizza tutti gli utenti");
        this.setOnAction(e -> {
            try {
                Statement stmt = MySQLConnect.getConnection().createStatement();
                String query = "SELECT * FROM utente";
                ResultSet rs = stmt.executeQuery(query);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(null);
                alert.setHeaderText("Tabella utente");
                String content = "";
                while (rs.next()) {
                    content += rs.getString("Nome") + "\t\t" + rs.getString("Cognome") + "\t\t" + rs.getString("Email")
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
