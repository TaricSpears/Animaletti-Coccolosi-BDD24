package gui.RUN.VETERINARY.Buttons;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.MySQLConnect;
import gui.Tab;
import gui.RUN.VETERINARY.Actions.RegisterDiet;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

public class PopulateTherapyWithDietButton extends Button {
    public PopulateTherapyWithDietButton(final String email, final Stage primaryStage,
            final Tab previousTab) {
        this.setText("Popola terapia con dieta");

        this.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Popola terapia");
            dialog.setHeaderText("Inserisci il codice identificativo della terapia");
            dialog.setContentText("Codice identificativo:");
            dialog.showAndWait().ifPresent(idTerapia -> {
                if (isValid(idTerapia, email)) {
                    RegisterDiet registerDiet = new RegisterDiet();
                    registerDiet.show(idTerapia, primaryStage, previousTab);
                }
            });
        });
    }

    private boolean isValid(final String idTerapia, final String email) {
        if (idTerapia.isEmpty() || idTerapia.length() > 3 || !idTerapia.matches("[0-9]+")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText("Codice identificativo non valido");
            alert.setContentText("Il codice identificativo deve essere un intero di massimo 3 cifre");
            alert.showAndWait();
            return false;
        }
        String query = "SELECT * FROM Terapia WHERE Codice_Terapia = ?";
        String Codice_Referto = "";
        try (Connection conn = MySQLConnect.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, idTerapia);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Errore");
                alert.setHeaderText("Codice identificativo non valido");
                alert.setContentText("Il codice identificativo non corrisponde a nessuna terapia");
                alert.showAndWait();
                return false;
            }
            if (rs.getString("Codice_Dieta") != null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Errore");
                alert.setHeaderText("Errore");
                alert.setContentText("La terapia e' gia' associata ad una dieta");
                alert.showAndWait();
                return false;
            }
            Codice_Referto = rs.getString("Codice_Referto");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        String query2 = "SELECT r.ID_visita FROM referto_clinico r WHERE r.Codice_Referto = ?";
        String idVisita = "";
        try (Connection connection = MySQLConnect.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query2)) {

            preparedStatement.setString(1, Codice_Referto);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Errore");
                alert.setHeaderText("Errore");
                alert.setContentText("Il referto non e' associato ad alcuna visita");
                alert.showAndWait();
                return false;
            }
            idVisita = resultSet.getString("ID_visita");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        String query3 = "SELECT v.ACC_Email FROM visita v WHERE v.ID_visita = ?";
        try (Connection connection = MySQLConnect.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query3)) {

            preparedStatement.setString(1, idVisita);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Errore");
                alert.setHeaderText("Errore");
                alert.setContentText(
                        "La visita associata al referto di questa terapia non e' stata svolta da alcun medico!");
                alert.showAndWait();
                return false;
            }
            if (!resultSet.getString("ACC_Email").equals(email)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Errore");
                alert.setHeaderText("Errore");
                alert.setContentText("Non hai il permesso di modificare una terapia per questo referto");
                alert.showAndWait();
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
}