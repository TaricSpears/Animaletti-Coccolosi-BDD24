package gui.RUN.OWNER.Buttons;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;
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
    public ReadDailyWorkoutButton(String email) {
        this.setText("Visualizza i workout da fare oggi");
        this.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Visualizza i workout da fare oggi");
            dialog.setHeaderText("Inserisci il codice identificativo dell'animale");
            dialog.setContentText("Codice identificativo:");
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(this.getScene().getWindow());
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                String day = LocalDate.now().getDayOfWeek().name();
                switch (day) {
                    case "MONDAY":
                        day = "Lunedi";
                        break;
                    case "TUESDAY":
                        day = "Martedi";
                        break;
                    case "WEDNESDAY":
                        day = "Mercoledi";
                        break;
                    case "THURSDAY":
                        day = "Giovedi";
                        break;
                    case "FRIDAY":
                        day = "Venerdi";
                        break;
                    case "SATURDAY":
                        day = "Sabato";
                        break;
                    case "SUNDAY":
                        day = "Domenica";
                        break;
                }
                TextInputDialog dietaDialog = new TextInputDialog();
                dietaDialog.setTitle("Visualizza i workout da fare oggi");
                dietaDialog.setHeaderText("Inserisci Codice_Workout di cui visualizzare i farmaci");
                dietaDialog.setContentText("Codice_Workout:");
                Optional<String> idWorkout = dietaDialog.showAndWait();
                if (idWorkout.isPresent()) {
                    String query = "SELECT Email FROM animale WHERE Codice_Identificativo = ?";
                    try (Connection connection = MySQLConnect.getConnection();
                            PreparedStatement preparedStatement = connection.prepareStatement(query)) {

                        preparedStatement.setString(1, result.get());
                        ResultSet resultSet = preparedStatement.executeQuery();

                        if (resultSet.next()) {
                            if (!resultSet.getString("Email").equals(email)) {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Errore");
                                alert.setHeaderText("Errore");
                                alert.setContentText(
                                        "Il codice identificativo inserito non corrisponde a nessuno dei tuoi animali");
                                alert.showAndWait();
                                return;
                            } else {
                                String query2 = "select * " +
                                        "from workout w " +
                                        "join composizione_w cw on cw.Codice_Workout = w.Codice_Workout " +
                                        "join prescrizione_e pe on pe.Codice_Workout = w.Codice_Workout " +
                                        "join occorrenza_w  ow on ow.Codice_Workout = of.Codice_Workout " +
                                        "join terapia t on t.Codice_Terapia = pe.Codice_Terapia" +
                                        "join referto_clinico rc on rt.Codice_Referto = t.Codice_Referto" +
                                        "where rc.Codice_Identificativo = ? " +
                                        "and ow.Codice_Giorno = ? " +
                                        "and t.Codice_Terapia = ?";

                                try (PreparedStatement preparedStatement2 = connection.prepareStatement(query2)) {
                                    preparedStatement2.setString(1, result.get());
                                    preparedStatement2.setString(2, day);
                                    preparedStatement2.setString(2, idWorkout.get());
                                    ResultSet resultSet2 = preparedStatement2.executeQuery();

                                    VBox vBox = new VBox();
                                    vBox.setAlignment(Pos.CENTER);
                                    vBox.setSpacing(5);
                                    vBox.setPadding(new Insets(10));

                                    boolean foundMenu = false;
                                    while (resultSet2.next()) {
                                        foundMenu = true;
                                        Text text = new Text(resultSet2.getString("Codice_Workout") + " "
                                                + resultSet2.getString("Descrizione"));
                                        text.setFont(Font.font(20));
                                        vBox.getChildren().add(text);
                                    }

                                    if (!foundMenu) {
                                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                        alert.setTitle("Informazione");
                                        alert.setHeaderText("Nessun Workout trovato");
                                        alert.setContentText("Non esiste alcun regime per il giorno specificato.");
                                        alert.showAndWait();
                                    } else {
                                        Stage stage = new Stage();
                                        stage.setTitle("Regime Giornaliero");
                                        Scene scene = new Scene(vBox);
                                        stage.setScene(scene);
                                        stage.initModality(Modality.APPLICATION_MODAL);
                                        stage.initOwner(this.getScene().getWindow());
                                        stage.showAndWait();
                                    }
                                }
                            }
                        } else {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Errore");
                            alert.setHeaderText("Errore");
                            alert.setContentText(
                                    "Il codice identificativo inserito non corrisponde a nessuno dei tuoi animali");
                            alert.showAndWait();
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }
}
