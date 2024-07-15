//parcella:inserisci IDprop(per pagamento)e i campi della parcella.setta a false pagata

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

public class AddParcelButton extends Button {
    public AddParcelButton(final String email) {
        this.setText("Inserisci una nuova parcella");
        this.setOnAction(click -> {
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Inserisci i dati della parcella");

            Text title = new Text("Inserisci i dati della parcella:");
            title.setFont(new Font(20));

            TextField importo = new TextField();
            importo.setPromptText("Importo");

            TextField descrizione = new TextField();
            descrizione.setPromptText("Descrizione");

            TextField emailProprietario = new TextField();
            emailProprietario.setPromptText("Email proprietario");

            Button submit = new Button("Invia");
            submit.setOnAction(e -> {
                if (isValid(email, importo, descrizione, emailProprietario)) {
                    try {
                        String insertQuery = "INSERT INTO parcella (Importo, Descrizione, Email) VALUES (?, ?, ?)";
                        String selectCodParcella = "SELECT CodParcella FROM parcella ORDER BY CodParcella DESC LIMIT 1";

                        Connection connection = MySQLConnect.getConnection();
                        PreparedStatement preparedStatement3 = connection.prepareStatement(insertQuery);
                        preparedStatement3.setString(1, importo.getText());
                        preparedStatement3.setString(2, descrizione.getText());
                        preparedStatement3.setString(3, emailProprietario.getText());
                        preparedStatement3.executeUpdate();
                        ResultSet resultSet3 = preparedStatement3.executeQuery(selectCodParcella);
                        resultSet3.next();
                        String codParcella = resultSet3.getString("CodParcella");

                        String insertPagamento = "INSERT INTO pagamento (CodParcella, Pagata, Email) VALUES (?, 0, ?)";
                        PreparedStatement preparedStatement4 = connection.prepareStatement(insertPagamento);
                        preparedStatement4.setString(1, codParcella);
                        preparedStatement4.setString(2, emailProprietario.getText());
                        preparedStatement4.executeUpdate();

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Successo");
                        alert.setHeaderText("Successo");
                        alert.setContentText("Parcella inserita con successo");
                        alert.showAndWait();

                    } catch (Exception ex) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Errore");
                        alert.setHeaderText("Errore");
                        alert.setContentText("Errore durante l'inserimento della parcella");
                        alert.showAndWait();
                        ex.printStackTrace();
                    }
                }
            });
            Button quitButton = new Button("Chiudi");
            quitButton.setOnAction(e -> dialogStage.close());

            VBox vBox = new VBox(title, importo, descrizione, emailProprietario, submit, quitButton);
            vBox.setSpacing(10);
            vBox.setAlignment(Pos.CENTER);

            Scene scene = new Scene(vBox, 400, 400);
            dialogStage.setScene(scene);
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.show();
        });
    }

    private boolean isValid(String email, TextField importoField, TextField descrizioneField,
            TextField emailProprietarioField) {
        if (importoField.getText().isEmpty() || descrizioneField.getText().isEmpty()
                || emailProprietarioField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText("Errore");
            alert.setContentText("Inserisci tutti i campi");
            alert.showAndWait();
            return false;
        }
        String importo = importoField.getText();
        String emailProprietario = emailProprietarioField.getText();
        String descrizione = descrizioneField.getText();
        if (importo.length() > 7 || !importo.matches("[0-9]+")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText("Errore");
            alert.setContentText("L'importo deve essere un numero intero di massimo 7 cifre");
            alert.showAndWait();
            return false;
        }
        if (emailProprietario.length() > 25) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText("Errore");
            alert.setContentText("L'email del proprietario deve essere di massimo 25 caratteri");
            alert.showAndWait();
            return false;
        }
        if (descrizione.length() > 500) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText("Errore");
            alert.setContentText("La descrizione deve essere di massimo 500 caratteri");
            alert.showAndWait();
            return false;
        }
        // verifica che il medico abbia visitato almeno una volta un animale di
        // proprieta di email proprietario
        String query = "SELECT a.Email FROM animale a, visita v WHERE a.Codice_Identificativo = v.Codice_Identificativo AND v.ACC_Email = ? AND a.Email = ?";
        try (Connection connection = MySQLConnect.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, emailProprietario);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Errore");
                alert.setHeaderText("Errore");
                alert.setContentText("Il veterinario non ha visitato o operato un animale di proprieta dell'utente");
                alert.setResizable(true);
                alert.showAndWait();
                return false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return true;
    }
}
