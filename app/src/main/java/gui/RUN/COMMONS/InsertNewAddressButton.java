package gui.RUN.COMMONS;

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

public class InsertNewAddressButton extends Button {

    public InsertNewAddressButton(final Stage primaryStage, final Tab previousTab) {
        this.setText("Inserisci un nuovo indirizzo");
        this.setOnAction(e -> {
            // Create a VBox layout
            VBox root = new VBox();
            root.setAlignment(Pos.CENTER);
            root.setSpacing(20);

            // Create a title
            Text title = new Text("Inserisci un nuovo indirizzo");
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

            // Create login button
            Button inserisciButton = new Button("Inserisci");
            inserisciButton.setOnAction(ev -> {
                String via = viaField.getText();
                String civico = civicoField.getText();
                String nomezona = nomezonaField.getText();
                if (isViaValid(via) && isCivicoValid(civico) && isZonaValid(nomezona)) {
                    String query = "SELECT * FROM indirizzo i WHERE Ind_Via = ? AND Ind_Numero_Civico = ? AND Nome = ?";
                    try {
                        Connection connection = MySQLConnect.getConnection();
                        PreparedStatement preparedStatement = connection.prepareStatement(query);
                        preparedStatement.setString(1, via);
                        preparedStatement.setString(2, civico);
                        preparedStatement.setString(3, nomezona);
                        ResultSet rs = preparedStatement.executeQuery();
                        if (rs.next()) { // l'indirizzo esiste iga
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Information");
                            alert.setHeaderText(null);
                            alert.setContentText("L'indirizzo è gia' presente");
                            alert.showAndWait();
                            return;
                        } else { // l'indirizzo non è presnete va inserito
                            String queryIns = "INSERT INTO indirizzo (Ind_Via, Ind_Numero_Civico, Nome) VALUES (?, ?, ?)";
                            PreparedStatement preparedStatementIns = connection.prepareStatement(queryIns);
                            preparedStatementIns.setString(1, via);
                            preparedStatementIns.setString(2, civico);
                            preparedStatementIns.setString(3, nomezona);
                            preparedStatementIns.executeUpdate();
                            preparedStatementIns.close();
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Confirmation");
                            alert.setHeaderText(null);
                            alert.setContentText("Indirizzo inserito correttamente");
                            alert.showAndWait();
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    return;
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setHeaderText(null);
                    alert.setContentText("Invalid value for input fields");
                    alert.showAndWait();
                    return;
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
            root.getChildren().addAll(title, viaField, civicoField, nomezonaField, inserisciButton, backButton);

            Scene scene = new Scene(root, 400, 300);
            primaryStage.setTitle("Animaletti Coccolosi");
            primaryStage.setScene(scene);
            primaryStage.show();

        });
    }

    private boolean isViaValid(final String via) {
        return via.length() > 0 && via.length() <= 30;
    }

    private boolean isCivicoValid(final String civico) {
        return civico.length() > 0 && civico.length() <= 10 && civico.chars().allMatch(Character::isDigit);
    }

    private boolean isZonaValid(final String zona) {
        String query = "SELECT * FROM zona z WHERE z.nome = ?";
        boolean flag = false;
        try {
            Connection connection = MySQLConnect.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, zona);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) { // La zona esiste
                flag = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return zona.length() > 0 && zona.length() <= 50 && flag;
    }
}