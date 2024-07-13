//consulta referti per un animale: inserisci IDanimale. vai ai suoi referti. ordina per data emissione.
package gui.RUN.COMMONS;

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

public class ShowRefertiButton extends Button {
    public ShowRefertiButton(final String email, final Roles role) {
        this.setText("Visualizza referti per un animale");
        this.setOnAction(click -> {
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Inserisci IDanimale");

            Text title = new Text("Inserisci IDanimale:");
            title.setFont(new Font(20));

            TextField idAnimale = new TextField();
            idAnimale.setPromptText("IDanimale");

            Button submit = new Button("Invia");
            submit.setOnAction(e -> {
                if (isValid(idAnimale.getText(), email, role)) {
                    String query = "SELECT * FROM referto_clinico WHERE Codice_Identificativo = ? ORDER BY Data_emissione DESC";
                    try {
                        Connection connection = MySQLConnect.getConnection();
                        PreparedStatement preparedStatement = connection.prepareStatement(query);
                        preparedStatement.setString(1, idAnimale.getText());
                        ResultSet resultSet = preparedStatement.executeQuery();
                        String result = "Referti per l'animale " + idAnimale.getText() + ":\n";
                        while (resultSet.next()) {
                            result += "Descrizione: " + resultSet.getString("Descrizione")
                                    + "\tCodice Referto: " + resultSet.getString("Codice_Referto")
                                    + "\tData emissione: " + resultSet.getString("Data_emissione")
                                    + "\tID_Visita: " + resultSet.getString("ID_Visita") + "\n";
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

    private boolean isValid(final String idAnimale, final String email, final Roles role) {
        if (idAnimale.isEmpty() || !idAnimale.matches("[0-9]+") || idAnimale.length() > 5) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText("Errore");
            alert.setContentText("L'ID animale deve essere un numero di massimo 5 cifre");
        }
        if (role == Roles.VETERINARIO) {
            String query = "SELECT * FROM visita WHERE Codice_Identificativo = ? AND ACC_Email = ?";
            try {
                Connection connection = MySQLConnect.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, idAnimale);
                preparedStatement.setString(2, email);
                ResultSet rs = preparedStatement.executeQuery();
                if (!rs.next()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Errore");
                    alert.setHeaderText("Errore");
                    alert.setContentText("Non hai accesso a questo animale");
                    alert.showAndWait();
                    return false;
                }
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        if (role == Roles.PROPRIETARIO) {
            String query = "SELECT * FROM animale WHERE Codice_Identificativo = ? AND Email = ?";
            try {
                Connection connection = MySQLConnect.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, idAnimale);
                preparedStatement.setString(2, email);
                ResultSet rs = preparedStatement.executeQuery();
                if (!rs.next()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Errore");
                    alert.setHeaderText("Errore");
                    alert.setContentText("Non hai accesso a questo animale");
                    alert.showAndWait();
                    return false;
                }
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }
}
