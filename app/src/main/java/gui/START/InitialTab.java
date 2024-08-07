package gui.START;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class InitialTab {

    private final Stage primaryStage;

    public InitialTab(Stage primaryStage) {
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

        // Create login button
        Button loginButton = new Button("Login");
        loginButton.setOnAction(e -> {
            LoginTab loginTab = new LoginTab(primaryStage);
            loginTab.show();
        });

        // Create register button
        Button registerButton = new Button("Register");
        registerButton.setOnAction(e -> {
            RegistrationTab registrationTab = new RegistrationTab(primaryStage);
            registrationTab.show();
        });

        // Add components to the layout
        root.getChildren().addAll(title, loginButton, registerButton);

        // set color
        root.setStyle("-fx-background-color: #a8bfbf;");

        // set this app icon to animaletticoccolosi image in resources folder in a try
        // with resources
        try (var is = ClassLoader
                .getSystemResourceAsStream("images/ANIMALETTICOCCOLOSI8BIT.png")) {
            primaryStage.getIcons().add(new Image(is));
            System.out.println("Icon set");
        } catch (Exception e) {
            e.printStackTrace();
        }

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
}