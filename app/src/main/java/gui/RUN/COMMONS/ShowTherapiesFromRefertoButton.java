package gui.RUN.COMMONS;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import database.MySQLConnect;
import gui.Tab;
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

public class ShowTherapiesFromRefertoButton extends Button {
    public ShowTherapiesFromRefertoButton(final String email, final Roles role, final Stage primaryStage,
            final Tab previousTab) {
        this.setText("Visualizza terapie da referto");
        this.setOnAction(click -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Inserisci Codice_Referto");
            dialog.setHeaderText("Inserisci il codice referto");
            dialog.setContentText("Codice Referto:");
            dialog.showAndWait().ifPresent(Codice_Referto -> {
                if (isValid(Codice_Referto, email, role)) {
                    // ottieni id animale da referto
                    String IDAnimale = "";
                    String query = "SELECT Codice_Identificativo FROM referto_clinico WHERE Codice_Referto = ?";
                    try {
                        Connection connection = MySQLConnect.getConnection();
                        PreparedStatement preparedStatement = connection.prepareStatement(query);
                        preparedStatement.setString(1, Codice_Referto);
                        ResultSet rs = preparedStatement.executeQuery();
                        if (rs.next()) {
                            IDAnimale = rs.getString("Codice_Identificativo");
                        } else {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Errore");
                            alert.setHeaderText("Errore");
                            alert.setContentText("Errore nel recupero dell'id animale dal referto inserito");
                            alert.setResizable(true);
                            alert.showAndWait();
                            return;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }

                    String querySelect = "SELECT * FROM terapia WHERE Codice_Referto IN (SELECT Codice_Referto FROM referto_clinico WHERE Codice_Identificativo = ?) ORDER BY Data_Inizio DESC";
                    try {
                        Connection connection = MySQLConnect.getConnection();
                        PreparedStatement preparedStatement = connection.prepareStatement(querySelect);
                        preparedStatement.setString(1, IDAnimale);
                        ResultSet resultSet = preparedStatement.executeQuery();
                        if (resultSet.next()) {
                            Stage dialogStage = new Stage();
                            dialogStage.setTitle("Terapie");
                            VBox vBox = new VBox();
                            vBox.setAlignment(Pos.CENTER);
                            vBox.setSpacing(20);
                            Text title1 = new Text("Terapie");
                            title1.setFont(Font.font(24));
                            vBox.getChildren().add(title1);
                            do {
                                String codiceTerapia = resultSet.getString("Codice_Terapia");
                                String nome = resultSet.getString("Nome");
                                String dataTerapia = resultSet.getString("Data_Inizio");
                                String descrizione = resultSet.getString("Descrizione");
                                Integer Codice_Dieta = resultSet.getInt("Codice_Dieta");
                                if (Codice_Dieta == 0) {
                                    Codice_Dieta = null;
                                }

                                String codiceReferto = resultSet.getString("Codice_Referto");
                                Text text = new Text("Codice Terapia: " + codiceTerapia + " Nome: " + nome
                                        + " Data Terapia: " + dataTerapia
                                        + " Codice Dieta: " + Codice_Dieta
                                        + " Codice Referto: " + codiceReferto
                                        + "\nDescrizione: " + descrizione
                                        + "\n\n");
                                text.setFont(new Font(14));
                                vBox.getChildren().add(text);
                            } while (resultSet.next());
                            Scene scene = new Scene(vBox, 800, 600);
                            dialogStage.setScene(scene);
                            dialogStage.initModality(Modality.APPLICATION_MODAL);
                            dialogStage.show();
                        } else {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Errore");
                            alert.setHeaderText("Errore");
                            alert.setContentText(
                                    "Non ci sono terapie relative al referto inserito");
                            alert.setResizable(true);
                            alert.showAndWait();
                            return;
                        }
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            });
        });
    }

    private boolean isValid(String codiceReferto, String email, Roles role) {
        if (codiceReferto.length() == 0 || codiceReferto.length() > 4 || !codiceReferto.matches("[0-9]+")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText("Codice referto non valido");
            alert.setContentText("Il codice referto deve essere un numero di massimo 5 cifre");
            alert.showAndWait();
            return false;
        }
        // controllo che il referto esista
        String query10 = "SELECT * FROM referto_clinico WHERE Codice_Referto = ?";
        try {
            Connection connection = MySQLConnect.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query10);
            preparedStatement.setString(1, codiceReferto);
            ResultSet rs = preparedStatement.executeQuery();
            if (!rs.next()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Errore");
                alert.setHeaderText("Errore");
                alert.setContentText("Il codice referto inserito non corrisponde a nessun referto");
                alert.showAndWait();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        // ottengo idanimale da referto
        String IDAnimale = "";
        String query0 = "SELECT Codice_Identificativo FROM referto_clinico WHERE Codice_Referto = ?";
        try {
            Connection connection = MySQLConnect.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query0);
            preparedStatement.setString(1, codiceReferto);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                IDAnimale = rs.getString("Codice_Identificativo");
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Errore");
                alert.setHeaderText("Errore");
                alert.setContentText("Errore nel recupero dell'id animale dal referto inserito");
                alert.showAndWait();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        if (role == Roles.PROPRIETARIO) {
            // controllo che l'animale inserito appartenga al proprietario
            String query = "SELECT * FROM animale WHERE Codice_Identificativo = ? AND Email = ?";
            try {
                Connection connection = MySQLConnect.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, IDAnimale);
                preparedStatement.setString(2, email);
                ResultSet rs = preparedStatement.executeQuery();
                if (!rs.next()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Errore");
                    alert.setHeaderText("Errore");
                    alert.setContentText("Non hai accesso a questo animale");
                    alert.showAndWait();
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            // controllo che ci sia un referto con tale id associato all animale
            String query2 = "SELECT * FROM referto_clinico WHERE Codice_identificativo = ? AND Codice_Referto = ?";
            try {
                Connection connection = MySQLConnect.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query2);
                preparedStatement.setString(1, IDAnimale);
                preparedStatement.setString(2, codiceReferto);
                ResultSet rs = preparedStatement.executeQuery();
                if (!rs.next()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Errore");
                    alert.setHeaderText("Errore");
                    alert.setContentText("Non esiste un referto con questo codice associato all'animale");
                    alert.showAndWait();
                    return false;
                }
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        if (role == Roles.VETERINARIO) {
            // controllo che esista tale referto
            String query = "SELECT * FROM referto_clinico WHERE Codice_Referto = ?";
            try {
                Connection connection = MySQLConnect.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, codiceReferto);
                ResultSet rs = preparedStatement.executeQuery();
                if (!rs.next()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Errore");
                    alert.setHeaderText("Errore");
                    alert.setContentText("Non esiste un referto con questo codice");
                    alert.showAndWait();
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            // ottengo idvisita dal referto
            String idVisita = "";
            String query2 = "SELECT ID_visita FROM referto_clinico WHERE Codice_Referto = ?";
            try {
                Connection connection = MySQLConnect.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query2);
                preparedStatement.setString(1, codiceReferto);
                ResultSet rs = preparedStatement.executeQuery();
                if (rs.next()) {
                    idVisita = rs.getString("ID_visita");
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Errore");
                    alert.setHeaderText("Errore");
                    alert.setContentText("Errore nel recupero dell'id visita dal referto inserito");
                    alert.showAndWait();
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            // controllo che l'animale inserito sia associato alla visita e al veterinario
            String query3 = "SELECT * FROM visita WHERE ID_visita = ? AND Codice_Identificativo = ? AND ACC_Email = ?";
            try {
                Connection connection = MySQLConnect.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query3);
                preparedStatement.setString(1, idVisita);
                preparedStatement.setString(2, IDAnimale);
                preparedStatement.setString(3, email);
                ResultSet rs = preparedStatement.executeQuery();
                if (!rs.next()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Errore");
                    alert.setHeaderText("Errore");
                    alert.setContentText("Non hai accesso a questo animale o a questa visita");
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
