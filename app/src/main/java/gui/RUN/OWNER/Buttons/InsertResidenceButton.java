package gui.RUN.OWNER.Buttons;

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

public class InsertResidenceButton extends Button {

    public InsertResidenceButton(final Stage primaryStage, final Tab previousTab, final String email) {
        this.setText("Inserisci una nuova residenza");
        this.setOnAction(e -> {
            // Create a VBox layout
            VBox root = new VBox();
            root.setAlignment(Pos.CENTER);
            root.setSpacing(20);

            // Create a title
            Text title = new Text("Inserisci una nuova residenza");
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

            // Create isnerisci button
            Button inserisciButton = new Button("Inserisci");
            inserisciButton.setOnAction(ev -> {
                String via = viaField.getText();
                String civico = civicoField.getText();
                String nomezona = nomezonaField.getText();
                if (isIndirizzoValid(via, civico, nomezona, email)) {
                    String query = "INSERT INTO residenza (Email, Ind_Via, Ind_Numero_Civico, ZONA_Nome) VALUES (?, ?, ?, ?)";
                    try {
                        Connection connection = MySQLConnect.getConnection();
                        PreparedStatement preparedStatement = connection.prepareStatement(query);
                        preparedStatement.setString(1, email);
                        preparedStatement.setString(2, via);
                        preparedStatement.setString(3, civico);
                        preparedStatement.setString(4, nomezona);
                        preparedStatement.executeUpdate();
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information");
                        alert.setHeaderText(null);
                        alert.setContentText("Residenza inserita correttamente");
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
            root.getChildren().addAll(title, viaField, civicoField, nomezonaField,
                    inserisciButton, backButton);

            Scene scene = new Scene(root, 400, 300);
            primaryStage.setTitle("Animaletti Coccolosi");
            primaryStage.setScene(scene);
            primaryStage.show();

        });
    }

    private boolean isIndirizzoValid(final String via, final String civico, final String nomezona, final String email) {
        String query1 = "SELECT * FROM indirizzo i WHERE i.Ind_Via = ? AND i.Ind_Numero_Civico = ? AND i.Nome = ?"; // the
                                                                                                                    // address
                                                                                                                    // is
                                                                                                                    // present
        String query2 = "SELECT * FROM ambulatorio a WHERE a.Ind_Via = ? AND a.Ind_Numero_Civico = ? AND a.ZONA_Nome = ?"; // the
        // address
        // is
        // not
        // registered
        // in
        // ambulatorio
        String query3 = "SELECT * FROM residenza r WHERE r.Ind_Via = ? AND r.Ind_Numero_Civico = ? AND r.ZONA_Nome = ? AND r.email = ?"; // you
                                                                                                                                         // already
                                                                                                                                         // live
                                                                                                                                         // there
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
                    alert.setContentText("L'indirizzo è gia' registrato per un ambulatorio");
                    alert.showAndWait();
                    return false;
                } else {
                    PreparedStatement preparedStatement3 = connection.prepareStatement(query3);
                    preparedStatement3.setString(1, via);
                    preparedStatement3.setString(2, civico);
                    preparedStatement3.setString(3, nomezona);
                    preparedStatement3.setString(4, email);
                    ResultSet rs3 = preparedStatement3.executeQuery();
                    if (rs3.next()) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information");
                        alert.setHeaderText(null);
                        alert.setContentText("Vivi gia in questa residenza");
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