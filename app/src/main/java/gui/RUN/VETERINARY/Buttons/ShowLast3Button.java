//consulta ultimi 3 referti per un animale: inserisci IDanimale. vai ai suoi referti. ordina per data emissione.
package gui.RUN.VETERINARY.Buttons;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import database.MySQLConnect;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ShowLast3Button extends Button {
    public ShowLast3Button(final String email) {
        this.setText("Visualizza ultimi 3 referti per un animale");
        this.setOnAction(click -> {
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Inserisci IDanimale");

            Text title = new Text("Inserisci IDanimale:");
            title.setFont(new Font(20));

            TextField idAnimale = new TextField();
            idAnimale.setPromptText("IDanimale");

            Button submit = new Button("Invia");
            submit.setOnAction(e -> {
                String query = "SELECT * FROM referto_clinico WHERE Codice_Identificativo = ? ORDER BY Data_emissione DESC LIMIT 3";
                try {
                    Connection connection = MySQLConnect.getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setString(1, idAnimale.getText());
                    ResultSet resultSet = preparedStatement.executeQuery();
                    String result = "Ultimi 3 referti per l'animale " + idAnimale.getText() + ":\n";
                    while (resultSet.next()) {
                        result += "Data emissione: " + resultSet.getString("Data_emissione") + "Descrizione: "
                                + resultSet.getString("Descrizione") + "Codice Identificativo: "
                                + resultSet.getString("Codice_Referto") + "ID_Visita: "
                                + resultSet.getString("ID_Visita") + "+\n";
                    }
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Referti");
                    alert.setHeaderText("Referti");
                    alert.setContentText(result);
                    alert.setResizable(true);
                    alert.showAndWait();
                } catch (Exception ex) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Errore");
                    alert.setHeaderText("Errore");
                    alert.setContentText("Errore durante la visualizzazione dei referti");
                    alert.showAndWait();
                    ex.printStackTrace();
                }
            });

            VBox vBox = new VBox(title, idAnimale, submit);
            vBox.setAlignment(Pos.CENTER);
            vBox.setSpacing(10);

            Scene scene = new Scene(vBox, 300, 200);
            dialogStage.setScene(scene);
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.show();
        });
    }
}
