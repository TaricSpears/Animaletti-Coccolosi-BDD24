//visualizza proprietari dei pazienti: analogo a sopra, poi risali ai prop

package gui.RUN.VETERINARY.Buttons;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.MySQLConnect;
import gui.Tab;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ViewOwnersButton extends Button {
    public ViewOwnersButton(final String email) {
        this.setText("Visualizza email proprietari dei pazienti");
        this.setOnAction(click -> {
            String query = "SELECT a.Email FROM animale a, visita v WHERE a.Codice_Identificativo = v.Codice_Identificativo AND v.ACC_Email = ? GROUP BY a.Codice_Identificativo";
            try {
                Connection connection = MySQLConnect.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, email);
                ResultSet resultSet = preparedStatement.executeQuery();
                String result = "Email dei proprietari dei pazienti:\n";
                while (resultSet.next()) {
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
