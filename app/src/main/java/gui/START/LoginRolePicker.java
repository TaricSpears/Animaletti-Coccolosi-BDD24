package gui.START;

//create a class built like LoginTab which asks for the role of the user (proprietarian, veterinary or admin).
//the roles must be selectable from a combobox. the combobox gets its items from a function, getRoles(), that returns a list of strings.
//it must have a button to go to the login tab.

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.List;

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

        // Create login button
        Button backToLoginButton = new Button("Back to Login");
        backToLoginButton.setOnAction(e -> {
            LoginTab loginTab = new LoginTab(primaryStage);
            loginTab.show();
        });

        // Add components to the layout
        root.getChildren().addAll(title, roleComboBox, backToLoginButton);

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