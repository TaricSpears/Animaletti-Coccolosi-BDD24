package gui.RUN.OWNER.Buttons;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

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
        this.setText("Visualizza il workout giornaliero");
        this.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Visualizza il workout giornaliero");
            dialog.setHeaderText("Inserisci il codice identificativo dell'animale");
            dialog.setContentText("Codice identificativo:");
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(this.getScene().getWindow());
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                TextInputDialog giornoDialog = new TextInputDialog();
                giornoDialog.setTitle("Visualizza il workout giornaliero");
                giornoDialog.setHeaderText("Inserisci il giorno per il workout");
                giornoDialog.setContentText("Giorno:");
                Optional<String> giornoResult = giornoDialog.showAndWait();
                if (giornoResult.isPresent()) {
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
                                String query2 = "SELECT w.* " +
                                        "FROM workout w " +
                                        "JOIN prescrizione_w pw ON w.Codice_Workout = pw.Codice_Workout " +
                                        "JOIN occorrenza_w ow ON pw.Codice_Terapia = ow.Codice_Terapia " +
                                        "JOIN giorno g ON ow.Codice_Giorno = g.Codice_Giorno " +
                                        "JOIN referto r ON ow.Codice_Referto = r.Codice_Referto " +
                                        "WHERE r.Codice_Identificativo = ? " +
                                        "  AND g.Giorno = ?";

                                try (PreparedStatement preparedStatement2 = connection.prepareStatement(query2)) {
                                    preparedStatement2.setString(1, result.get());
                                    preparedStatement2.setString(2, giornoResult.get());
                                    ResultSet resultSet2 = preparedStatement2.executeQuery();

                                    VBox vBox = new VBox();
                                    vBox.setAlignment(Pos.CENTER);
                                    vBox.setSpacing(5);
                                    vBox.setPadding(new Insets(10));

                                    boolean foundWorkout = false;
                                    while (resultSet2.next()) {
                                        foundWorkout = true;
                                        Text text = new Text(resultSet2.getString("Codice_Workout") + " "
                                                + resultSet2.getString("Descrizione"));
                                        text.setFont(Font.font(20));
                                        vBox.getChildren().add(text);
                                    }

                                    if (!foundWorkout) {
                                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                        alert.setTitle("Informazione");
                                        alert.setHeaderText("Nessun workout trovato");
                                        alert.setContentText("Non esiste alcun workout per il giorno specificato.");
                                        alert.showAndWait();
                                    } else {
                                        Stage stage = new Stage();
                                        stage.setTitle("Workout Giornaliero");
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
