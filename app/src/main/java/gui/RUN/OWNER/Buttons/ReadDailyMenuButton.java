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
                TextInputDialog giornoDialog = new TextInputDialog();
                giornoDialog.setTitle("Visualizza il menù giornaliero");
                giornoDialog.setHeaderText("Inserisci il giorno per il menù");
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
                                String query2 = "SELECT m.* " +
                                        "FROM menu m " +
                                        "JOIN dieta d ON m.Codice_Dieta = d.Codice_Dieta " +
                                        "JOIN occurrenza_m om ON m.Codice_Menu = om.Codice_Menu " +
                                        "JOIN giorno g ON om.Codice_Giorno = g.Codice_Giorno " +
                                        "JOIN ( " +
                                        "    SELECT t.Codice_Dieta " +
                                        "    FROM terapia t " +
                                        "    JOIN referto r ON t.codice_referto = r.codice_referto " +
                                        "    WHERE r.Codice_Identificativo = ? " +
                                        "    UNION " +
                                        "    SELECT a.Codice_Dieta " +
                                        "    FROM alimentazione a " +
                                        "    JOIN referto r ON a.codice_referto = r.codice_referto " +
                                        "    WHERE r.Codice_Identificativo = ? " +
                                        ") dietes ON d.Codice_Dieta = dietes.Codice_Dieta " +
                                        "WHERE g.Giorno = ?";

                                try (PreparedStatement preparedStatement2 = connection.prepareStatement(query2)) {
                                    preparedStatement2.setString(1, result.get());
                                    preparedStatement2.setString(2, result.get());
                                    preparedStatement2.setString(3, giornoResult.get());
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
