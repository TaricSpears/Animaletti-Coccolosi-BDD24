package gui.RUN.OWNER.Buttons;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import database.MySQLConnect;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

public class ShowVeterinaryButton extends Button {
    public ShowVeterinaryButton() {
        this.setText("Visualizza Veterinari registrati");
        this.setOnAction(click -> {
            String query = "SELECT * FROM veterinario v, utente u WHERE v.Email = u.Email";
            try {
                Connection connection = MySQLConnect.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();
                String result = "Nome\tCognome\tEmail\tValutazione media\tCurriculum\n";
                while (resultSet.next()) {
                    result += resultSet.getString("Nome") + "\t" + resultSet.getString("Cognome") + "\t"
                            + resultSet.getString("Email") + "\t" + resultSet.getString("Valutazione_Media") + "\t"
                            + resultSet.getString("Curriculum") + "\n";
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Veterinari");
                alert.setHeaderText("Veterinari");
                alert.setContentText(result);
                alert.setResizable(true);
                alert.showAndWait();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Errore");
                alert.setHeaderText("Errore");
                alert.setContentText("Errore durante la visualizzazione dei veterinari");
                alert.showAndWait();
                e.printStackTrace();
            }
        });
    }
}
