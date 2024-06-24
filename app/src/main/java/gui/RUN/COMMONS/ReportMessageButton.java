package gui.RUN.COMMONS;

import java.sql.ResultSet;
import java.sql.Statement;

import database.MySQLConnect;
import gui.Tab;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ReportMessageButton extends Button {
    public ReportMessageButton(final String email, final Stage primaryStage, final Tab previousTab) {
        this.setText("Segnala messaggio");
        this.setOnAction(e -> {
            // Create a VBox layout
            VBox root = new VBox();
            root.setAlignment(Pos.CENTER);
            root.setSpacing(20);

            // Create a title
            Text title = new Text("Segnala un messaggio");
            title.setFont(Font.font(24));

            // Create via field
            TextField IDMField = new TextField();
            IDMField.setPromptText("ID_Messaggio");

            // Create numero field
            TextField motivazioneField = new TextField();
            motivazioneField.setPromptText("Motivazione");

            // Create login button
            Button inserisciButton = new Button("Inserisci");
            inserisciButton.setOnAction(ev -> {
                String IDM = IDMField.getText();
                String motivazione = motivazioneField.getText();
                if (isIDMValid(IDM) && isMotivazioneValid(motivazione)) {
                    // segnalazione del messaggio
                    String query = "INSERT INTO segnalazione (ID_Messaggio, Email, Motivazione) VALUES ('" + IDM
                            + "', '"
                            + email + "', '" + motivazione + "')";
                    try {
                        Statement stmt = MySQLConnect.getConnection().createStatement();
                        stmt.executeUpdate(query);
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle(null);
                        alert.setHeaderText("Successo");
                        alert.setContentText("Messaggio segnalato con successo");
                        alert.showAndWait();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
            // Create back button
            Button backButton = new Button("Back");
            backButton.setOnAction(exc -> {
                try {
                    previousTab.show();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });

            // Add components to the layout
            root.getChildren().addAll(title, IDMField, motivazioneField, inserisciButton, backButton);

            Scene scene = new Scene(root, 400, 300);
            primaryStage.setTitle("Animaletti Coccolosi");
            primaryStage.setScene(scene);
            primaryStage.show();
        });
    }

    private boolean isMotivazioneValid(String motivazione) {
        if (motivazione.length() > 0 && motivazione.length() < 200) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText(null);
            alert.setContentText("Motivazione non valida");
            alert.showAndWait();
            return false;
        }
    }

    private boolean isIDMValid(String iDM) {
        // check if a message with such id exists
        String query = "SELECT * FROM messaggio WHERE ID_Messaggio = '" + iDM + "'";
        try {
            Statement stmt = MySQLConnect.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                return true;
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Errore");
                alert.setHeaderText(null);
                alert.setContentText("Messaggio non trovato");
                alert.showAndWait();
                return false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
