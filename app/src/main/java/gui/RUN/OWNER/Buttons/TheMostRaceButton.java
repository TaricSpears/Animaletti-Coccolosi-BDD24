//razza più propensa ad una condizione clinica: inserisci IDcondizione. calcola la razza più propensa a quella condizione
package gui.RUN.OWNER.Buttons;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import database.MySQLConnect;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class TheMostRaceButton extends Button {
    public TheMostRaceButton() {
        this.setText("Specie piu propensa ad una cond clinica");
        this.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Specie piu propensa");
            dialog.setHeaderText("Inserisci il nome della condizione clinica");
            dialog.setContentText("ID: ");

            java.util.Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                // query to count the number of animal with the condition
                // use the joined table assegniazione to get all the animals with the condition
                // look inside the table animale to get APP_Nome and count the max APP_Nome with
                // the condition

                String query = "SELECT APP_Nome AS specie FROM animale JOIN assegnazione ON animale.Codice_Identificativo = assegnazione.Codice_Identificativo WHERE assegnazione.Nome = '"
                        + result.get() + "' GROUP BY APP_Nome ORDER BY COUNT(APP_Nome) DESC LIMIT 1";
                try {
                    PreparedStatement preparedStatement = MySQLConnect.getConnection().prepareStatement(query);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    if (resultSet.next()) {
                        Text text = new Text("La specie piu propensa ad avere la condizione clinica "
                                + result.get() + " e': " + resultSet.getString("specie"));
                        text.setFont(new Font(20));
                        VBox vBox = new VBox(text);
                        vBox.setAlignment(Pos.CENTER);
                        dialog.getDialogPane().setContent(vBox);
                        dialog.showAndWait();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Error");
                        alert.setContentText("Nessun animale ha la condizione clinica con nome " + result.get());
                        alert.showAndWait();
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }

        });

    }
}