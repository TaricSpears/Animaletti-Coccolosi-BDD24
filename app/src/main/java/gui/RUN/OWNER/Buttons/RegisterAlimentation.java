package gui.RUN.OWNER.Buttons;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import database.MySQLConnect;
import gui.Tab;
import gui.RUN.COMMONS.CheckboxMatrixMenu;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class RegisterAlimentation {

    public void show(final String email, final Stage primaryStage, final Tab previousTab) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Registra Alimentazione");
        dialog.setHeaderText("Registra Alimentazione");
        dialog.setContentText("Inserisci il codice identificativo dell'animale:");
        dialog.showAndWait().ifPresent(idAnimale -> {
            // controlla che l id inserito sia valido
            if (isValid(idAnimale, email)) {
                // Create a VBox layout
                VBox root = new VBox();
                root.setAlignment(Pos.CENTER);
                root.setSpacing(20);

                // Create a title
                Text title = new Text("Registra Alimentazione");
                title.setFont(Font.font(24));

                // Create email field
                TextField nomeField = new TextField();
                nomeField.setPromptText("Nome");

                // Create password field
                TextField descrizioneField = new TextField();
                descrizioneField.setPromptText("Descrizione");

                // Create login button
                Button inserisciButton = new Button("Inserisci");
                inserisciButton.setOnAction(ev -> {
                    if (nomeField.getText().length() == 0 || nomeField.getText().length() > 20
                            || descrizioneField.getText().length() == 0
                            || descrizioneField.getText().length() > 100) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Errore");
                        alert.setHeaderText("Errore");
                        alert.setContentText("Lunghezza di nome o descrizione non valida");
                        alert.showAndWait();
                    } else {
                        // chiedi la descrizione e nome della dieta. Inserisci tale dieta nel db.
                        String query = "INSERT INTO dieta (Nome, Descrizione) VALUES (?, ?)";
                        try {
                            PreparedStatement preparedStatement = MySQLConnect.getConnection()
                                    .prepareStatement(query);
                            preparedStatement.setString(1, nomeField.getText());
                            preparedStatement.setString(2, descrizioneField.getText());
                            preparedStatement.executeUpdate();
                        } catch (Exception ex) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Errore");
                            alert.setHeaderText("Errore");
                            alert.setContentText("Errore durante l'inserimento della dieta");
                            alert.showAndWait();
                            ex.printStackTrace();
                        }
                        // ottieni id dieta.
                        String query2 = "SELECT Codice_Dieta FROM dieta ORDER BY Codice_Dieta DESC LIMIT 1";
                        int Codice_Dieta = 0;
                        try {
                            PreparedStatement preparedStatement = MySQLConnect.getConnection()
                                    .prepareStatement(query2);
                            ResultSet resultSet = preparedStatement.executeQuery();
                            resultSet.next();
                            Codice_Dieta = resultSet.getInt("Codice_Dieta");
                        } catch (Exception ex) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Errore");
                            alert.setHeaderText("Errore");
                            alert.setContentText("Errore durante l'ottenimento dell'id della dieta");
                            alert.showAndWait();
                            ex.printStackTrace();
                        }
                        // inserisci istanza alimentazione nel db
                        String query3 = "INSERT INTO alimentazione (Codice_Identificativo, Codice_Dieta) VALUES (?, ?)";
                        try {
                            PreparedStatement preparedStatement = MySQLConnect.getConnection()
                                    .prepareStatement(query3);
                            preparedStatement.setString(1, idAnimale);
                            preparedStatement.setInt(2, Codice_Dieta);
                            preparedStatement.executeUpdate();
                        } catch (Exception ex) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Errore");
                            alert.setHeaderText("Errore");
                            alert.setContentText("Errore durante l'inserimento dell'alimentazione");
                            alert.showAndWait();
                            ex.printStackTrace();
                        }
                        // passa iddieta a checkboxmatrixmenu
                        CheckboxMatrixMenu checkBoxMatrixMenu = new CheckboxMatrixMenu();
                        checkBoxMatrixMenu.show(primaryStage, previousTab, Codice_Dieta);

                    }
                });

                // Add components to the layout
                root.getChildren().addAll(title, inserisciButton, nomeField, descrizioneField);

                Scene scene = new Scene(root, 400, 300);
                primaryStage.setTitle("Animaletti Coccolosi");
                primaryStage.setScene(scene);
                primaryStage.show();
            }
        });
    }

    private boolean isValid(final String idAnimale, final String email) {
        if (idAnimale.length() == 0 || idAnimale.length() > 5) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText("Errore");
            alert.setContentText("Il codice identificativo deve essere compreso tra 1 e 5 caratteri");
            alert.showAndWait();
            return false;
        }
        String query = "SELECT * FROM animale WHERE Codice_Identificativo = ? AND Email = ?";
        try {
            PreparedStatement preparedStatement = MySQLConnect.getConnection().prepareStatement(query);
            preparedStatement.setString(1, idAnimale);
            preparedStatement.setString(2, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Errore");
                alert.setHeaderText("Errore");
                alert.setContentText("L'animale non esiste o non e' tuo");
                alert.showAndWait();
                return false;
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText("Errore");
            alert.setContentText("Errore durante la verifica del codice identificativo");
            alert.showAndWait();
            e.printStackTrace();
            return false;
        }
        return true;
    }
}