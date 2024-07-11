package gui.START;

//create a class built like LoginTab which asks for the role of the user (proprietarian, veterinary or admin).
//the roles must be selectable from a combobox. the combobox gets its items from a function, getRoles(), that returns a list of strings.
//it must have a button to go to the login tab.

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import database.MySQLConnect;
import gui.RUN.ADMIN.AdminTab;
import gui.RUN.OWNER.OwnerTab;
import gui.RUN.VETERINARY.VeterinaryTab;

import java.util.ArrayList;

public class LoginRolePicker {

    private final Stage primaryStage;
    private final String email;

    public LoginRolePicker(Stage primaryStage, String email) {
        this.primaryStage = primaryStage;
        this.email = email;
    }

    public void show() {
        // Create a VBox layout
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(20);

        // Create a title
        Text title = new Text("Animaletti Coccolosi");
        title.setFont(Font.font(24));

        // Create role combobox
        ComboBox<String> roleComboBox = new ComboBox<>();
        roleComboBox.getItems().addAll(getRoles());
        roleComboBox.setPromptText("Select your role");

        // Create back button
        Button backToLoginButton = new Button("Back to Login");
        backToLoginButton.setOnAction(e -> {
            LoginTab loginTab = new LoginTab(primaryStage);
            loginTab.show();
        });

        // set color
        root.setStyle("-fx-background-color: #a8bfbf;");

        // Create login button
        Button loginButton = new Button("Login");
        loginButton.setOnAction(e -> {
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
        root.getChildren().addAll(title, roleComboBox, backToLoginButton, loginButton);

        // set the style of all buttons
        root.getChildren().stream().filter(node -> node instanceof Button).forEach(node -> {
            Button button = (Button) node;
            button.setStyle(
                    "-fx-background-color: D1EAEA; -fx-text-fill: #374545; -fx-font-size: 12px; -fx-font-weight: bold; -fx-padding: 5px 12px; -fx-border-radius: 12px; -fx-cursor: hand;");
        });

        // Create a scene and set it on the stage
        Scene scene = new Scene(root, 400, 300);
        primaryStage.setTitle("Animaletti Coccolosi");
        primaryStage.setScene(scene);
        primaryStage.show();
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