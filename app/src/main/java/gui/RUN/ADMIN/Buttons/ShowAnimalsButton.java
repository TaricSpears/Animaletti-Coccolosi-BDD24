package gui.RUN.ADMIN.Buttons;

import java.sql.ResultSet;
import java.sql.Statement;

import database.MySQLConnect;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import java.sql.SQLException;

public class ShowAnimalsButton extends Button {
    public ShowAnimalsButton() {
        this.setText("Mostra animali");
        this.setOnAction(e -> {
            try {
                Statement stmt = MySQLConnect.getConnection().createStatement();
                String query = "SELECT * FROM animale";
                ResultSet rs = stmt.executeQuery(query);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(null);
                alert.setHeaderText("Tabella animale");
                String content = "";
                content += "Nome" + "\t\t" + "Data_di_Nascita" + "\t\t"
                        + "Peso"
                        + "\t\t" + "Valutazione_media" + "\t\t" + "APP_nome" + "\t\t"
                        + "Codice_Identificativo" + "\t\t" + "Email" + "\n";
                while (rs.next()) {
                    content += rs.getString("Nome") + "\t\t" + rs.getString("Data_di_Nascita") + "\t\t"
                            + rs.getString("Peso")
                            + "\t\t" + rs.getString("Valutazione_media") + "\t\t" + rs.getString("APP_nome") + "\t\t"
                            + rs.getString("Codice_Identificativo") + "\t\t" + rs.getString("Email") + "\n";
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
