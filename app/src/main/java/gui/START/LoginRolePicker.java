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

import java.sql.SQLException;
import java.util.List;
import gui.RUN.*;

public class LoginRolePicker {

    private final Stage primaryStage;

    public LoginRolePicker(Stage primaryStage) {
        this.primaryStage = primaryStage;
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
                case "Proprietarian":
                    OwnerTab ownerTab = new OwnerTab(primaryStage);
                    try {
                        ownerTab.show();
                    } catch (SQLException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    break;
                /*case "Veterinary":
                    VeterinaryTab veterinaryTab = new VeterinaryTab(primaryStage);
                    veterinaryTab.show();
                    break;
                case "Admin":
                    AdminTab adminTab = new AdminTab(primaryStage);
                    adminTab.show();
                    break;*/
            }
        });

        // Add components to the layout
        root.getChildren().addAll(title, roleComboBox, backToLoginButton, loginButton);

        // Create a scene and set it on the stage
        Scene scene = new Scene(root, 400, 300);
        primaryStage.setTitle("Animaletti Coccolosi");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private List<String> getRoles() {
        return List.of("Proprietarian", "Veterinary", "Admin");
    }
}