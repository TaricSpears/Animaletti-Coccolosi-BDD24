package gui.RUN.COMMONS;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import database.MySQLConnect;
import gui.Tab;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SendMessageButton extends Button {

    public SendMessageButton(final String email, final int ruolo, final Stage primaryStage, final Tab previousTab) {
        this.setText("Invia un messaggio");
        this.setOnAction(e -> {
            String query = "SELECT p.Bloccato FROM proprietario p WHERE p.email = '" + email + "'"; // controlla se
            // l'utente è bloccato (quindi se è proprietario)
            try {
                Statement stmt = MySQLConnect.getConnection().createStatement();
                ResultSet rs = stmt.executeQuery(query);
                if (rs.next() && ruolo == Roles.PROPRIETARIO.value) {
                    if (rs.getBoolean("Bloccato")) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle(null);
                        alert.setHeaderText("Errore");
                        alert.setContentText(
                                "Non puoi inviare messaggi perchè sei bloccato e stai accedendo come proprietario");
                        alert.setResizable(true);
                        alert.showAndWait();
                    } else { // apri un nuovo stage per inserire id_gruppo e contenuto messaggio
                        VBox root = new VBox();
                        root.setAlignment(Pos.CENTER);
                        root.setSpacing(20);

                        // Create a title
                        Text title = new Text("Inserisci un nuovo messaggio");
                        title.setFont(Font.font(24));

                        // Create via field
                        TextField idgruppoField = new TextField();
                        idgruppoField.setPromptText("ID_Gruppo");

                        // Create numero field
                        TextField contenutoField = new TextField();
                        contenutoField.setPromptText("Contenuto");

                        // Create isnerisci button
                        Button inserisciButton = new Button("Inserisci");
                        inserisciButton.setOnAction(ev -> {
                            String idgruppo = idgruppoField.getText();
                            String contenuto = contenutoField.getText();
                            if (isIDGruppoValid(idgruppo, email) && isContenutoValid(contenuto)) {
                                String query3 = "INSERT INTO messaggio (Contenuto, Istante_Invio, Email, ID_Gruppo) VALUES (?, ?, ?, ?)";
                                try {
                                    Connection connection = MySQLConnect.getConnection();
                                    PreparedStatement preparedStatement = connection.prepareStatement(query3);
                                    contenuto = "|" + createRoleVisualizer(email) + "|\t\t| " + contenuto;
                                    preparedStatement.setString(1, contenuto);
                                    preparedStatement.setString(2, LocalDateTime.now().toString());
                                    preparedStatement.setString(3, email);
                                    preparedStatement.setString(4, idgruppo);
                                    preparedStatement.executeUpdate();
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setTitle("Information");
                                    alert.setHeaderText(null);
                                    alert.setContentText("Messaggio inserito correttamente");
                                    alert.showAndWait();
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                        });

                        // Create back button
                        Button backButton = new Button("Back");
                        backButton.setOnAction(excc -> {
                            try {
                                previousTab.show();
                            } catch (SQLException e1) {
                                e1.printStackTrace();
                            }
                        });

                        // Add components to the layout
                        root.getChildren().addAll(title, idgruppoField, contenutoField, inserisciButton, backButton);

                        Scene scene = new Scene(root, 400, 300);
                        primaryStage.setTitle("Animaletti Coccolosi");
                        primaryStage.setScene(scene);
                        primaryStage.show();

                    }
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

        });
    }

    private boolean isContenutoValid(String contenuto) {
        if (contenuto.length() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(null);
            alert.setHeaderText("Errore");
            alert.setContentText("Inserire un contenuto");
            alert.setResizable(true);
            alert.showAndWait();
        }
        return contenuto.length() > 0 && contenuto.length() <= 200;
    }

    private boolean isIDGruppoValid(String idgruppo, String email) {
        // check if the group exists
        boolean flag = false;
        String query = "SELECT g.ID_Gruppo FROM gruppo g WHERE g.ID_Gruppo = '" + idgruppo + "'";
        try {
            Statement stmt = MySQLConnect.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) { // il gruppo esiste
                // check if the user participates in the group
                String query2 = "SELECT p.ID_Gruppo FROM partecipazione p WHERE p.ID_Gruppo = '" + idgruppo
                        + "' AND p.email = '" + email + "'";
                ResultSet rs2 = stmt.executeQuery(query2);
                if (rs2.next()) {
                    flag = true;
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle(null);
                    alert.setHeaderText("Errore");
                    alert.setContentText("Non partecipi a questo gruppo");
                    alert.setResizable(true);
                    alert.showAndWait();
                    return false;
                }
            } else { // il gruppo non esiste
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(null);
                alert.setHeaderText("Errore");
                alert.setContentText("Il gruppo non esiste");
                alert.setResizable(true);
                alert.showAndWait();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }

    private String createRoleVisualizer(final String email) {
        String roleVisualizer = "";
        // verifica se l'utente è proprietario
        String query1 = "SELECT p.Email FROM proprietario p WHERE p.email = '" + email + "'";
        // verifica se l'utente è veterinario
        String query2 = "SELECT v.Email FROM veterinario v WHERE v.email = '" + email + "'";
        // verifica se l'utente è amministratore
        String query3 = "SELECT a.Email FROM amministratore a WHERE a.email = '" + email + "'";
        try {
            Statement stmt1 = MySQLConnect.getConnection().createStatement();
            ResultSet rs1 = stmt1.executeQuery(query1);
            Statement stmt2 = MySQLConnect.getConnection().createStatement();
            ResultSet rs2 = stmt2.executeQuery(query2);
            Statement stmt3 = MySQLConnect.getConnection().createStatement();
            ResultSet rs3 = stmt3.executeQuery(query3);
            if (rs1.next()) {
                roleVisualizer += "p";
            }
            if (rs2.next()) {
                roleVisualizer += "v";
            }
            if (rs3.next()) {
                roleVisualizer += "a";
            }
            return roleVisualizer;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "unk";
    }
}
