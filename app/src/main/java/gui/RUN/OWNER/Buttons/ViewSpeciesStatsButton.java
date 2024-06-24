package gui.RUN.OWNER.Buttons;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import database.MySQLConnect;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

public class ViewSpeciesStatsButton extends Button {
    public ViewSpeciesStatsButton() {
        this.setText("Visualizza statistiche specie");
        this.setOnAction(click -> {
            String query = "SELECT s.Nome, s.peso_medio, AVG(v.Voto) AS media_voti, COUNT(v.Voto) AS numero_voti " +
                    "FROM animale a LEFT JOIN valutazione_a v ON a.Codice_Identificativo = v.Codice_Identificativo " +
                    "JOIN specie s ON a.APP_Nome = s.Nome " +
                    "WHERE (s.Nome = a.APP_Nome) " +
                    "GROUP BY s.Nome ";
            try {
                Connection connection = MySQLConnect.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();
                String result = "Specie\t\tPeso medio\tMedia voti\tNumero voti\n";
                while (resultSet.next()) {
                    result += resultSet.getString("Nome") + "\t\t";
                    result += resultSet.getString("peso_medio") + "\t\t";
                    result += resultSet.getString("media_voti") + "\t\t";
                    result += resultSet.getString("numero_voti") + "\n";
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Statistiche specie");
                alert.setHeaderText("Statistiche specie");
                alert.setContentText(result);
                alert.setResizable(true);
                alert.showAndWait();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Errore");
                alert.setHeaderText("Errore");
                alert.setContentText("Errore durante la visualizzazione delle statistiche delle specie");
                alert.showAndWait();
                e.printStackTrace();
            }
        });
    }
}
