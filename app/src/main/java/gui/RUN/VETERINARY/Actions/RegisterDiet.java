package gui.RUN.VETERINARY.Actions;

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
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class RegisterDiet {

    public void show(final String idTerapia, final Stage primaryStage, final Tab previousTab) {
        // Create a VBox layout
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(20);

        // Create a title
        Text title = new Text("Registra dieta");
        title.setFont(Font.font(24));

        // Create nome field
        TextField nomeField = new TextField();
        nomeField.setPromptText("Nome");

        // Create descrizione field
        TextField descrizioneField = new TextField();
        descrizioneField.setPromptText("Descrizione");

        // create indietro button
        Button indietroButton = new Button("Indietro");
        indietroButton.setOnAction(e -> {
            try {
                previousTab.show();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        // Create inserisci button
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
                // aggiornla la terapia associata con il cdice di questa dieta
                String query3 = "UPDATE terapia SET Codice_Dieta = ? WHERE Codice_Terapia = ?";
                try {
                    PreparedStatement preparedStatement = MySQLConnect.getConnection()
                            .prepareStatement(query3);
                    preparedStatement.setInt(1, Codice_Dieta);
                    preparedStatement.setString(2, idTerapia);
                    preparedStatement.executeUpdate();
                } catch (Exception ex) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Errore");
                    alert.setHeaderText("Errore");
                    alert.setContentText("Errore durante l'aggiornamento della terapia");
                    alert.showAndWait();
                    ex.printStackTrace();
                }

                // passa iddieta a checkboxmatrixmenu
                CheckboxMatrixMenu checkBoxMatrixMenu = new CheckboxMatrixMenu();
                checkBoxMatrixMenu.show(primaryStage, previousTab, Codice_Dieta);

            }
        });

        // Add components to the layout
        root.getChildren().addAll(title, inserisciButton, nomeField, descrizioneField, indietroButton);

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setTitle("Animaletti Coccolosi");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
