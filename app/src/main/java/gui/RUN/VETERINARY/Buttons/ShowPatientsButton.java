package gui.RUN.VETERINARY.Buttons;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import database.MySQLConnect;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

public class ShowPatientsButton extends Button {
    public ShowPatientsButton(final String email) {
        this.setText("Visualizza pazienti");
        this.setOnAction(click -> {
            String query = "SELECT a.* FROM animale a, visita v WHERE a.Codice_Identificativo = v.Codice_Identificativo AND v.ACC_Email = ?";
            try {
                Connection connection = MySQLConnect.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, email);
                ResultSet resultSet = preparedStatement.executeQuery();
                String result = "Codice Identificativo\tNome\tData di nascita\tPeso\tNome Specie\tProprietario\n";
                while (resultSet.next()) {
                    result += resultSet.getString("Codice_Identificativo") + "\t";
                    result += resultSet.getString("Nome") + "\t";
                    result += resultSet.getString("Data_di_Nascita") + "\t";
                    result += resultSet.getString("Peso") + "\t";
                    result += resultSet.getString("APP_Nome") + "\t";
                    result += resultSet.getString("Email") + "\n";
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Pazienti");
                alert.setHeaderText("Pazienti");
                alert.setContentText(result);
                alert.setResizable(true);
                alert.showAndWait();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Errore");
                alert.setHeaderText("Errore");
                alert.setContentText("Errore durante la visualizzazione dei pazienti");
                alert.showAndWait();
                e.printStackTrace();
            }
        });
    }
}
