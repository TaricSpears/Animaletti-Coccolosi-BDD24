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

public class CheckboxMatrixMenu {

    private static final int SIZE = 7;
    private ArrayList<String> DAYS;
    private ArrayList<String> MENUS;
    private String arg;

    public CheckboxMatrixMenu() {
        this.arg = "Menu";
        this.DAYS = new ArrayList<String>(
                List.of("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"));
        this.MENUS = new ArrayList<String>(
                List.of(arg + "1", arg + "2", arg + "3", arg + "4", arg + "5", arg + "6", arg + "7"));
    }

    public void show(Stage primaryStage, Tab previousTab, int Codice_Dieta) {
        GridPane grid = new GridPane();
        CheckBox[][] checkBoxes = new CheckBox[SIZE][SIZE];

        // Add day labels
        for (int col = 0; col < SIZE; col++) {
            Label dayLabel = new Label(DAYS.get(col));
            grid.add(dayLabel, col + 1, 0);
        }

        // Add menu labels and checkboxes
        for (int row = 0; row < SIZE; row++) {
            Label menuLabel = new Label(MENUS.get(row));
            grid.add(menuLabel, 0, row + 1);

            for (int col = 0; col < SIZE; col++) {
                CheckBox checkBox = new CheckBox();
                checkBoxes[row][col] = checkBox;
                grid.add(checkBox, col + 1, row + 1);
            }
        }

        Button insertButton = new Button("Insert");
        insertButton.setOnAction(event -> {
            Map<String, String> menuSchedule = new HashMap<>();
            if (validateMatrix(checkBoxes, menuSchedule)) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Menu registrati correttamente!");
                alert.setHeaderText("Menu registrati correttamente!");
                alert.showAndWait();

                long nMenu = menuSchedule.values().stream().distinct().count();
                handleMenuEntry(0, nMenu, menuSchedule, Codice_Dieta, primaryStage, previousTab);
            } else {
                showAlert(AlertType.ERROR, "Errore", "Ogni colonna deve avere esattamente un menu selezionato!");
            }
        });

        VBox vbox = new VBox(10, grid, insertButton);
        Scene scene = new Scene(vbox, 500, 500);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Checkbox Matrix Menu");
        primaryStage.show();
    }

    private void handleMenuEntry(int currentIndex, long nMenu, Map<String, String> menuSchedule, int Codice_Dieta,
            Stage primaryStage, Tab previousTab) {
        if (currentIndex >= nMenu) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Menu registrati correttamente!");
            alert.setHeaderText("Menu registrati correttamente!");
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
        Text myTitle = new Text("Menu" + (currentIndex + 1));
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
                // Insert into menu
                String query = "INSERT INTO menu (Descrizione) VALUES (?)";
                try {
                    PreparedStatement preparedStatement = MySQLConnect.getConnection().prepareStatement(query);
                    preparedStatement.setString(1, descrizione);
                    preparedStatement.executeUpdate();
                } catch (Exception ex) {
                    showAlert(AlertType.ERROR, "Errore", "Errore durante l'inserimento del menu");
                    ex.printStackTrace();
                }

                // Get last inserted menu id
                String query2 = "SELECT Codice_Menu FROM menu ORDER BY Codice_Menu DESC LIMIT 1";
                int Codice_Menu = 0;
                try {
                    PreparedStatement preparedStatement = MySQLConnect.getConnection().prepareStatement(query2);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    resultSet.next();
                    Codice_Menu = resultSet.getInt("Codice_Menu");
                } catch (Exception ex) {
                    showAlert(AlertType.ERROR, "Errore", "Errore durante l'ottenimento dell'id del menu");
                    ex.printStackTrace();
                }

                // Insert into comprensione
                String query3 = "INSERT INTO comprensione (Codice_Menu, Codice_Dieta) VALUES (?, ?)";
                try {
                    PreparedStatement preparedStatement = MySQLConnect.getConnection().prepareStatement(query3);
                    preparedStatement.setInt(1, Codice_Menu);
                    preparedStatement.setInt(2, Codice_Dieta);
                    preparedStatement.executeUpdate();
                } catch (Exception ex) {
                    showAlert(AlertType.ERROR, "Errore", "Errore durante l'inserimento della comprensione menu");
                    ex.printStackTrace();
                }

                // Handle integrations
                handleIntegrations(Codice_Menu, currentIndex, primaryStage, () -> {
                    handleMenuEntry(currentIndex + 1, nMenu, menuSchedule, Codice_Dieta, primaryStage, previousTab);
                });
            }
        });

        root.getChildren().addAll(myTitle, descrizioneField, nextButton);

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setTitle("Animaletti Coccolosi");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handleIntegrations(int Codice_Menu, int menuIndex, Stage primaryStage, Runnable onComplete) {
        int[] nIntegrazioni = { 1 }; // Use an array to allow modification within the lambda

        BooleanProperty continueLoop = new SimpleBooleanProperty(true);

        while (continueLoop.get()) {
            // Create a VBox layout
            VBox rootInt = new VBox();
            rootInt.setAlignment(Pos.CENTER);
            rootInt.setSpacing(20);

            // Create a title
            Text title = new Text("Integrazione" + nIntegrazioni[0] + " di Menu" + (menuIndex + 1));
            title.setFont(Font.font(24));

            // Create fields
            TextField nomeField = new TextField();
            nomeField.setPromptText("Nome Cibo");

            TextField frequenzaField = new TextField();
            frequenzaField.setPromptText("Frequenza");

            TextField quantitaField = new TextField();
            quantitaField.setPromptText("Quantità");

            CheckBox ultimaIntegrazione = new CheckBox("Questa è l'ultima integrazione del Menu");

            // Create insert button
            Button inserisciButton = new Button("Inserisci");
            inserisciButton.setOnAction(ev -> {
                if (isValid(nomeField.getText()) && areValid(frequenzaField.getText(), quantitaField.getText())) {
                    // Check if food already exists in this menu integration
                    String query7 = "SELECT * FROM integrazione WHERE Codice_Menu = ? AND Nome = ?";
                    try {
                        PreparedStatement preparedStatement = MySQLConnect.getConnection().prepareStatement(query7);
                        preparedStatement.setInt(1, Codice_Menu);
                        preparedStatement.setString(2, nomeField.getText());
                        ResultSet resultSet = preparedStatement.executeQuery();
                        if (resultSet.next()) {
                            showAlert(AlertType.ERROR, "Errore", "Cibo già presente in questo menu");
                            return;
                        }
                    } catch (Exception ex) {
                        showAlert(AlertType.ERROR, "Errore", "Errore durante la verifica dell'integrazione");
                        ex.printStackTrace();
                    }

                    // Insert integration
                    String query4 = "INSERT INTO integrazione (Nome, Frequenza, Quantita, Codice_Menu) VALUES (?, ?, ?, ?)";
                    try {
                        PreparedStatement preparedStatement = MySQLConnect.getConnection().prepareStatement(query4);
                        preparedStatement.setString(1, nomeField.getText());
                        preparedStatement.setInt(2, Integer.parseInt(frequenzaField.getText()));
                        preparedStatement.setDouble(3, Double.parseDouble(quantitaField.getText()));
                        preparedStatement.setInt(4, Codice_Menu);
                        preparedStatement.executeUpdate();
                        Alert alert2 = new Alert(AlertType.INFORMATION);
                        alert2.setTitle("Integrazione registrata correttamente!");
                        alert2.setHeaderText("Integrazione registrata correttamente!");
                        alert2.showAndWait();
                    } catch (Exception ex) {
                        showAlert(AlertType.ERROR, "Errore", "Errore durante l'inserimento dell'integrazione");
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
            dialogStage.setTitle("Inserisci Integrazione" + nIntegrazioni[0] + " di Menu" + (menuIndex + 1));
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

    private boolean isValid(String nomeCibo) {
        if (nomeCibo.length() == 0 || nomeCibo.length() > 20) {
            showAlert(AlertType.ERROR, "Errore", "Lunghezza nome cibo non valida");
            return false;
        }
        String query = "SELECT * FROM cibo WHERE Nome = ?";
        try {
            PreparedStatement preparedStatement = MySQLConnect.getConnection().prepareStatement(query);
            preparedStatement.setString(1, nomeCibo);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                showAlert(AlertType.ERROR, "Errore", "Il cibo non esiste");
                return false;
            }
        } catch (Exception e) {
            showAlert(AlertType.ERROR, "Errore", "Errore durante la verifica del cibo");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private boolean validateMatrix(CheckBox[][] checkBoxes, Map<String, String> menuSchedule) {
        for (int col = 0; col < SIZE; col++) {
            int checkedCount = 0;
            String menuForDay = null;
            for (int row = 0; row < SIZE; row++) {
                if (checkBoxes[row][col].isSelected()) {
                    checkedCount++;
                    menuForDay = MENUS.get(row);
                }
            }
            if (checkedCount != 1) {
                return false;
            }
            menuSchedule.put(DAYS.get(col), menuForDay);
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