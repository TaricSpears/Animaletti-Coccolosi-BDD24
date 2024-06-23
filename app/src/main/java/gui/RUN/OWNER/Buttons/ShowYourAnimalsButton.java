package gui.RUN.OWNER.Buttons;

import java.sql.ResultSet;
import java.sql.Statement;

import database.MySQLConnect;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import java.sql.SQLException;

public class ShowYourAnimalsButton extends Button {
    public ShowYourAnimalsButton(String email) {
        this.setText("Mostra i tuoi animali");
        this.setOnAction(e -> {
            try {
                Statement stmt = MySQLConnect.getConnection().createStatement();
                String query = "SELECT * FROM animale WHERE Email = '" + email + "'";
                ResultSet rs = stmt.executeQuery(query);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(null);
                alert.setHeaderText("Tabella animale");
                String content = "";
                while (rs.next()) {
                    content += rs.getString("Nome") + "\t\t" + rs.getString("Data_di_Nascita") + "\t\t"
                            + rs.getString("Peso")
                            + "\t\t" + rs.getString("Valutazione_media") + "\t\t" + rs.getString("APP_nome") + "\t\t"
                            + rs.getString("Codice_Identificativo") + "\t\t" + rs.getString("Email") + "\n";
                }
                alert.setContentText(content);
                alert.showAndWait();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });
    }
}
