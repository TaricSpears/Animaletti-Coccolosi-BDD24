//visualizza diete di un animale(prop, vet)

package gui.RUN.COMMONS;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

import com.mysql.cj.x.protobuf.MysqlxPrepare.Prepare;

import database.MySQLConnect;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ShowDietsButton extends Button {
    public ShowDietsButton(String email, Roles role) {
        this.setText("Visualizza le diete");
        this.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Visualizza le diete");
            dialog.setHeaderText("Inserisci il codice identificativo dell'animale");
            dialog.setContentText("Codice identificativo:");
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(this.getScene().getWindow());
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent() && role.equals(Roles.VETERINARIO)) {
                String query = "SELECT a.* FROM animale a, visita v WHERE a.Codice_Identificativo = v.Codice_Identificativo AND v.ACC_Email = ? GROUP BY a.Codice_Identificativo";
                try {
                    Connection connection = MySQLConnect.getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setString(1, email);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    boolean found = false;
                    while (resultSet.next()) {
                        if (resultSet.getString("Codice_Identificativo").equals(result.get())) {
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Errore");
                        alert.setHeaderText("Errore");
                        alert.setContentText(
                                "Il codice identificativo inserito non corrisponde a nessuno dei tuoi pazienti");
                        alert.showAndWait();
                        return;
                    }
                } catch (Exception ex) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Errore");
                    alert.setHeaderText("Errore");
                    alert.setContentText("Errore durante la visualizzazione delle diete");
                    alert.showAndWait();
                    ex.printStackTrace();
                    return;
                }
                String query1 = "SELECT * FROM dieta IN (SELECT Codice_Identificativo FROM alimentazione WHERE Codice_Identificativo = ?)";
                String query2 = "SELECT * FROM dieta WHERE Codice_Dieta IN (" +
                        "SELECT t.Codice_Dieta FROM terapia t " +
                        "JOIN referto r ON t.codice_referto = r.codice_referto " +
                        "WHERE r.Codice_Identificativo = ?)";
                try {
                    Connection connection = MySQLConnect.getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement(query1);
                    PreparedStatement preparedStatement2 = connection.prepareStatement(query2);
                    preparedStatement.setString(1, result.get());
                    preparedStatement2.setString(1, result.get());

                    ResultSet resultSet = preparedStatement.executeQuery();
                    ResultSet resultSet2 = preparedStatement2.executeQuery();
                    String result1 = "Codice Identificativo\tNome\tDescrizione\n";
                    while (resultSet.next()) {
                        result1 += resultSet.getString("Codice_Identificativo") + "\t";
                        result1 += resultSet.getString("Nome") + "\t";
                        result1 += resultSet.getString("Descrizione") + "\n";
                    }
                    while (resultSet2.next()) {
                        result1 += resultSet2.getString("Codice_Identificativo") + "\t";
                        result1 += resultSet2.getString("Nome") + "\t";
                        result1 += resultSet2.getString("Descrizione") + "\n";
                    }
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Diete");
                    alert.setHeaderText("Diete");
                    alert.setContentText(result1);
                    alert.setResizable(true);
                    alert.showAndWait();
                } catch (Exception ex) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Errore");
                    alert.setHeaderText("Errore");
                    alert.setContentText("Errore durante la visualizzazione delle diete");
                    alert.showAndWait();
                    ex.printStackTrace();
                }
            } else if (result.isPresent() && role.equals(Roles.PROPRIETARIO)) {
                String query = "SELECT * FROM animale WHERE Codice_Identificativo = ?";
                try {
                    Connection connection = MySQLConnect.getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setString(1, result.get());
                    ResultSet resultSet = preparedStatement.executeQuery();
                    boolean found = false;
                    while (resultSet.next()) {
                        if (resultSet.getString("Email").equals(email)) {
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Errore");
                        alert.setHeaderText("Errore");
                        alert.setContentText(
                                "Il codice identificativo inserito non corrisponde a nessuno dei tuoi animali");
                        alert.showAndWait();
                        return;
                    }
                } catch (Exception ex) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Errore");
                    alert.setHeaderText("Errore");
                    alert.setContentText("Errore durante la visualizzazione delle diete");
                    alert.showAndWait();
                    ex.printStackTrace();
                    return;
                }
                String query1 = "SELECT * FROM dieta IN (SELECT Codice_Identificativo FROM alimentazione WHERE Codice_Identificativo = ?)";
                String query2 = "SELECT * FROM dieta WHERE Codice_Dieta IN (" +
                        "SELECT t.Codice_Dieta FROM terapia t " +
                        "JOIN referto r ON t.codice_referto = r.codice_referto " +
                        "WHERE r.Codice_Identificativo = ?)";
                try {
                    Connection connection = MySQLConnect.getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement(query1);
                    PreparedStatement preparedStatement2 = connection.prepareStatement(query2);
                    preparedStatement.setString(1, result.get());
                    preparedStatement2.setString(1, result.get());

                    ResultSet resultSet = preparedStatement.executeQuery();
                    ResultSet resultSet2 = preparedStatement2.executeQuery();
                    String result1 = "Codice Identificativo\tNome\tDescrizione\n";
                    while (resultSet.next()) {
                        result1 += resultSet.getString("Codice_Identificativo") + "\t";
                        result1 += resultSet.getString("Nome") + "\t";
                        result1 += resultSet.getString("Descrizione") + "\n";
                    }
                    while (resultSet2.next()) {
                        result1 += resultSet2.getString("Codice_Identificativo") + "\t";
                        result1 += resultSet2.getString("Nome") + "\t";
                        result1 += resultSet2.getString("Descrizione") + "\n";
                    }
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Diete");
                    alert.setHeaderText("Diete");
                    alert.setContentText(result1);
                    alert.setResizable(true);
                    alert.showAndWait();
                } catch (Exception ex) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Errore");
                    alert.setHeaderText("Errore");
                    alert.setContentText("Errore durante la visualizzazione delle diete");
                    alert.showAndWait();
                    ex.printStackTrace();
                }

            }

        });

    }

}
