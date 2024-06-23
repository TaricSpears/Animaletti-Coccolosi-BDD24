package gui.RUN.COMMONS;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import database.MySQLConnect;
import javafx.scene.control.Alert;
import javafx.scene.text.Text;

public class UserDataText extends Text {
    public UserDataText(final String email) {
        String query = "SELECT u.nome, u.cognome, u.email FROM utente u WHERE u.email = ?";
        try {
            PreparedStatement stmt = MySQLConnect.getConnection().prepareStatement(query);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String name = rs.getString("nome");
                String surname = rs.getString("cognome");
                this.setText(name + "\t| " + surname + "\t| " + email);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(null);
                alert.setHeaderText("Errore");
                alert.setContentText("L'utente non esiste");
                alert.showAndWait();
            }
            stmt.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
