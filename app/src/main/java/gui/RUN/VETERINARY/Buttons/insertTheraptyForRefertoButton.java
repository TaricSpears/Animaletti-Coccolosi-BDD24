package gui.RUN.VETERINARY.Buttons;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.MySQLConnect;
import gui.Tab;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class insertTheraptyForRefertoButton extends Button {
    public insertTheraptyForRefertoButton(final Stage primaryStage, final Tab previousTab) {
        this.setText("Inserisci terapia per referto");
        this.setOnAction(click -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Inserisci terapia per referto");
            dialog.setHeaderText("Inserisci il codice identificativo del referto");
            dialog.setContentText("Codice identificativo:");
            dialog.showAndWait().ifPresent(Codice_Referto -> {
                if (Codice_Referto.isEmpty() || !isValid(Codice_Referto)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Errore");
                    alert.setHeaderText("Errore nell'inserimento del codice identificativo");
                    alert.setContentText("Il campo non può essere vuoto");
                    return;
                }
                // Create a VBox layout
                VBox root = new VBox();
                root.setAlignment(Pos.CENTER);
                root.setSpacing(20);

                // Create a title
                Text title = new Text("Animaletti Coccolosi");
                title.setFont(Font.font(24));

                // Create email field
                TextField nomeField = new TextField();
                nomeField.setPromptText("Nome Terapia");

                // Create password field
                TextField descrizioneField = new TextField();
                descrizioneField.setPromptText("Descrizione");

                // Create date of birth field
                DatePicker dataInizio = new DatePicker();
                dataInizio.setPromptText("data inizio");

                // Create login button
                Button inserisciButton = new Button("inserisci");
                inserisciButton.setOnAction(e -> {
                    if (nomeField.getText().length() == 0 || nomeField.getText().length() > 20
                            || descrizioneField.getText().length() == 0
                            || descrizioneField.getText().length() > 100 || dataInizio.getValue() == null) {
                        Alert alert3 = new Alert(Alert.AlertType.ERROR);
                        alert3.setTitle("Errore");
                        alert3.setHeaderText("Errore");
                        alert3.setContentText("Nome o descrizione della terapia non validi");
                        alert3.showAndWait();
                        return;
                    }
                    String query = "INSERT INTO terapia (Nome, Descrizione, Codice_Referto, Data_Inizio) VALUES (?, ?, ?, ?)";
                    try (Connection connection = MySQLConnect.getConnection();
                            PreparedStatement preparedStatement = connection.prepareStatement(query)) {

                        preparedStatement.setString(1, nomeField.getText());
                        preparedStatement.setString(2, descrizioneField.getText());
                        preparedStatement.setString(3, Codice_Referto);
                        preparedStatement.setString(4, dataInizio.getValue().toString());
                        preparedStatement.executeUpdate();

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Inserimento terapia");
                        alert.setHeaderText("Inserimento terapia");
                        alert.setContentText("Terapia inserita con successo");
                        alert.showAndWait();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                });

                // Add components to the layout
                root.getChildren().addAll(title, nomeField, descrizioneField, inserisciButton);

                Scene scene = new Scene(root, 400, 300);
                primaryStage.setTitle("Animaletti Coccolosi");
                primaryStage.setScene(scene);
                primaryStage.showAndWait();

            });
        });

    }

    private boolean isValid(String Codice_Referto) {
        String query = "SELECT * FROM referto_clinico WHERE Codice_Referto = ?";
        try (Connection connection = MySQLConnect.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, Codice_Referto);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Errore");
                alert.setHeaderText("Errore");
                alert.setContentText("Il codice identificativo inserito non corrisponde a nessun referto");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
}
