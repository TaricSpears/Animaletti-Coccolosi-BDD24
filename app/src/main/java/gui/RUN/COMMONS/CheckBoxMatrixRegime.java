package gui.RUN.COMMONS;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

import database.MySQLConnect;
import gui.Tab;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CheckBoxMatrixRegime {

    private static final int SIZE = 7;
    private ArrayList<String> DAYS;
    private ArrayList<String> REGIMI;
    private String arg;

    public CheckBoxMatrixRegime() {
        this.arg = "Regime";
        this.DAYS = new ArrayList<String>(
                List.of("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"));
        this.REGIMI = new ArrayList<String>(
                List.of(arg + "1", arg + "2", arg + "3", arg + "4", arg + "5", arg + "6", arg + "7"));
    }

    public void show(Stage primaryStage, Tab previousTab, int Codice_Terapia) {
        GridPane grid = new GridPane();
        CheckBox[][] checkBoxes = new CheckBox[SIZE][SIZE];

        // Add day labels
        for (int col = 0; col < SIZE; col++) {
            Label dayLabel = new Label(DAYS.get(col));
            grid.add(dayLabel, col + 1, 0);
        }

        // Add regime labels and checkboxes
        for (int row = 0; row < SIZE; row++) {
            Label regimeLabel = new Label(REGIMI.get(row));
            grid.add(regimeLabel, 0, row + 1);

            for (int col = 0; col < SIZE; col++) {
                CheckBox checkBox = new CheckBox();
                checkBoxes[row][col] = checkBox;
                grid.add(checkBox, col + 1, row + 1);
            }
        }

        Button insertButton = new Button("Insert");
        insertButton.setOnAction(event -> {
            Map<String, String> regimiSchedule = new HashMap<>();
            if (validateMatrix(checkBoxes, regimiSchedule)) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Regimi registrati correttamente!");
                alert.setHeaderText("Regimi registrati correttamente!");
                alert.showAndWait();

                long nRegimi = regimiSchedule.values().stream().distinct().count();
                handleRegimeEntry(0, nRegimi, regimiSchedule, Codice_Terapia, primaryStage, previousTab);
            } else {
                showAlert(AlertType.ERROR, "Errore", "Ogni colonna deve avere esattamente un regime selezionato!");
            }
        });

        Button backButton = new Button("Indietro");
        backButton.setOnAction(event -> {
            try {
                previousTab.show();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        VBox vbox = new VBox(10, grid, insertButton, backButton);
        Scene scene = new Scene(vbox, 500, 500);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Checkbox Matrix Regime");
        primaryStage.show();
    }

    private void handleRegimeEntry(int currentIndex, long nRegimi, Map<String, String> regimiSchedule,
            int Codice_Terapia,
            Stage primaryStage, Tab previousTab) {
        if (currentIndex >= nRegimi) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Regimi registrati correttamente!");
            alert.setHeaderText("Regimi registrati correttamente!");
            alert.showAndWait();
            try {
                previousTab.show();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return;
        }

        // Create a VBox layout
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(20);

        // Create a title
        Text myTitle = new Text("Regime" + (currentIndex + 1));
        myTitle.setFont(Font.font(24));

        // Create description field
        TextField descrizioneField = new TextField();
        descrizioneField.setPromptText("Descrizione");

        // Create next button
        Button nextButton = new Button("Inserisci");

        nextButton.setOnAction(e -> {
            if (descrizioneField.getText().length() == 0 || descrizioneField.getText().length() > 100) {
                showAlert(AlertType.ERROR, "Errore", "Lunghezza descrizione non valida");
            } else {
                String descrizione = descrizioneField.getText();
                // Insert into regime
                String query = "INSERT INTO regime_farmacologico (Descrizione) VALUES (?)";
                try {
                    PreparedStatement preparedStatement = MySQLConnect.getConnection().prepareStatement(query);
                    preparedStatement.setString(1, descrizione);
                    preparedStatement.executeUpdate();
                } catch (Exception ex) {
                    showAlert(AlertType.ERROR, "Errore", "Errore durante l'inserimento del regime");
                    ex.printStackTrace();
                }

                // Get last inserted regime id
                String query2 = "SELECT Codice_Regime FROM regime_farmacologico ORDER BY Codice_Regime DESC LIMIT 1";
                int Codice_Regime = 0;
                try {
                    PreparedStatement preparedStatement = MySQLConnect.getConnection().prepareStatement(query2);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    resultSet.next();
                    Codice_Regime = resultSet.getInt("Codice_Regime");
                } catch (Exception ex) {
                    showAlert(AlertType.ERROR, "Errore", "Errore durante l'ottenimento dell'id del regime");
                    ex.printStackTrace();
                }

                // Insert into comprensione
                String query3 = "INSERT INTO Prescrizione_A (Codice_Terapia, Codice_Regime) VALUES (?, ?)";
                try {
                    PreparedStatement preparedStatement = MySQLConnect.getConnection().prepareStatement(query3);
                    preparedStatement.setInt(1, Codice_Terapia);
                    preparedStatement.setInt(2, Codice_Regime);
                    preparedStatement.executeUpdate();
                } catch (Exception ex) {
                    showAlert(AlertType.ERROR, "Errore", "Errore durante l'inserimento della prescrizione regime");
                    ex.printStackTrace();
                }

                // insert into occorrenza_m the regime of each day
                for (Map.Entry<String, String> entry : regimiSchedule.entrySet()) {
                    if (entry.getValue().equals(REGIMI.get(currentIndex))) {
                        System.out.println(entry.getKey() + " " + entry.getValue());
                        String codiceGiorno = "0";
                        switch (entry.getKey()) {
                            case "Monday":
                                codiceGiorno = "1";
                                break;
                            case "Tuesday":
                                codiceGiorno = "2";
                                break;
                            case "Wednesday":
                                codiceGiorno = "3";
                                break;
                            case "Thursday":
                                codiceGiorno = "4";
                                break;
                            case "Friday":
                                codiceGiorno = "5";
                                break;
                            case "Saturday":
                                codiceGiorno = "6";
                                break;
                            case "Sunday":
                                codiceGiorno = "7";
                                break;
                        }
                        String query4 = "INSERT INTO occorrenza_f (Codice_Giorno, Codice_Regime) VALUES (?, ?)";
                        try {
                            PreparedStatement preparedStatement = MySQLConnect.getConnection().prepareStatement(query4);
                            preparedStatement.setString(1, codiceGiorno);
                            preparedStatement.setInt(2, Codice_Regime);
                            preparedStatement.executeUpdate();
                        } catch (Exception ex) {
                            showAlert(AlertType.ERROR, "Errore",
                                    "Errore durante l'inserimento dell'occorrenza regime");
                            ex.printStackTrace();
                        }
                    }
                }

                // Handle integrations
                handleAssunzione(Codice_Regime, currentIndex, primaryStage, () -> {
                    handleRegimeEntry(currentIndex + 1, nRegimi, regimiSchedule, Codice_Terapia, primaryStage,
                            previousTab);
                });
            }
        });

        root.getChildren().addAll(myTitle, descrizioneField, nextButton);

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setTitle("Animaletti Coccolosi");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handleAssunzione(int Codice_Regime, int regimeIndex, Stage primaryStage, Runnable onComplete) {
        int[] nAssunzioni = { 1 }; // Use an array to allow modification within the lambda

        BooleanProperty continueLoop = new SimpleBooleanProperty(true);

        while (continueLoop.get()) {
            // Create a VBox layout
            VBox rootInt = new VBox();
            rootInt.setAlignment(Pos.CENTER);
            rootInt.setSpacing(20);

            // Create a title
            Text title = new Text("Assunzione" + nAssunzioni[0] + " di Farmaco" + (regimeIndex + 1));
            title.setFont(Font.font(24));

            // Create fields
            TextField nomeField = new TextField();
            nomeField.setPromptText("Nome Farmaco");

            TextField frequenzaField = new TextField();
            frequenzaField.setPromptText("Frequenza");

            TextField quantitaField = new TextField();
            quantitaField.setPromptText("Quantità");

            CheckBox ultimaIntegrazione = new CheckBox("Questa è l'ultima assunzione del Regime");

            // Create insert button
            Button inserisciButton = new Button("Inserisci");
            inserisciButton.setOnAction(ev -> {
                if (isValid(nomeField.getText()) && areValid(frequenzaField.getText(), quantitaField.getText())) {
                    // Check if food already exists in this regime integration
                    String query7 = "SELECT * FROM assunzione WHERE Codice_Regime = ? AND Nome = ?";
                    try {
                        PreparedStatement preparedStatement = MySQLConnect.getConnection().prepareStatement(query7);
                        preparedStatement.setInt(1, Codice_Regime);
                        preparedStatement.setString(2, nomeField.getText());
                        ResultSet resultSet = preparedStatement.executeQuery();
                        if (resultSet.next()) {
                            showAlert(AlertType.ERROR, "Errore", "Farmaco già presente in questo regime");
                            return;
                        }
                    } catch (Exception ex) {
                        showAlert(AlertType.ERROR, "Errore", "Errore durante la verifica della assunzione regime");
                        ex.printStackTrace();
                    }

                    // Insert copmosizione
                    String query4 = "INSERT INTO assunzione (Nome, Frequenza, Quantita, Codice_Regime) VALUES (?, ?, ?, ?)";
                    try {
                        PreparedStatement preparedStatement = MySQLConnect.getConnection().prepareStatement(query4);
                        preparedStatement.setString(1, nomeField.getText());
                        preparedStatement.setInt(2, Integer.parseInt(frequenzaField.getText()));
                        preparedStatement.setDouble(3, Double.parseDouble(quantitaField.getText()));
                        preparedStatement.setInt(4, Codice_Regime);
                        preparedStatement.executeUpdate();
                        Alert alert2 = new Alert(AlertType.INFORMATION);
                        alert2.setTitle("Assunzione registrata correttamente!");
                        alert2.setHeaderText("Assunzione registrata correttamente!");
                        alert2.showAndWait();
                    } catch (Exception ex) {
                        showAlert(AlertType.ERROR, "Errore", "Errore durante l'inserimento dell'Assunzione");
                        ex.printStackTrace();
                    }
                }
                if (ultimaIntegrazione.isSelected()) {
                    continueLoop.set(false);
                    onComplete.run();
                }
            });

            // Add components to the layout
            rootInt.getChildren().addAll(title, nomeField, frequenzaField, quantitaField, ultimaIntegrazione,
                    inserisciButton);

            Scene scene = new Scene(rootInt, 400, 300);
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Inserisci Integrazione" + nAssunzioni[0] + " di Regime" + (regimeIndex + 1));
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            dialogStage.setScene(scene);
            dialogStage.showAndWait();

            // Reset the primary stage for the next iteration
            if (continueLoop.get()) {
                nAssunzioni[0]++;
            }
        }
    }

    private boolean areValid(String frequenza, String quantita) {
        if (frequenza.length() == 0 || quantita.length() == 0 || frequenza.length() > 3 || quantita.length() > 7
                || !frequenza.matches("[0-9]+") || !quantita.matches("[+-]?([0-9]*[.])?[0-9]+")) {
            showAlert(AlertType.ERROR, "Errore", "Inserisci frequenza e quantita valide");
            return false;
        }
        return true;
    }

    private boolean isValid(String nomeFarmaco) {
        if (nomeFarmaco.length() == 0 || nomeFarmaco.length() > 20) {
            showAlert(AlertType.ERROR, "Errore", "Lunghezza nome farmaco non valida");
            return false;
        }
        String query = "SELECT * FROM farmaco WHERE Nome = ?";
        try {
            PreparedStatement preparedStatement = MySQLConnect.getConnection().prepareStatement(query);
            preparedStatement.setString(1, nomeFarmaco);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                showAlert(AlertType.ERROR, "Errore", "Il famraco non esiste");
                return false;
            }
        } catch (Exception e) {
            showAlert(AlertType.ERROR, "Errore", "Errore durante la verifica del farmaco");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private boolean validateMatrix(CheckBox[][] checkBoxes, Map<String, String> regimiSchedule) {
        for (int col = 0; col < SIZE; col++) {
            int checkedCount = 0;
            String regimeForDay = null;
            for (int row = 0; row < SIZE; row++) {
                if (checkBoxes[row][col].isSelected()) {
                    checkedCount++;
                    regimeForDay = REGIMI.get(row);
                }
            }
            if (checkedCount != 1) {
                return false;
            }
            regimiSchedule.put(DAYS.get(col), regimeForDay);
        }
        return true;
    }

    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}