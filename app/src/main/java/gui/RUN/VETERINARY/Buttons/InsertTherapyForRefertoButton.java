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

public class InsertTherapyForRefertoButton extends Button {
    public InsertTherapyForRefertoButton(final String email, final Stage primaryStage, final Tab previousTab) {
        this.setText("Inserisci terapia per referto");
        this.setOnAction(click -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Inserisci terapia per referto");
            dialog.setHeaderText("Inserisci il codice identificativo del referto");
            dialog.setContentText("Codice identificativo:");
            dialog.showAndWait().ifPresent(Codice_Referto -> {
                if (isValid(Codice_Referto, email)) {
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
                    dataInizio.setPromptText("Data inizio");

                    // create back button
                    Button backButton = new Button("Indietro");
                    backButton.setOnAction(e -> {
                        try {
                            previousTab.show();
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                    });

                    // Create login button
                    Button inserisciButton = new Button("Inserisci");
                    inserisciButton.setOnAction(e -> {
                        if (areValid(nomeField.getText(), descrizioneField.getText(),
                                dataInizio)) {

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
                        }
                    });

                    // Add components to the layout
                    root.getChildren().addAll(title, nomeField, descrizioneField, dataInizio, backButton,
                            inserisciButton);

                    Scene scene = new Scene(root, 400, 300);
                    primaryStage.setTitle("Animaletti Coccolosi");
                    primaryStage.setScene(scene);
                    primaryStage.show();
                }
            });
        });

    }

    private boolean isValid(final String Codice_Referto, final String email) {
        if (Codice_Referto.isEmpty() || Codice_Referto.length() > 4 || !Codice_Referto.matches("[0-9]+")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText("Errore nell'inserimento del codice identificativo");
            alert.setContentText("Il campo dev'essere un numero di 4 cifre");
            alert.showAndWait();
            return false;
        }
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
                alert.showAndWait();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String query2 = "SELECT r.ID_visita FROM referto_clinico r WHERE r.Codice_Referto = ?";
        String idVisita = "";
        try (Connection connection = MySQLConnect.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query2)) {

            preparedStatement.setString(1, Codice_Referto);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Errore");
                alert.setHeaderText("Errore");
                alert.setContentText("Il referto non e' associato ad alcuna visita");
                alert.showAndWait();
                return false;
            }
            idVisita = resultSet.getString("ID_visita");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        String query3 = "SELECT v.ACC_Email FROM visita v WHERE v.ID_visita = ?";
        try (Connection connection = MySQLConnect.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query3)) {

            preparedStatement.setString(1, idVisita);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Errore");
                alert.setHeaderText("Errore");
                alert.setContentText("La visita associata a questo referto non e' stata svolta da alcun medico!");
                alert.showAndWait();
                return false;
            }
            if (!resultSet.getString("ACC_Email").equals(email)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Errore");
                alert.setHeaderText("Errore");
                alert.setContentText("Non hai il permesso di inserire una terapia per questo referto");
                alert.showAndWait();
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    private boolean areValid(final String nome, final String descrizione, final DatePicker dataInizio) {
        if (nome.isEmpty() || nome.length() > 20) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText("Errore nell'inserimento del nome");
            alert.setContentText("Il campo dev'essere di massimo 20 caratteri");
            alert.showAndWait();
            return false;
        }
        if (descrizione.isEmpty() || descrizione.length() > 100) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText("Errore nell'inserimento della descrizione");
            alert.setContentText("Il campo dev'essere di massimo 100 caratteri");
            alert.showAndWait();
            return false;
        }
        if (dataInizio.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText("Errore nell'inserimento della data");
            alert.setContentText("Il campo non puo' essere vuoto");
            alert.showAndWait();
            return false;
        }
        return true;
    }
}
