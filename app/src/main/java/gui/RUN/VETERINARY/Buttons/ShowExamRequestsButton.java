package gui.RUN.VETERINARY.Buttons;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import database.MySQLConnect;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

public class ShowExamRequestsButton extends Button {
    public ShowExamRequestsButton(final String email) {
        this.setText("Mostra richieste di visita per urgenza");
        this.setOnAction(click -> {
            String query = "SELECT * FROM visita v WHERE (v.ACC_Email is null) AND (v.Email is null OR v.Email = ?) ORDER BY v.urgenza DESC ";
            try {
                Connection connection = MySQLConnect.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, email);
                ResultSet resultSet = preparedStatement.executeQuery();
                String result = "ID_Visita\tCodice Animale\tData\tOra\tUrgenza\tEmail vet\tEmail acc\tDescrizione\n";
                while (resultSet.next()) {
                    result += resultSet.getString("ID_visita") + "\t" + resultSet.getString("Codice_Identificativo")
                            + "\t" + resultSet.getString("Data") + "\t"
                            + resultSet.getString("Ora") + "\t" + resultSet.getString("Urgenza") + "\t"
                            + resultSet.getString("Email") + "\t" + resultSet.getString("ACC_Email") + "\t"
                            + resultSet.getString("Descrizione") + "\n";
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Visite");
                alert.setHeaderText("Visite");
                alert.setContentText(result);
                alert.setResizable(true);
                alert.showAndWait();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Errore");
                alert.setHeaderText("Errore");
                alert.setContentText("Errore durante la visualizzazione delle visite");
                alert.showAndWait();
                e.printStackTrace();
            }
        });
    }
}
