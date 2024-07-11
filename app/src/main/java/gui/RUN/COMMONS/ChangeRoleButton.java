package gui.RUN.COMMONS;

import database.MySQLConnect;
import gui.Tab;
import gui.RUN.ADMIN.AdminTab;
import gui.RUN.OWNER.OwnerTab;
import gui.RUN.VETERINARY.VeterinaryTab;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class ChangeRoleButton extends Button {
    private final String email;

    public ChangeRoleButton(final Stage primaryStage, final Tab previousTab, final String email) {
        this.email = email;
        this.setText("CAMBIA RUOLO");

        setOnAction(e -> {
            VBox root = new VBox();
            root.setAlignment(Pos.CENTER);
            root.setSpacing(20);

            // Create a title
            Text title = new Text("Animaletti Coccolosi");
            title.setFont(Font.font(24));

            // Create role combobox
            ComboBox<String> roleComboBox = new ComboBox<>();
            roleComboBox.getItems().addAll(getRoles());
            roleComboBox.setPromptText("Seleziona il tuo ruolo");

            // Create back button
            Button backButton = new Button("Indietro");
            backButton.setOnAction(ev -> {
                try {
                    previousTab.show();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });

            // set color
            root.setStyle("-fx-background-color: #aad2d2;");

            // Create login button
            Button cambiaButton = new Button("Cambia Ruolo");
            cambiaButton.setOnAction(ev -> {
                String role = roleComboBox.getValue();
                if (role == null) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setHeaderText(null);
                    alert.setContentText("Please select a role");
                    alert.showAndWait();
                    return;
                }
                switch (role) {
                    case "Proprietario":
                        OwnerTab ownerTab = new OwnerTab(primaryStage, email);
                        try {
                            ownerTab.show();
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                        break;
                    case "Veterinario":
                        VeterinaryTab veterinaryTab = new VeterinaryTab(primaryStage, email);
                        try {
                            veterinaryTab.show();
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                        break;
                    case "Amministratore":
                        AdminTab adminTab = new AdminTab(primaryStage, email);
                        try {
                            adminTab.show();
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                        break;
                }
            });

            // Add components to the layout
            root.getChildren().addAll(title, roleComboBox, backButton, cambiaButton);

            // Create a scene and set it on the stage
            Scene scene = new Scene(root, 400, 300);
            primaryStage.setTitle("Animaletti Coccolosi");
            primaryStage.setScene(scene);
            primaryStage.show();
        });
    }

    private List<String> getRoles() {
        ArrayList<String> roles = new ArrayList<>();
        final String query1 = "SELECT * FROM proprietario WHERE Email = ?";
        final String query2 = "SELECT * FROM veterinario WHERE Email = ?";
        final String query3 = "SELECT * FROM amministratore WHERE Email = ?";
        try {
            Connection connection = MySQLConnect.getConnection();
            PreparedStatement preparedStatement1 = connection.prepareStatement(query1);
            PreparedStatement preparedStatement2 = connection.prepareStatement(query2);
            PreparedStatement preparedStatement3 = connection.prepareStatement(query3);
            preparedStatement1.setString(1, email);
            preparedStatement2.setString(1, email);
            preparedStatement3.setString(1, email);
            if (preparedStatement1.executeQuery().next()) {
                roles.add("Proprietario");
            }
            if (preparedStatement2.executeQuery().next()) {
                roles.add("Veterinario");
            }
            if (preparedStatement3.executeQuery().next()) {
                roles.add("Amministratore");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return roles;
    }
}