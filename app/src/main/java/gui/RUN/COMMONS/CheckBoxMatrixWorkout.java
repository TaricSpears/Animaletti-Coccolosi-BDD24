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

public class CheckBoxMatrixWorkout {

    private static final int SIZE = 7;
    private ArrayList<String> DAYS;
    private ArrayList<String> WORKOUTS;
    private String arg;

    public CheckBoxMatrixWorkout() {
        this.arg = "Workout";
        this.DAYS = new ArrayList<String>(
                List.of("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"));
        this.WORKOUTS = new ArrayList<String>(
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

        // Add workout labels and checkboxes
        for (int row = 0; row < SIZE; row++) {
            Label workoutLabel = new Label(WORKOUTS.get(row));
            grid.add(workoutLabel, 0, row + 1);

            for (int col = 0; col < SIZE; col++) {
                CheckBox checkBox = new CheckBox();
                checkBoxes[row][col] = checkBox;
                grid.add(checkBox, col + 1, row + 1);
            }
        }

        Button insertButton = new Button("Insert");
        insertButton.setOnAction(event -> {
            Map<String, String> workoutSchedule = new HashMap<>();
            if (validateMatrix(checkBoxes, workoutSchedule)) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Workouts registrati correttamente!");
                alert.setHeaderText("Workouts registrati correttamente!");
                alert.showAndWait();

                long nWorkout = workoutSchedule.values().stream().distinct().count();
                handleWorkoutEntry(0, nWorkout, workoutSchedule, Codice_Terapia, primaryStage, previousTab);
            } else {
                showAlert(AlertType.ERROR, "Errore", "Ogni colonna deve avere esattamente un workout selezionato!");
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
        primaryStage.setTitle("Checkbox Matrix Workout");
        primaryStage.show();
    }

    private void handleWorkoutEntry(int currentIndex, long nWorkout, Map<String, String> workoutSchedule,
            int Codice_Terapia,
            Stage primaryStage, Tab previousTab) {
        if (currentIndex >= nWorkout) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Workouts registrati correttamente!");
            alert.setHeaderText("Workouts registrati correttamente!");
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
        Text myTitle = new Text("Workout" + (currentIndex + 1));
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
                // Insert into workout
                String query = "INSERT INTO workout (Descrizione) VALUES (?)";
                try {
                    PreparedStatement preparedStatement = MySQLConnect.getConnection().prepareStatement(query);
                    preparedStatement.setString(1, descrizione);
                    preparedStatement.executeUpdate();
                } catch (Exception ex) {
                    showAlert(AlertType.ERROR, "Errore", "Errore durante l'inserimento del workout");
                    ex.printStackTrace();
                }

                // Get last inserted wokrout id
                String query2 = "SELECT Codice_Workout FROM workout ORDER BY Codice_Workout DESC LIMIT 1";
                int Codice_Workout = 0;
                try {
                    PreparedStatement preparedStatement = MySQLConnect.getConnection().prepareStatement(query2);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    resultSet.next();
                    Codice_Workout = resultSet.getInt("Codice_Workout");
                } catch (Exception ex) {
                    showAlert(AlertType.ERROR, "Errore", "Errore durante l'ottenimento dell'id del workout");
                    ex.printStackTrace();
                }

                // Insert into comprensione
                String query3 = "INSERT INTO Prescrizione_E (Codice_Terapia, Codice_Workout) VALUES (?, ?)";
                try {
                    PreparedStatement preparedStatement = MySQLConnect.getConnection().prepareStatement(query3);
                    preparedStatement.setInt(1, Codice_Terapia);
                    preparedStatement.setInt(2, Codice_Workout);
                    preparedStatement.executeUpdate();
                } catch (Exception ex) {
                    showAlert(AlertType.ERROR, "Errore", "Errore durante l'inserimento della prescrizione workout");
                    ex.printStackTrace();
                }

                // insert into occorrenza_m the workout of each day
                for (Map.Entry<String, String> entry : workoutSchedule.entrySet()) {
                    if (entry.getValue().equals(WORKOUTS.get(currentIndex))) {
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
                        String query4 = "INSERT INTO occorrenza_w (Codice_Giorno, Codice_Workout) VALUES (?, ?)";
                        try {
                            PreparedStatement preparedStatement = MySQLConnect.getConnection().prepareStatement(query4);
                            preparedStatement.setString(1, codiceGiorno);
                            preparedStatement.setInt(2, Codice_Workout);
                            preparedStatement.executeUpdate();
                        } catch (Exception ex) {
                            showAlert(AlertType.ERROR, "Errore",
                                    "Errore durante l'inserimento dell'occorrenza workout");
                            ex.printStackTrace();
                        }
                    }
                }

                // Handle integrations
                handleCompositionsW(Codice_Workout, currentIndex, primaryStage, () -> {
                    handleWorkoutEntry(currentIndex + 1, nWorkout, workoutSchedule, Codice_Terapia, primaryStage,
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

    private void handleCompositionsW(int Codice_Workout, int workoutIndex, Stage primaryStage, Runnable onComplete) {
        int[] nIntegrazioni = { 1 }; // Use an array to allow modification within the lambda

        BooleanProperty continueLoop = new SimpleBooleanProperty(true);

        while (continueLoop.get()) {
            // Create a VBox layout
            VBox rootInt = new VBox();
            rootInt.setAlignment(Pos.CENTER);
            rootInt.setSpacing(20);

            // Create a title
            Text title = new Text("Composizione" + nIntegrazioni[0] + " di Esercizio" + (workoutIndex + 1));
            title.setFont(Font.font(24));

            // Create fields
            TextField nomeField = new TextField();
            nomeField.setPromptText("Nome Esercizio");

            TextField frequenzaField = new TextField();
            frequenzaField.setPromptText("Frequenza");

            TextField quantitaField = new TextField();
            quantitaField.setPromptText("Quantità");

            CheckBox ultimaIntegrazione = new CheckBox("Questa è l'ultima composizione del Workout");

            // Create insert button
            Button inserisciButton = new Button("Inserisci");
            inserisciButton.setOnAction(ev -> {
                if (isValid(nomeField.getText()) && areValid(frequenzaField.getText(), quantitaField.getText())) {
                    // Check if exercise already exists in this workout integration
                    String query7 = "SELECT * FROM composizione_w WHERE Codice_Workout = ? AND Nome = ?";
                    try {
                        PreparedStatement preparedStatement = MySQLConnect.getConnection().prepareStatement(query7);
                        preparedStatement.setInt(1, Codice_Workout);
                        preparedStatement.setString(2, nomeField.getText());
                        ResultSet resultSet = preparedStatement.executeQuery();
                        if (resultSet.next()) {
                            showAlert(AlertType.ERROR, "Errore", "Esercizio già presente in questo workout");
                            return;
                        }
                    } catch (Exception ex) {
                        showAlert(AlertType.ERROR, "Errore", "Errore durante la verifica della composizione worokout");
                        ex.printStackTrace();
                    }

                    // Insert copmosizione
                    String query4 = "INSERT INTO composizione_w (Nome, Frequenza, Quantita, Codice_Workout) VALUES (?, ?, ?, ?)";
                    try {
                        PreparedStatement preparedStatement = MySQLConnect.getConnection().prepareStatement(query4);
                        preparedStatement.setString(1, nomeField.getText());
                        preparedStatement.setInt(2, Integer.parseInt(frequenzaField.getText()));
                        preparedStatement.setDouble(3, Double.parseDouble(quantitaField.getText()));
                        preparedStatement.setInt(4, Codice_Workout);
                        preparedStatement.executeUpdate();
                        Alert alert2 = new Alert(AlertType.INFORMATION);
                        alert2.setTitle("Composizione registrata correttamente!");
                        alert2.setHeaderText("Composizione registrata correttamente!");
                        alert2.showAndWait();
                    } catch (Exception ex) {
                        showAlert(AlertType.ERROR, "Errore", "Errore durante l'inserimento della Composizione");
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
            dialogStage.setTitle("Inserisci Integrazione" + nIntegrazioni[0] + " di Workout" + (workoutIndex + 1));
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            dialogStage.setScene(scene);
            dialogStage.showAndWait();

            // Reset the primary stage for the next iteration
            if (continueLoop.get()) {
                nIntegrazioni[0]++;
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

    private boolean isValid(String nomeEsercizio) {
        if (nomeEsercizio.length() == 0 || nomeEsercizio.length() > 20) {
            showAlert(AlertType.ERROR, "Errore", "Lunghezza nome esercizio non valida");
            return false;
        }
        String query = "SELECT * FROM esercizio WHERE Nome = ?";
        try {
            PreparedStatement preparedStatement = MySQLConnect.getConnection().prepareStatement(query);
            preparedStatement.setString(1, nomeEsercizio);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                showAlert(AlertType.ERROR, "Errore", "L'esercizio non esiste");
                return false;
            }
        } catch (Exception e) {
            showAlert(AlertType.ERROR, "Errore", "Errore durante la verifica dell 'esercizio");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private boolean validateMatrix(CheckBox[][] checkBoxes, Map<String, String> workoutSchedule) {
        for (int col = 0; col < SIZE; col++) {
            int checkedCount = 0;
            String workoutForDay = null;
            for (int row = 0; row < SIZE; row++) {
                if (checkBoxes[row][col].isSelected()) {
                    checkedCount++;
                    workoutForDay = WORKOUTS.get(row);
                }
            }
            if (checkedCount != 1) {
                return false;
            }
            workoutSchedule.put(DAYS.get(col), workoutForDay);
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