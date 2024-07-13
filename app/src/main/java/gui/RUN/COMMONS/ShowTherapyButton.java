package gui.RUN.COMMONS;

import java.sql.Connection;
import java.sql.PreparedStatement;

import database.MySQLConnect;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;

/*questo bottone serve per visualizzare comodamente una terapia
a partire dal codice terapia. l utente puo essere veterinario o
proprietario. svolgere i check necessari. la schermata deve mostrare una "griglia": sotto ai dati della terapia
i 7 giorni della settimana (colonne), e i 3 ambiti (menu, workout, regime ) */
public class ShowTherapyButton extends Button {
    public ShowTherapyButton(final Roles role, final String email) {
        this.setText("Visualizza terapia");
        this.setOnAction(click -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Visualizza terapia");
            dialog.setHeaderText("Inserisci il codice terapia");
            dialog.setContentText("Codice terapia:");
            dialog.showAndWait().ifPresent(id -> {
                if (isValid(id, role, email)) {
                    Stage dialogStage = new Stage();
                    dialogStage.setTitle("Terapia");
                    VBox vBox = new VBox();
                    vBox.setAlignment(Pos.CENTER);
                    vBox.setSpacing(20);
                    Text title1 = new Text("Terapia: " + id + "\n\n");
                    title1.setFont(Font.font(24));
                    String queryMenu = "SELECT c.Codice_Menu FROM terapia t JOIN dieta d ON t.Codice_Dieta = d.Codice_Dieta JOIN comprensione c ON d.Codice_Dieta = c.Codice_Dieta WHERE t.Codice_Terapia = ?";
                    String queryWorkout = "SELECT Codice_Workout FROM prescrizione_e WHERE Codice_Terapia = ?";
                    String queryRegime = "SELECT Codice_Regime FROM prescrizione_a WHERE Codice_Terapia = ?";
                    Set<Integer> menuSet = new HashSet<>();
                    Set<Integer> workoutSet = new HashSet<>();
                    Set<Integer> regimeSet = new HashSet<>();
                    Map<Integer, Integer> menuMap = new HashMap<>();
                    Map<Integer, Integer> workoutMap = new HashMap<>();
                    Map<Integer, Integer> regimeMap = new HashMap<>();
                    String dayRow = "Giorno:\t\tLunedi\tMartedi\tMercoledi\tGiovedi\tVenerdi\tSabato\tDomenica\n";
                    String menuRow = "Menu:\t\t";
                    String workoutRow = "Workout:\t\t";
                    String regimeRow = "Regime:\t\t";
                    try {
                        Connection connection = MySQLConnect.getConnection();
                        PreparedStatement preparedStatement = connection.prepareStatement(queryMenu);
                        preparedStatement.setInt(1, Integer.parseInt(id));
                        ResultSet resultSet = preparedStatement.executeQuery();
                        while (resultSet.next()) {
                            menuSet.add(resultSet.getInt(1));
                        }
                        preparedStatement = connection.prepareStatement(queryWorkout);
                        preparedStatement.setInt(1, Integer.parseInt(id));
                        resultSet = preparedStatement.executeQuery();
                        while (resultSet.next()) {
                            workoutSet.add(resultSet.getInt(1));
                        }
                        preparedStatement = connection.prepareStatement(queryRegime);
                        preparedStatement.setInt(1, Integer.parseInt(id));
                        resultSet = preparedStatement.executeQuery();
                        while (resultSet.next()) {
                            regimeSet.add(resultSet.getInt(1));
                        }
                    } catch (Exception e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Errore");
                        alert.setHeaderText("Errore");
                        alert.setContentText("Errore durante la visualizzazione della terapia");
                        alert.showAndWait();
                        e.printStackTrace();
                    }
                    for (var menu : menuSet) {
                        String query = "SELECT * FROM occorrenza_m WHERE Codice_Menu = ?";
                        try {
                            Connection connection = MySQLConnect.getConnection();
                            PreparedStatement preparedStatement = connection.prepareStatement(query);
                            preparedStatement.setInt(1, menu);
                            ResultSet resultSet = preparedStatement.executeQuery();
                            while (resultSet.next()) {
                                menuMap.put(resultSet.getInt("Codice_Giorno"), menu);
                            }
                        } catch (Exception e) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Errore");
                            alert.setHeaderText("Errore");
                            alert.setContentText("Errore durante la visualizzazione della terapia");
                            alert.showAndWait();
                            e.printStackTrace();
                        }
                    }
                    for (int i = 1; i < 8; i++) {
                        menuRow += menuMap.get(i) + "\t\t";
                    }
                    for (var workout : workoutSet) {
                        String query = "SELECT * FROM occorrenza_w WHERE Codice_Workout = ?";
                        try {
                            Connection connection = MySQLConnect.getConnection();
                            PreparedStatement preparedStatement = connection.prepareStatement(query);
                            preparedStatement.setInt(1, workout);
                            ResultSet resultSet = preparedStatement.executeQuery();
                            while (resultSet.next()) {
                                workoutMap.put(resultSet.getInt("Codice_Giorno"), workout);
                            }
                        } catch (Exception e) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Errore");
                            alert.setHeaderText("Errore");
                            alert.setContentText("Errore durante la visualizzazione della terapia");
                            alert.showAndWait();
                            e.printStackTrace();
                        }
                    }
                    for (int i = 1; i < 8; i++) {
                        workoutRow += workoutMap.get(i) + "\t\t";
                    }
                    for (var regime : regimeSet) {
                        String query = "SELECT * FROM occorrenza_f WHERE Codice_Regime = ?";
                        try {
                            Connection connection = MySQLConnect.getConnection();
                            PreparedStatement preparedStatement = connection.prepareStatement(query);
                            preparedStatement.setInt(1, regime);
                            ResultSet resultSet = preparedStatement.executeQuery();
                            while (resultSet.next()) {
                                regimeMap.put(resultSet.getInt("Codice_Giorno"), regime);
                            }
                        } catch (Exception e) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Errore");
                            alert.setHeaderText("Errore");
                            alert.setContentText("Errore durante la visualizzazione della terapia");
                            alert.showAndWait();
                            e.printStackTrace();
                        }
                    }
                    for (int i = 1; i < 8; i++) {
                        regimeRow += regimeMap.get(i) + "\t\t";
                    }
                    Text dayText = new Text(dayRow);
                    Text menuText = new Text(menuRow);
                    Text workoutText = new Text(workoutRow);
                    Text regimeText = new Text(regimeRow);
                    dayText.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");
                    vBox.getChildren().addAll(title1, dayText, menuText, workoutText, regimeText);
                    Scene scene = new Scene(vBox, 800, 600);
                    dialogStage.setScene(scene);
                    dialogStage.initModality(Modality.APPLICATION_MODAL);
                    dialogStage.show();
                }
            });
        });
    }

    // check se id e' valido. ovvero deve corrispondere ad una terapia esistente. se
    // sei vet dev'essere di un tuo pazient, se sei prop dev essere di un tuo
    // animale
    private boolean isValid(final String id, final Roles role, final String email) {
        if (id.length() <= 0 || id.length() > 3 || !id.matches("[0-9]+")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText("Errore");
            alert.setContentText("ID deve essere un intero di massimo 3 cifre!");
            alert.showAndWait();
            return false;
        }
        if (role == Roles.VETERINARIO) {
            int codiceReferto = 0;
            int idVisita = 0;
            String query = "SELECT Codice_Referto FROM terapia WHERE Codice_Terapia = ?";
            try {
                Connection connection = MySQLConnect.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, Integer.parseInt(id));
                ResultSet rs = preparedStatement.executeQuery();
                if (!rs.next()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Errore");
                    alert.setHeaderText("Errore");
                    alert.setContentText("Terapia non esistente");
                    alert.showAndWait();
                    return false;
                }
                codiceReferto = rs.getInt(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            // check se id e' di un tuo paziente
            String query2 = "SELECT ID_visita FROM referto_clinico WHERE Codice_Referto = ?";
            try {
                Connection connection = MySQLConnect.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query2);
                preparedStatement.setInt(1, codiceReferto);
                ResultSet rs = preparedStatement.executeQuery();
                if (!rs.next()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Errore");
                    alert.setHeaderText("Errore");
                    alert.setContentText("Errore nel reperire il referto clinico associato alla terapia");
                    alert.showAndWait();
                    return false;
                }
                idVisita = rs.getInt(1);
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Errore");
                alert.setHeaderText("Errore");
                alert.setContentText("Errore nel reperire il referto clinico associato alla terapia");
                alert.showAndWait();
                e.printStackTrace();
            }
            String query3 = "SELECT * FROM visita WHERE ID_visita = ? AND ACC_Email = ?";
            try {
                Connection connection = MySQLConnect.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query3);
                preparedStatement.setInt(1, idVisita);
                preparedStatement.setString(2, email);
                if (!preparedStatement.executeQuery().next()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Errore");
                    alert.setHeaderText("Errore");
                    alert.setContentText("Non hai accesso a questa terapia");
                    alert.showAndWait();
                    return false;
                }
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (role == Roles.PROPRIETARIO) {
            String query = "SELECT * FROM terapia t JOIN referto_clinico r ON t.Codice_Referto = r.Codice_Referto JOIN animale a ON r.Codice_Identificativo = a.Codice_Identificativo WHERE t.Codice_Terapia = ? AND a.Email = ?";
            try {
                Connection connection = MySQLConnect.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, Integer.parseInt(id));
                preparedStatement.setString(2, email);
                if (!preparedStatement.executeQuery().next()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Errore");
                    alert.setHeaderText("Errore");
                    alert.setContentText("Non hai accesso a questa terapia");
                    alert.showAndWait();
                    return false;
                }
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

}
