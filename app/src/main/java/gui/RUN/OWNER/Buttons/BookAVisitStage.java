package gui.RUN.OWNER.Buttons;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import database.MySQLConnect;
import gui.RUN.OWNER.OwnerTab;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BookAVisitStage {
    private final Stage primaryStage;
    private String email;

    public BookAVisitStage(Stage primaryStage, String email) {
        this.primaryStage = primaryStage;
        this.email = email;
    }

    public void show() {
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(10);

        TextField codField = new TextField();
        codField.setPromptText("Codice Identificativo");

        TextField descField = new TextField();
        descField.setPromptText("Descrizione");

        TextField urgencyField = new TextField();
        urgencyField.setPromptText("Urgenza");

        TextField veterinaryField = new TextField();
        veterinaryField.setPromptText("Veterinario (opzionale)");

        TextField hourField = new TextField();
        hourField.setPromptText("Ora");

        DatePicker dateField = new DatePicker();
        dateField.setPromptText("Data");

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            OwnerTab ownerTab = new OwnerTab(primaryStage, email);
            try {
                ownerTab.show();
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Errore durante il caricamento della pagina").showAndWait();
            }
        });

        Button bookButton = new Button("Prenota");
        bookButton.setOnAction(e -> {
            String query = "SELECT * FROM Animale WHERE Codice_Identificativo = ? AND Email = ?";
            try {
                PreparedStatement stmt = MySQLConnect.getConnection().prepareStatement(query);
                stmt.setInt(1, Integer.parseInt(codField.getText()));
                stmt.setString(2, email);
                ResultSet rs = stmt.executeQuery();
                if (!rs.next()) {
                    new Alert(Alert.AlertType.ERROR, "Non possiedi un animale con questo codice identificativo")
                            .showAndWait();
                    return;
                }
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Errore durante la verifica del codice identificativo").showAndWait();
                return;
            }

            if (codField.getText().isEmpty() || codField.getText().length() > 5 || descField.getText().isEmpty()
                    || descField.getText().length() > 100 || urgencyField.getText().isEmpty()
                    || urgencyField.getText().length() > 1
                    || hourField.getText().isEmpty() || hourField.getText().length() > 2
                    || !hourField.getText().matches("[0-9]+")
                    || dateField.getValue() == null
                    || veterinaryField.getText().length() > 25 || !isValid(veterinaryField.getText())) {
                new Alert(Alert.AlertType.ERROR, "Compila tutti i campi correttamente").showAndWait();
                return;
            }

            if (veterinaryField.getText().isEmpty()) {
                try {
                    PreparedStatement stmt = MySQLConnect.getConnection()
                            .prepareStatement(
                                    "INSERT INTO Visita (Data, Ora, Descrizione, Urgenza, Codice_Identificativo) VALUES (?, ?, ?, ?, ?)");
                    stmt.setDate(1, Date.valueOf(dateField.getValue()));
                    stmt.setString(2, hourField.getText());
                    stmt.setString(3, descField.getText());
                    stmt.setString(4, urgencyField.getText());
                    stmt.setInt(5, Integer.parseInt(codField.getText()));

                    stmt.executeUpdate();
                    new Alert(Alert.AlertType.INFORMATION, "Prenotazione effettuata").showAndWait();
                } catch (Exception ex) {
                    new Alert(Alert.AlertType.ERROR, "Errore durante la prenotazione").showAndWait();
                    ex.printStackTrace();
                }
            } else {
                try {
                    PreparedStatement stmt = MySQLConnect.getConnection()
                            .prepareStatement(
                                    "INSERT INTO Visita (Data, Ora, Descrizione, Urgenza, Codice_Identificativo, Email) VALUES (?, ?, ?, ?, ?, ?)");
                    stmt.setDate(1, Date.valueOf(dateField.getValue()));
                    stmt.setString(2, hourField.getText());
                    stmt.setString(3, descField.getText());
                    stmt.setString(4, urgencyField.getText());
                    stmt.setInt(5, Integer.parseInt(codField.getText()));
                    stmt.setString(6, veterinaryField.getText());

                    stmt.executeUpdate();
                    new Alert(Alert.AlertType.INFORMATION, "Prenotazione effettuata").showAndWait();
                } catch (Exception ex) {
                    new Alert(Alert.AlertType.ERROR, "Errore durante la prenotazione").showAndWait();
                    ex.printStackTrace();
                }
            }

        });

        root.getChildren().addAll(codField, descField, urgencyField, veterinaryField, hourField, dateField, bookButton,
                backButton);
        Scene scene = new Scene(root, 300, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private boolean isValid(String veterinaryEmail) {
        if (veterinaryEmail.isEmpty())
            return true;
        String query = "SELECT * FROM Veterinario WHERE Email = ?";
        try {
            PreparedStatement stmt = MySQLConnect.getConnection().prepareStatement(query);
            stmt.setString(1, veterinaryEmail);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (Exception ex) {
            new Alert(Alert.AlertType.ERROR, "Errore durante la verifica dell'email del veterinario").showAndWait();
            return false;
        }
    }

}
