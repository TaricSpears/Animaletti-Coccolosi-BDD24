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

public class ReadDailyMenuButton extends Button {
    public ReadDailyMenuButton(String email) {
        this.setText("Visualizza il menù giornaliero");
        this.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Visualizza il menù giornaliero");
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
                dietaDialog.setTitle("Visualizza il menù giornaliero");
                dietaDialog.setHeaderText("Inserisci id dieta di cui visualizzare menu");
                dietaDialog.setContentText("ID_Dieta:");
                Optional<String> diIdetaResult = dietaDialog.showAndWait();
                if (diIdetaResult.isPresent()) {
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
                                        "from menu m " +
                                        "join comprensione c on c.Codice_Menu = m.Codice_Menu " +
                                        "join alimentazione a on a.Codice_Dieta = c.Codice_Dieta " +
                                        "join occorrenza_m  om on m.Codice_Menu = om.Codice_Menu " +
                                        "where a.Codice_Identificativo = ? " +
                                        "and om.Codice_Giorno = ?";

                                try (PreparedStatement preparedStatement2 = connection.prepareStatement(query2)) {
                                    preparedStatement2.setString(1, result.get());
                                    preparedStatement2.setString(2, diIdetaResult.get());
                                    ResultSet resultSet2 = preparedStatement2.executeQuery();

                                    VBox vBox = new VBox();
                                    vBox.setAlignment(Pos.CENTER);
                                    vBox.setSpacing(5);
                                    vBox.setPadding(new Insets(10));

                                    boolean foundMenu = false;
                                    while (resultSet2.next()) {
                                        foundMenu = true;
                                        Text text = new Text(resultSet2.getString("Codice_Menu") + " "
                                                + resultSet2.getString("Descrizione"));
                                        text.setFont(Font.font(20));
                                        vBox.getChildren().add(text);
                                    }

                                    if (!foundMenu) {
                                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                        alert.setTitle("Informazione");
                                        alert.setHeaderText("Nessun menù trovato");
                                        alert.setContentText("Non esiste alcun menù per il giorno specificato.");
                                        alert.showAndWait();
                                    } else {
                                        Stage stage = new Stage();
                                        stage.setTitle("Menù Giornaliero");
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
