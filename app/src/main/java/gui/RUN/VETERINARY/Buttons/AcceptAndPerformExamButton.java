package gui.RUN.VETERINARY.Buttons;

import java.util.Optional;

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
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AcceptAndPerformExamButton extends Button {
    public AcceptAndPerformExamButton(final String email, final Stage primaryStage, final Tab previousTab) {
        this.setText("Accetta e svolgi visita");
        this.setOnAction(click -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Accetta e svolgi visita");
            dialog.setHeaderText("Accetta e svolgi visita");
            dialog.setContentText("Inserisci id visita:");
            dialog.showAndWait().ifPresent(idVisita -> {
                // la visita selezionata deve appartenere al set di showExamRequestsButton
                String query1 = "SELECT * FROM visita v WHERE (v.ACC_Email is null) AND (v.Email is null OR v.Email = ?) AND (v.ID_visita = ?)";
                try {
                    Connection connection = MySQLConnect.getConnection();
                    PreparedStatement preparedStatement1 = connection.prepareStatement(query1);
                    preparedStatement1.setString(1, email);
                    preparedStatement1.setString(2, idVisita);
                    ResultSet resultSet = preparedStatement1.executeQuery();
                    if (!resultSet.next()) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Errore");
                        alert.setHeaderText("Errore");
                        alert.setContentText("La visita selezionata non e' presente tra le visite disponibili");
                        alert.showAndWait();
                        return;
                    }
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Errore");
                    alert.setHeaderText("Errore");
                    alert.setContentText("Errore durante la visualizzazione delle visite");
                    alert.showAndWait();
                    e.printStackTrace();
                    return;
                }
                // Data,Ora della visita selezionata devono essere diverse dalle data,ora delle
                // visite già accettate
                String query2 = "SELECT * " +
                        "FROM visita v " +
                        "WHERE v.ID_visita = ? " +
                        "AND NOT EXISTS (SELECT * " +
                        "FROM visita v1 " +
                        "WHERE ACC_Email = ? " +
                        "AND v1.Data = v.Data " +
                        "AND v1.Ora = v.Ora)";
                try {
                    Connection connection = MySQLConnect.getConnection();
                    PreparedStatement preparedStatement2 = connection.prepareStatement(query2);
                    preparedStatement2.setString(1, idVisita);
                    preparedStatement2.setString(2, email);
                    ResultSet resultSet = preparedStatement2.executeQuery();
                    if (!resultSet.next()) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Errore");
                        alert.setHeaderText("Errore");
                        alert.setContentText("Hai già accettato una visita per la stessa data e ora");
                        alert.showAndWait();
                        return;
                    }
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Errore");
                    alert.setHeaderText("Errore");
                    alert.setContentText("Errore durante la visualizzazione delle visite");
                    alert.showAndWait();
                    e.printStackTrace();
                    return;
                }
                // update visita set ACC_Email = email where id = idVisita
                String query3 = "UPDATE visita SET ACC_Email = ? WHERE ID_visita = ?";
                try {
                    Connection connection = MySQLConnect.getConnection();
                    PreparedStatement preparedStatement3 = connection.prepareStatement(query3);
                    preparedStatement3.setString(1, email);
                    preparedStatement3.setString(2, idVisita);
                    preparedStatement3.executeUpdate();
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Errore");
                    alert.setHeaderText("Errore");
                    alert.setContentText("Errore durante l'aggiornamento della visita");
                    alert.showAndWait();
                    e.printStackTrace();
                    return;
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Visita accettata ed eseguita");
                alert.setHeaderText("Visita accettata ed eseguita");
                alert.setContentText("Visita accettata ed eseguita con successo");
                alert.showAndWait();
                // ottieni id proprietario
                String query4 = "SELECT a.Email FROM animale a WHERE a.Codice_Identificativo = (SELECT v.Codice_Identificativo FROM visita v WHERE v.ID_visita = ?)";
                String emailProprietario = "";
                try {
                    Connection connection = MySQLConnect.getConnection();
                    PreparedStatement preparedStatement4 = connection.prepareStatement(query4);
                    preparedStatement4.setString(1, idVisita);
                    ResultSet resultSet = preparedStatement4.executeQuery();
                    resultSet.next();
                    emailProprietario = resultSet.getString(1);
                } catch (Exception e) {
                    Alert alert1 = new Alert(Alert.AlertType.ERROR);
                    alert1.setTitle("Errore");
                    alert1.setHeaderText("Errore");
                    alert1.setContentText("Errore durante l'ottenimento dell'email del proprietario");
                    alert1.showAndWait();
                    e.printStackTrace();
                    return;
                }
                // verifica se esiste già un gruppo con user e proprietario dell'animale
                if (email.equals(emailProprietario)) {
                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert1.setTitle("Informazione");
                    alert1.setHeaderText("Informazione");
                    alert1.setContentText("Siccome hai visitato un tuo animale non verra' creato il gruppo");
                    alert1.showAndWait();
                } else {
                    String query5 = "SELECT * from partecipazione p1, partecipazione p2 where p1.Email != p2.Email " +
                            "and p1.ID_Gruppo = p2.ID_Gruppo and p1.email = ? " +
                            "and p2.email = ? and p1.ID_gruppo != 1";
                    try {
                        Connection connection = MySQLConnect.getConnection();
                        PreparedStatement preparedStatement5 = connection.prepareStatement(query5);
                        preparedStatement5.setString(1, email);
                        preparedStatement5.setString(2, emailProprietario);
                        ResultSet resultSet = preparedStatement5.executeQuery();
                        if (!resultSet.next()) {
                            // crea gruppo
                            String query6 = "INSERT INTO gruppo (Nome, Data_apertura, Privato) VALUES (?, ?, 1)";
                            PreparedStatement preparedStatement6 = connection.prepareStatement(query6);
                            preparedStatement6.setString(1,
                                    "V: " + email.subSequence(0, 8) + " P: " + emailProprietario.subSequence(0, 8));
                            preparedStatement6.setString(2, java.time.LocalDate.now().toString());
                            preparedStatement6.executeUpdate();
                            // ottieni id gruppo
                            String query7 = "SELECT g.ID_Gruppo FROM gruppo g ORDER BY g.ID_Gruppo DESC LIMIT 1";
                            PreparedStatement preparedStatement7 = connection.prepareStatement(query7);
                            ResultSet resultSet7 = preparedStatement7.executeQuery();
                            resultSet7.next();
                            int idGruppo = resultSet7.getInt(1);
                            // inserisci partecipazione
                            String query8 = "INSERT INTO partecipazione (Email, ID_Gruppo) VALUES (?, ?)";
                            PreparedStatement preparedStatement8 = connection.prepareStatement(query8);
                            preparedStatement8.setString(1, email);
                            preparedStatement8.setInt(2, idGruppo);
                            preparedStatement8.executeUpdate();
                            PreparedStatement preparedStatement9 = connection.prepareStatement(query8);
                            preparedStatement9.setString(1, emailProprietario);
                            preparedStatement9.setInt(2, idGruppo);
                            preparedStatement9.executeUpdate();
                            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                            alert1.setTitle("Gruppo creato");
                            alert1.setHeaderText("Gruppo creato");
                            alert1.setContentText("Gruppo creato con successo");
                            alert1.showAndWait();
                        }
                    } catch (Exception e) {
                        Alert alert1 = new Alert(Alert.AlertType.ERROR);
                        alert1.setTitle("Errore");
                        alert1.setHeaderText("Errore");
                        alert1.setContentText(
                                "Errore durante la verifica dell'esistenza / creazione del gruppo tra te e il tuo paziente...");
                        alert1.setResizable(true);
                        alert1.showAndWait();
                        e.printStackTrace();
                        return;
                    }
                }
                // scegli se creare referto per questa visita + terapia + ...
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION, "Vuoi creare un referto per questa visita?",
                        ButtonType.YES, ButtonType.NO);
                alert2.setTitle("REFERTO");
                alert2.setHeaderText("REFERTO");
                Optional<ButtonType> result = alert2.showAndWait();

                if (result.get() == ButtonType.YES) {
                    TextInputDialog dialog1 = new TextInputDialog();
                    dialog1.setTitle("Referto");
                    dialog1.setHeaderText("Referto");
                    dialog1.setContentText("Inserisci la descrizione del referto:");
                    dialog1.showAndWait().ifPresent(descrizione -> {
                        if (descrizione.length() == 0 || descrizione.length() > 1000) {
                            Alert alert1 = new Alert(Alert.AlertType.ERROR);
                            alert1.setTitle("Errore");
                            alert1.setHeaderText("Errore");
                            alert1.setContentText("Descrizione del referto non valida");
                            alert1.showAndWait();
                            return;
                        }
                        String query10 = "SELECT v.Codice_Identificativo FROM visita v WHERE v.ID_visita = ?";
                        String codiceIdentificativo = "";
                        try {
                            Connection connection = MySQLConnect.getConnection();
                            PreparedStatement preparedStatement10 = connection.prepareStatement(query10);
                            preparedStatement10.setString(1, idVisita);
                            ResultSet resultSet = preparedStatement10.executeQuery();
                            resultSet.next();
                            codiceIdentificativo = resultSet.getString(1);
                        } catch (Exception e) {
                            Alert alert1 = new Alert(Alert.AlertType.ERROR);
                            alert1.setTitle("Errore");
                            alert1.setHeaderText("Errore");
                            alert1.setContentText("Errore durante l'ottenimento del codice identificativo");
                            alert1.showAndWait();
                            e.printStackTrace();
                            return;
                        }
                        String query9 = "INSERT INTO referto_clinico (ID_visita, Descrizione, Data_Emissione, Codice_Identificativo) VALUES (?, ?, ?, ?)";
                        try {
                            Connection connection = MySQLConnect.getConnection();
                            PreparedStatement preparedStatement9 = connection.prepareStatement(query9);
                            preparedStatement9.setString(1, idVisita);
                            preparedStatement9.setString(2, descrizione);
                            preparedStatement9.setString(3, java.time.LocalDate.now().toString());
                            preparedStatement9.setString(4, codiceIdentificativo);
                            preparedStatement9.executeUpdate();
                        } catch (Exception e) {
                            Alert alert1 = new Alert(Alert.AlertType.ERROR);
                            alert1.setTitle("Errore");
                            alert1.setHeaderText("Errore");
                            alert1.setContentText("Errore durante l'inserimento del referto");
                            alert1.showAndWait();
                            e.printStackTrace();
                            return;
                        }
                        // prendi id referto
                        String query11 = "SELECT r.Codice_Referto FROM referto_clinico r ORDER BY r.Codice_Referto DESC LIMIT 1";
                        int idReferto = 0;
                        try {
                            Connection connection = MySQLConnect.getConnection();
                            PreparedStatement preparedStatement11 = connection.prepareStatement(query11);
                            ResultSet resultSet = preparedStatement11.executeQuery();
                            resultSet.next();
                            idReferto = resultSet.getInt(1);
                        } catch (Exception e) {
                            Alert alert1 = new Alert(Alert.AlertType.ERROR);
                            alert1.setTitle("Errore");
                            alert1.setHeaderText("Errore");
                            alert1.setContentText("Errore durante l'ottenimento dell'id del referto");
                            alert1.showAndWait();
                            e.printStackTrace();
                            return;
                        }
                        Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                        alert1.setTitle("Referto inserito");
                        alert1.setHeaderText("Referto inserito");
                        alert1.setContentText("Referto inserito con successo");
                        alert1.showAndWait();
                        TextInputDialog dialog2 = new TextInputDialog();
                        dialog2.setTitle("Terapia");
                        dialog2.setHeaderText("Terapia");
                        dialog2.setContentText("Quante terapie determina questo refero?:");
                        dialog2.showAndWait().ifPresent(numeroTerapie -> {
                            if (numeroTerapie.length() == 0 || Integer.parseInt(numeroTerapie) < 0
                                    || !numeroTerapie.matches("[0-9]+")) {
                                Alert alert3 = new Alert(Alert.AlertType.ERROR);
                                alert3.setTitle("Errore");
                                alert3.setHeaderText("Errore");
                                alert3.setContentText("Numero di terapie non valido");
                                alert3.showAndWait();
                                return;
                            }
                            for (int i = 0; i < Integer.parseInt(numeroTerapie); i++) {
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

                                // Create login button
                                Button inserisciButton = new Button("Login");
                                inserisciButton.setOnAction(e -> {
                                    if (nomeField.getText().length() == 0 || nomeField.getText().length() > 20
                                            || descrizioneField.getText().length() == 0
                                            || descrizioneField.getText().length() > 100) {
                                        Alert alert3 = new Alert(Alert.AlertType.ERROR);
                                        alert3.setTitle("Errore");
                                        alert3.setHeaderText("Errore");
                                        alert3.setContentText("Nome o descrizione della terapia non validi");
                                        alert3.showAndWait();
                                        return;
                                    }

                                });

                                // Add components to the layout
                                root.getChildren().addAll(title, nomeField, descrizioneField, inserisciButton);

                                Scene scene = new Scene(root, 400, 300);
                                primaryStage.setTitle("Animaletti Coccolosi");
                                primaryStage.setScene(scene);
                                primaryStage.showAndWait();

                            }
                            try {
                                previousTab.show();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        });
                    });
                }
            });
        });
    }
}