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

public class ReadDailyMedicineButton extends Button {
    public ReadDailyMedicineButton(String email) {
        this.setText("Visualizza i farmaci da prendere oggi");
        this.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Visualizza i farmaci da prendere oggi");
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
                dietaDialog.setTitle("Visualizza i farmaci da prendere oggi");
                dietaDialog.setHeaderText("Inserisci Codice_Terapia di cui visualizzare i farmaci");
                dietaDialog.setContentText("Codice_Terapia:");
                Optional<String> idTerapia = dietaDialog.showAndWait();
                if (idTerapia.isPresent()) {
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
                                        "from regime_farmacologico r " +
                                        "join assunzione a on a.Codice_Regime = r.Codice_Regime " +
                                        "join prescrizione_a p on p.Codice_Regime = a.Codice_Regime " +
                                        "join occorrenza_f  of on r.Codice_Regime = of.Codice_Regime " +
                                        "join terapia t on t.Codice_Terapia = p.Codice_Terapia" +
                                        "join referto_clinico rc on rt.Codice_Referto = t.Codice_Referto" +
                                        "where rc.Codice_Identificativo = ? " +
                                        "and om.Codice_Giorno = ? " +
                                        "and t.Codice_Terapia = ?";

                                try (PreparedStatement preparedStatement2 = connection.prepareStatement(query2)) {
                                    preparedStatement2.setString(1, result.get());
                                    preparedStatement2.setString(2, day);
                                    preparedStatement2.setString(2, idTerapia.get());
                                    ResultSet resultSet2 = preparedStatement2.executeQuery();

                                    VBox vBox = new VBox();
                                    vBox.setAlignment(Pos.CENTER);
                                    vBox.setSpacing(5);
                                    vBox.setPadding(new Insets(10));

                                    boolean foundMenu = false;
                                    while (resultSet2.next()) {
                                        foundMenu = true;
                                        Text text = new Text(resultSet2.getString("Codice_Regime") + " "
                                                + resultSet2.getString("Descrizione"));
                                        text.setFont(Font.font(20));
                                        vBox.getChildren().add(text);
                                    }

                                    if (!foundMenu) {
                                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                        alert.setTitle("Informazione");
                                        alert.setHeaderText("Nessun regime trovato");
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
