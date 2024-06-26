//consulta terapie determinate dall’ultimo referto per un animale (prop, vet)

package gui.RUN.COMMONS;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

import database.MySQLConnect;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ShowLatestTherapiesButton extends Button {
    public ShowLatestTherapiesButton(final String email, Roles role) {
        this.setText("Visualizza terapie");
        this.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Inserisci il codice identificativo dell'animale");
            dialog.setHeaderText("Inserisci il codice identificativo dell'animale");
            dialog.setContentText("Codice identificativo:");
            Optional<String> result = dialog.showAndWait();
            // if the user insert the code of the animal we can show the therapies only if
            // the animal is his
            String queryCheck = "SELECT Email FROM animale WHERE Codice_Identificativo = ?";
            // if the user insert the code of the animal we can show the therapies only if
            // the animal is his patient
            String queryCheck2 = "SELECT Email FROM visita WHERE Codice_Identificativo = ?";

            if (role.equals(Roles.PROPRIETARIO)) {
                try {
                    Connection connection = MySQLConnect.getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement(queryCheck);
                    preparedStatement.setString(1, result.get());
                    ResultSet resultSet = preparedStatement.executeQuery();
                    if (resultSet.next()) {
                        if (!resultSet.getString("Email").equals(email)) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Errore");
                            alert.setHeaderText("Errore");
                            alert.setContentText(
                                    "Non puoi visualizzare le terapie di un animale che non ti appartiene");
                            alert.showAndWait();
                            return;
                        } else {
                            String querySelect = "SELECT * FROM terapia WHERE Codice_Referto IN (SELECT Codice_Referto FROM referto WHERE Codice_Identificativo = ?) ORDER BY Data_Terapia DESC LIMIT 1";
                            PreparedStatement preparedStatement2 = connection.prepareStatement(querySelect);
                            preparedStatement2.setString(1, result.get());
                            ResultSet resultSet2 = preparedStatement2.executeQuery();
                            if (resultSet2.next()) {
                                Stage dialogStage = new Stage();
                                dialogStage.setTitle("Terapie");
                                VBox vBox = new VBox();
                                vBox.setAlignment(Pos.CENTER);
                                vBox.setSpacing(20);
                                Text title = new Text("Terapie");
                                title.setFont(Font.font(24));
                                vBox.getChildren().add(title);
                                do {
                                    String codiceTerapia = resultSet2.getString("Codice_Terapia");
                                    String nome = resultSet2.getString("Nome");
                                    String dataTerapia = resultSet2.getString("Data_Inizio");
                                    String descrizione = resultSet2.getString("Descrizione");
                                    Integer Codice_Dieta = resultSet2.getInt("Codice_Dieta");

                                    String codiceReferto = resultSet2.getString("Codice_Referto");
                                    Text text = new Text("Codice Terapia: " + codiceTerapia + " Nome: " + nome
                                            + " Data Terapia: " + dataTerapia + " Descrizione: " + descrizione
                                            + " Codice Dieta: " + Codice_Dieta + " Codice Referto: " + codiceReferto);
                                    text.setFont(new Font(20));
                                    vBox.getChildren().add(text);
                                } while (resultSet2.next());
                                Scene scene = new Scene(vBox, 800, 600);
                                dialogStage.setScene(scene);
                                dialogStage.initModality(Modality.APPLICATION_MODAL);
                                dialogStage.show();
                            } else {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Errore");
                                alert.setHeaderText("Errore");
                                alert.setContentText(
                                        "Non ci sono terapie per l'animale con il codice identificativo inserito");
                                alert.showAndWait();
                                return;
                            }
                        }
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Errore");
                        alert.setHeaderText("Errore");
                        alert.setContentText("Non esiste un animale con il codice identificativo inserito");
                        alert.showAndWait();
                        return;
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }

            } else if (role.equals(Roles.VETERINARIO)) {
                try {
                    Connection connection = MySQLConnect.getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement(queryCheck2);
                    preparedStatement.setString(1, result.get());
                    ResultSet resultSet = preparedStatement.executeQuery();
                    if (resultSet.next()) {
                        if (!resultSet.getString("Email").equals(email)) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Errore");
                            alert.setHeaderText("Errore");
                            alert.setContentText(
                                    "Non puoi visualizzare le terapie di un animale che non ti appartiene");
                            alert.showAndWait();
                            return;
                        } else {
                            String querySelect2 = "SELECT * FROM terapia WHERE Codice_Referto IN (SELECT Codice_Referto FROM referto WHERE Codice_Identificativo = ?) ORDER BY Data_Terapia DESC LIMIT 1";
                            PreparedStatement preparedStatement2 = connection.prepareStatement(querySelect2);
                            preparedStatement2.setString(1, result.get());
                            ResultSet resultSet2 = preparedStatement2.executeQuery();
                            if (resultSet2.next()) {
                                Stage dialogStage = new Stage();
                                dialogStage.setTitle("Terapie");
                                VBox vBox = new VBox();
                                vBox.setAlignment(Pos.CENTER);
                                vBox.setSpacing(20);
                                Text title = new Text("Terapie");
                                title.setFont(Font.font(24));
                                vBox.getChildren().add(title);
                                do {
                                    String codiceTerapia = resultSet2.getString("Codice_Terapia");
                                    String nome = resultSet2.getString("Nome");
                                    String dataTerapia = resultSet2.getString("Data_Inizio");
                                    String descrizione = resultSet2.getString("Descrizione");
                                    Integer Codice_Dieta = resultSet2.getInt("Codice_Dieta");

                                    String codiceReferto = resultSet2.getString("Codice_Referto");
                                    Text text = new Text("Codice Terapia: " + codiceTerapia + " Nome: " + nome
                                            + " Data Terapia: " + dataTerapia + " Descrizione: " + descrizione
                                            + " Codice Dieta: " + Codice_Dieta + " Codice Referto: " + codiceReferto);
                                    text.setFont(new Font(20));
                                    vBox.getChildren().add(text);
                                } while (resultSet2.next());
                                Scene scene = new Scene(vBox, 800, 600);
                                dialogStage.setScene(scene);
                                dialogStage.initModality(Modality.APPLICATION_MODAL);
                                dialogStage.show();
                            } else {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Errore");
                                alert.setHeaderText("Errore");
                                alert.setContentText(
                                        "Non ci sono terapie per l'animale con il codice identificativo inserito");
                                alert.showAndWait();
                                return;
                            }
                        }
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Errore");
                        alert.setHeaderText("Errore");
                        alert.setContentText("Non esiste un animale con il codice identificativo inserito");
                        alert.showAndWait();
                        return;
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }

        });
        // Create a VBox layout

    }
}
