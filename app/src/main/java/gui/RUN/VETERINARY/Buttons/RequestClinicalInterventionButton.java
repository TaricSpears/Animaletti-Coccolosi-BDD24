package gui.RUN.VETERINARY.Buttons;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import database.MySQLConnect;
import gui.Tab;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class RequestClinicalInterventionButton extends Button {

    public RequestClinicalInterventionButton(final Stage primaryStage, final String email, final Tab previousTab) {
        this.setText("Richiedi intervento clinico per un paziente");
        this.setOnAction(click -> {
            VBox root = new VBox();
            root.setAlignment(Pos.CENTER);
            root.setSpacing(10);

            // aggiungi un titolo nper la finestra
            Text title = new Text();
            title.setText("Richiedi intervento clinico per un paziente");

            TextField codField = new TextField();
            codField.setPromptText("Codice Identificativo");

            TextField descField = new TextField();
            descField.setPromptText("Descrizione");

            TextField hourField = new TextField();
            hourField.setPromptText("Ora");

            DatePicker dateField = new DatePicker();
            dateField.setPromptText("Data");

            Button backButton = new Button("Back");
            backButton.setOnAction(e -> {
                try {
                    previousTab.show();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            });

            Button richiediButton = new Button("Richiedi");
            richiediButton.setOnAction(e -> {
                if (isCodValid(codField.getText(), email) && isDescValid(descField.getText())
                        && isDHValid(dateField.getValue(), hourField.getText(), email)) {
                    String query = "INSERT INTO Intervento (Codice_Identificativo, Data, Ora, Descrizione, Email) values (?, ?, ?, ?, ?)";
                    try {
                        PreparedStatement stmt = MySQLConnect.getConnection().prepareStatement(query);
                        stmt.setString(1, codField.getText());
                        stmt.setDate(2, Date.valueOf(dateField.getValue()));
                        stmt.setString(3, hourField.getText());
                        stmt.setString(4, descField.getText());
                        stmt.setString(5, email);
                        stmt.executeUpdate();
                        new Alert(Alert.AlertType.INFORMATION, "Intervento richiesto con successo").showAndWait();
                    } catch (Exception ex) {
                        new Alert(Alert.AlertType.ERROR, "Errore durante la richiesta dell'intervento").showAndWait();
                        ex.printStackTrace();
                    }
                }
            });

            root.getChildren().addAll(title, codField, descField, hourField, dateField, richiediButton,
                    backButton);
            Scene scene = new Scene(root, 300, 300);
            primaryStage.setScene(scene);
            primaryStage.show();
        });

    }

    private boolean isDHValid(final LocalDate data, final String ora, final String email) {
        if (data == null) {
            new Alert(Alert.AlertType.ERROR, "Inserire una data").showAndWait();
            return false;
        }
        if (ora.length() == 0 || ora.length() > 2 || !ora.matches("[0-9]+")) {
            new Alert(Alert.AlertType.ERROR, "Inserire un'ora (2 cifre)").showAndWait();
            return false;
        }
        String query2 = "SELECT * " +
                "FROM Intervento i JOIN Accettazione_I ai on i.ID_intervento = ai.ID_intervento " +
                "WHERE i.Email = ? " +
                "AND i.Data = ? " +
                "AND i.Ora = ?";
        try {
            PreparedStatement preparedStatement = MySQLConnect.getConnection().prepareStatement(query2);
            preparedStatement.setString(1, email);
            preparedStatement.setDate(2, Date.valueOf(data));
            preparedStatement.setString(3, ora);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                new Alert(Alert.AlertType.ERROR, "Hai gia' un intervento in quella data e ora").showAndWait();
                return false;
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Errore durante la verifica della data e ora").showAndWait();
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private boolean isDescValid(final String descField) {
        if (descField.length() == 0 || descField.length() > 200) {
            new Alert(Alert.AlertType.ERROR, "La descrizione deve essere da 0 a 200 caratteri").showAndWait();
            return false;
        }
        return true;
    }

    private boolean isCodValid(final String codiceAnimale, final String email) {
        if (codiceAnimale.length() == 0 || codiceAnimale.length() > 5) {
            new Alert(Alert.AlertType.ERROR, "Il codice identificativo deve essere da 0 a 5 caratteri").showAndWait();
            return false;
        }
        String query = "SELECT v.* FROM visita v WHERE v.Codice_Identificativo = ? AND v.ACC_Email = ?";
        try {
            PreparedStatement preparedStatement = MySQLConnect.getConnection().prepareStatement(query);
            preparedStatement.setString(1, codiceAnimale);
            preparedStatement.setString(2, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                new Alert(Alert.AlertType.ERROR, "L'animale non esiste o non e' tuo paziente").showAndWait();
                return false;
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Errore durante la verifica del codice identificativo").showAndWait();
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
