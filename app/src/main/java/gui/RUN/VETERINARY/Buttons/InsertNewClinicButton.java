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
import javafx.stage.Stage;

import gui.Tab;

public class InsertNewClinicButton extends Button {

    public InsertNewClinicButton(final Stage primaryStage, final Tab previousTab) {
        this.setText("Inserisci un nuovo ambulatorio");
        this.setOnAction(e -> {
            // Create a VBox layout
            VBox root = new VBox();
            root.setAlignment(Pos.CENTER);
            root.setSpacing(20);

            // Create a title
            Text title = new Text("Inserisci un nuovo ambulatorio");
            title.setFont(Font.font(24));

            // Create via field
            TextField viaField = new TextField();
            viaField.setPromptText("Via");

            // Create numero field
            TextField civicoField = new TextField();
            civicoField.setPromptText("Numero civico");

            // Create nome zona field
            TextField nomezonaField = new TextField();
            nomezonaField.setPromptText("Nome zona");

            // Create nome amb field
            TextField nomeAmbField = new TextField();
            nomeAmbField.setPromptText("Nome ambulatorio:");

            // Create descrizione field
            TextField descrizioneField = new TextField();
            descrizioneField.setPromptText("Descrizione");

            // Create isnerisci button
            Button inserisciButton = new Button("Inserisci");
            inserisciButton.setOnAction(ev -> {
                String via = viaField.getText();
                String civico = civicoField.getText();
                String nomezona = nomezonaField.getText();
                String nomeAmb = nomeAmbField.getText();
                String descrizione = descrizioneField.getText();
                if (isIndirizzoValid(via, civico, nomezona) && isNomeAmbValid(nomeAmb)
                        && isDescrizioneValid(descrizione)) {
                    String query = "INSERT INTO ambulatorio (Nome, Descrizione, Ind_Via, Ind_Numero_Civico, ZONA_Nome) VALUES (?, ?, ?, ?, ?)";
                    try {
                        Connection connection = MySQLConnect.getConnection();
                        PreparedStatement preparedStatement = connection.prepareStatement(query);
                        preparedStatement.setString(1, nomeAmb);
                        preparedStatement.setString(2, descrizione);
                        preparedStatement.setString(3, via);
                        preparedStatement.setString(4, civico);
                        preparedStatement.setString(5, nomezona);
                        preparedStatement.executeUpdate();
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information");
                        alert.setHeaderText(null);
                        alert.setContentText("Ambulatorio inserito correttamente");
                        alert.showAndWait();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });

            // Create back button
            Button backButton = new Button("Back");
            backButton.setOnAction(ev -> {
                try {
                    previousTab.show();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });

            // Add components to the layout
            root.getChildren().addAll(title, viaField, civicoField, nomezonaField, nomeAmbField, descrizioneField,
                    inserisciButton, backButton);

            Scene scene = new Scene(root, 400, 300);
            primaryStage.setTitle("Animaletti Coccolosi");
            primaryStage.setScene(scene);
            primaryStage.show();

        });
    }

    private boolean isNomeAmbValid(final String nomeAmb) {
        // check if the name is already present
        boolean flag = true;
        String query = "SELECT * FROM ambulatorio a WHERE a.nome = ?";
        try {
            Connection connection = MySQLConnect.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, nomeAmb);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText(null);
                alert.setContentText("L'ambulatorio è gia' presente");
                alert.showAndWait();
                flag = false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return nomeAmb.length() > 0 && nomeAmb.length() <= 30 && flag;
    }

    private boolean isDescrizioneValid(final String descrizione) {
        return descrizione.length() > 0 && descrizione.length() <= 200;
    }

    private boolean isIndirizzoValid(final String via, final String civico, final String nomezona) {
        String query1 = "SELECT * FROM indirizzo i WHERE i.Ind_Via = ? AND i.Ind_Numero_Civico = ? AND i.Nome = ?"; // the
                                                                                                                    // address
                                                                                                                    // is
                                                                                                                    // present
        String query2 = "SELECT * FROM residenza r WHERE r.Ind_Via = ? AND r.Ind_Numero_Civico = ? AND r.ZONA_Nome = ?"; // the
                                                                                                                         // address
                                                                                                                         // is
                                                                                                                         // not
                                                                                                                         // registered
                                                                                                                         // in
                                                                                                                         // residenza
        String query3 = "SELECT * FROM ambulatorio a WHERE a.Ind_Via = ? AND a.Ind_Numero_Civico = ? AND a.ZONA_Nome = ?"; // the
                                                                                                                           // address
                                                                                                                           // is
                                                                                                                           // not
                                                                                                                           // registered
                                                                                                                           // in
                                                                                                                           // ambulatorio
        boolean flag = false;
        try {
            Connection connection = MySQLConnect.getConnection();
            PreparedStatement preparedStatement1 = connection.prepareStatement(query1);
            preparedStatement1.setString(1, via);
            preparedStatement1.setString(2, civico);
            preparedStatement1.setString(3, nomezona);
            ResultSet rs1 = preparedStatement1.executeQuery();
            if (!rs1.next()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText(null);
                alert.setContentText("L'indirizzo non è registrato");
                alert.showAndWait();
                return false;
            } else {
                PreparedStatement preparedStatement2 = connection.prepareStatement(query2);
                preparedStatement2.setString(1, via);
                preparedStatement2.setString(2, civico);
                preparedStatement2.setString(3, nomezona);
                ResultSet rs2 = preparedStatement2.executeQuery();
                if (rs2.next()) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information");
                    alert.setHeaderText(null);
                    alert.setContentText("L'indirizzo è gia' registrato per una residenza");
                    alert.showAndWait();
                    return false;
                } else {
                    PreparedStatement preparedStatement3 = connection.prepareStatement(query3);
                    preparedStatement3.setString(1, via);
                    preparedStatement3.setString(2, civico);
                    preparedStatement3.setString(3, nomezona);
                    ResultSet rs3 = preparedStatement3.executeQuery();
                    if (rs3.next()) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information");
                        alert.setHeaderText(null);
                        alert.setContentText("L'indirizzo è gia' registrato per un ambulatorio");
                        alert.showAndWait();
                        return false;
                    } else {
                        flag = true;
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return via.length() > 0 && via.length() <= 30 && civico.length() > 0 && civico.length() <= 10
                && civico.chars().allMatch(Character::isDigit) && nomezona.length() > 0 && nomezona.length() <= 50
                && flag;
    }
}