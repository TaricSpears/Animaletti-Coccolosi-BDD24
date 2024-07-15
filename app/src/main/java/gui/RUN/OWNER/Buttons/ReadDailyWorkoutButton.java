package gui.RUN.OWNER.Buttons;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

import database.MySQLConnect;
import javafx.geometry.Insets;
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

public class ReadDailyWorkoutButton extends Button {
    public ReadDailyWorkoutButton(final String email) {
        this.setText("Visualizza i workout da fare oggi");
        this.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Visualizza i workout da fare oggi");
            dialog.setHeaderText("Inserisci il codice identificativo dell'animale");
            dialog.setContentText("Codice identificativo:");
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(this.getScene().getWindow());
            dialog.showAndWait().ifPresent(Codice_Identificativo -> {
                if (!isCodAnimValid(Codice_Identificativo, email)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Errore");
                    alert.setHeaderText("Errore");
                    alert.setContentText(
                            "Il codice identificativo inserito non corrisponde a nessuno dei tuoi animali");
                    alert.showAndWait();
                    return;
                }
                TextInputDialog dietaDialog = new TextInputDialog();
                dietaDialog.setTitle("Visualizza i workout da fare oggi");
                dietaDialog.setHeaderText("Inserisci Codice_Terapia di cui visualizzare i workout");
                dietaDialog.setContentText("Codice_Terapia:");
                dietaDialog.showAndWait().ifPresent(idTerapia -> {
                    if (!isIdTerValid(idTerapia, Codice_Identificativo)) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Errore");
                        alert.setHeaderText("Errore");
                        alert.setContentText(
                                "Il codice terapia inserito non corrisponde a nessuna terapia dell'animale");
                        alert.showAndWait();
                        return;
                    }
                    String Codice_Workout = "";
                    String query = "select ow.Codice_Workout " +
                            "from prescrizione_e pe join occorrenza_w ow " +
                            "on pe.Codice_Workout = ow.Codice_Workout " +
                            "where ow.Codice_Giorno = ? and pe.Codice_Terapia = ?";
                    try (Connection connection = MySQLConnect.getConnection();
                            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                        preparedStatement.setString(1, getDay());
                        preparedStatement.setString(2, idTerapia);
                        ResultSet resultSet = preparedStatement.executeQuery();

                        VBox vBox = new VBox();
                        vBox.setAlignment(Pos.CENTER);
                        vBox.setSpacing(5);
                        vBox.setPadding(new Insets(10));

                        boolean foundMenu = false;
                        if (resultSet.next()) {
                            Codice_Workout = resultSet.getString("Codice_Workout");
                            foundMenu = true;
                        }

                        if (!foundMenu) {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Informazione");
                            alert.setHeaderText("Nessun Workout trovato");
                            alert.setContentText("Non esiste alcun workout per il giorno specificato.");
                            alert.showAndWait();
                        } else {
                            Stage stage = new Stage();
                            stage.setTitle("Workout Giornaliero");
                            Scene scene = new Scene(vBox);

                            String query1 = "select * " +
                                    "from workout w join composizione_w cw " +
                                    "on w.Codice_Workout = cw.Codice_Workout " +
                                    "where w.Codice_Workout = ?";

                            PreparedStatement preparedStatement1 = connection
                                    .prepareStatement(query1);
                            preparedStatement1.setString(1, Codice_Workout);
                            ResultSet resultSet1 = preparedStatement1.executeQuery();
                            if (resultSet1.next()) {
                                Text text1 = new Text("Codice_Workout: "
                                        + resultSet1.getString("Codice_Workout") + " "
                                        + "\nDescrizione: " + resultSet1.getString("Descrizione")
                                        + "\n");
                                Text text2 = new Text("Nome Esercizio: " + resultSet1.getString("Nome")
                                        + " Frequenza: " + resultSet1.getString("Frequenza")
                                        + " Quantita: " + resultSet1.getString("Quantita") + "\n");
                                text1.setFont(Font.font(20));
                                text2.setFont(Font.font(15));
                                vBox.getChildren().addAll(text1, text2);
                            }
                            while (resultSet1.next()) {
                                Text text = new Text("Nome Esercizio: " + resultSet1.getString("Nome")
                                        + " Frequenza: " + resultSet1.getString("Frequenza")
                                        + " Quantita: " + resultSet1.getString("Quantita") + "\n");
                                text.setFont(Font.font(15));
                                vBox.getChildren().add(text);
                            }

                            stage.setScene(scene);
                            stage.initModality(Modality.APPLICATION_MODAL);
                            stage.initOwner(this.getScene().getWindow());
                            stage.showAndWait();
                        }
                    } catch (Exception ex) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Errore");
                        alert.setHeaderText("Errore");
                        alert.setContentText("Errore durante la visualizzazione dei workout");
                        alert.showAndWait();
                        ex.printStackTrace();
                    }
                });

            });
        });

    }

    private boolean isIdTerValid(final String idTerapia, final String Codice_Identificativo) {
        String query = "select * " +
                "from terapia t join referto_clinico r " +
                "on t.Codice_Referto = r.Codice_Referto " +
                "where t.Codice_Terapia = ? and r.Codice_Identificativo = ?";
        try (Connection connection = MySQLConnect.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, idTerapia);
            preparedStatement.setString(2, Codice_Identificativo);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    private boolean isCodAnimValid(final String codice_Identificativo, final String email) {
        String query = "SELECT * FROM animale WHERE Codice_Identificativo = ? AND Email = ?";
        try (Connection connection = MySQLConnect.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, codice_Identificativo);
            preparedStatement.setString(2, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    private String getDay() {
        String day = LocalDate.now().getDayOfWeek().name();
        switch (day) {
            case "MONDAY":
                day = "1";
                break;
            case "TUESDAY":
                day = "2";
                break;
            case "WEDNESDAY":
                day = "3";
                break;
            case "THURSDAY":
                day = "4";
                break;
            case "FRIDAY":
                day = "5";
                break;
            case "SATURDAY":
                day = "6";
                break;
            case "SUNDAY":
                day = "7";
                break;
        }
        return day;
    }
}
