package gui.RUN.VETERINARY.Buttons;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import database.MySQLConnect;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

public class ShowClinicalConditionsButton extends Button {
    public ShowClinicalConditionsButton() {
        this.setText("Visualizza condizioni cliniche");
        this.setOnAction(click -> {
            String query = "SELECT * FROM condizione_clinica";
            try {
                Connection connection = MySQLConnect.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();
                String result = "Condizioni cliniche\n";
                while (resultSet.next()) {
                    result += resultSet.getString("Nome") + "\n";
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Condizioni cliniche");
                alert.setHeaderText("Condizioni cliniche");
                alert.setContentText(result);
                alert.setResizable(true);
                alert.showAndWait();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Errore");
                alert.setHeaderText("Errore");
                alert.setContentText("Errore durante la visualizzazione delle condizioni cliniche");
                alert.showAndWait();
                e.printStackTrace();
            }
        });
    }
}
