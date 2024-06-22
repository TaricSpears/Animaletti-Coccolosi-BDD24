package gui.START;

// create a class called RegistrationTab built like LoginTab which asks for name, surname,
// email, password, date of birth. it must have a register button and a back button to go back to InitialTab.

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class RegistrationTab {

    private final Stage primaryStage;

    public RegistrationTab(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void show() {
        // Create a VBox layout
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(10);

        // Create a title
        Text title = new Text("Animaletti Coccolosi");
        title.setFont(Font.font(24));

        // Create name field
        TextField nameField = new TextField();
        nameField.setPromptText("Name");

        // Create surname field
        TextField surnameField = new TextField();
        surnameField.setPromptText("Surname");

        // Create email field
        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        // Create password field
        TextField passwordField = new TextField();
        passwordField.setPromptText("Password");

        // Create date of birth field
        DatePicker dobField = new DatePicker();
        dobField.setPromptText("Date of Birth");

        // Create register button
        Button registerButton = new Button("Register");
        registerButton.setOnAction(e -> {
            // Add register button action
        });

        // Create back button
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            InitialTab initialTab = new InitialTab(primaryStage);
            initialTab.show();
        });

        // Add components to the layout
        root.getChildren().addAll(title, nameField, surnameField, emailField, passwordField, dobField, registerButton, backButton);

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setTitle("Animaletti Coccolosi");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}