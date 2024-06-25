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
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class UpdateMedicalFolderButton extends Button {
    public UpdateMedicalFolderButton(final String email, final Stage primaryStage, final Tab previousTab) {
        this.setText("Aggiorna cartella medica di un tuo paziente");
        this.setOnAction(click -> {
            // Create a VBox layout
            VBox root = new VBox();
            root.setAlignment(Pos.CENTER);
            root.setSpacing(20);

            // Create a title
            Text title = new Text("Animaletti Coccolosi");
            title.setFont(Font.font(24));

            // Create email field
            TextField CIDField = new TextField();
            CIDField.setPromptText("Codice Identificativo animale");

            // Create password field
            TextField condField = new TextField();
            condField.setPromptText("Condizione Clinica");

            // Create login button
            Button inserisciButton = new Button("Inserisci");
            inserisciButton.setOnAction(e -> {
                if (isValid(email, CIDField.getText(), condField.getText())) {
                    // aagiungi la condizione clinica all'animale
                    String query = "INSERT INTO assegnazione(Codice_Identificativo, Nome) VALUES (?, ?)";
                    try {
                        Connection connection = MySQLConnect.getConnection();
                        PreparedStatement preparedStatement = connection.prepareStatement(query);
                        preparedStatement.setString(1, CIDField.getText());
                        preparedStatement.setString(2, condField.getText());
                        preparedStatement.executeUpdate();
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Successo");
                        alert.setHeaderText("Successo");
                        alert.setContentText("Condizione clinica aggiunta con successo");
                        alert.setResizable(true);
                        alert.showAndWait();
                    } catch (Exception e1) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Errore");
                        alert.setHeaderText("Errore");
                        alert.setContentText("Errore durante l'aggiunta della condizione clinica");
                        alert.setResizable(true);
                        alert.showAndWait();
                        e1.printStackTrace();
                    }
                }
            });

            // Create back button
            Button backButton = new Button("Back");
            backButton.setOnAction(e -> {
                try {
                    previousTab.show();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            });

            // Add components to the layout
            root.getChildren().addAll(title, CIDField, condField, inserisciButton, backButton);

            Scene scene = new Scene(root, 400, 300);
            primaryStage.setTitle("Animaletti Coccolosi");
            primaryStage.setScene(scene);
            primaryStage.show();

        });
    }

    private boolean isValid(final String email, final String codiceIdentificativo, final String condizioneClinica) {
        boolean flag = false;
        // controllo che il codice identificativo corrisponda a un paziente
        String query1 = "SELECT v.* FROM visita v WHERE v.Codice_Identificativo = ? AND v.ACC_Email = ?";
        try {
            Connection connection = MySQLConnect.getConnection();
            PreparedStatement preparedStatement1 = connection.prepareStatement(query1);
            preparedStatement1.setString(1, codiceIdentificativo);
            preparedStatement1.setString(2, email);
            ResultSet rs1 = preparedStatement1.executeQuery();
            if (rs1.next()) {// l'animale è tuo paziente
                // controllo che la condizione clinica esista
                String query2 = "SELECT c.Nome FROM condizione_clinica c WHERE c.Nome = ?";
                PreparedStatement preparedStatement2 = connection.prepareStatement(query2);
                preparedStatement2.setString(1, condizioneClinica);
                ResultSet rs2 = preparedStatement2.executeQuery();
                if (rs2.next()) {// la condizione clinica esiste
                    // controllo che l'animale non abbia già la condizione clinica
                    String query3 = "SELECT * FROM assegnazione a WHERE a.Codice_Identificativo = ? AND a.Nome = ?";
                    PreparedStatement preparedStatement3 = connection.prepareStatement(query3);
                    preparedStatement3.setString(1, codiceIdentificativo);
                    preparedStatement3.setString(2, condizioneClinica);
                    ResultSet rs3 = preparedStatement3.executeQuery();
                    if (!rs3.next()) {
                        flag = true;
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Errore");
                        alert.setHeaderText("Errore");
                        alert.setContentText(
                                "L'animale ha già questa condizione clinica");
                        alert.setResizable(true);
                        alert.showAndWait();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Errore");
                    alert.setHeaderText("Errore");
                    alert.setContentText(
                            "La condizione clinica non esiste");
                    alert.setResizable(true);
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Errore");
                alert.setHeaderText("Errore");
                alert.setContentText(
                        "L'animale non esiste o non è tuo paziente");
                alert.setResizable(true);
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
}
