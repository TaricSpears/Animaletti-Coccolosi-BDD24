package gui.RUN.ADMIN;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import database.MySQLConnect;
import gui.RUN.ADMIN.Buttons.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AdminTab {

    private final Stage primaryStage;

    public AdminTab(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void show() throws SQLException {
        // Create a VBox layout
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(20);

        // crea un bottone per visualizzare tutti gli utenti
        Button showUsersButton = new ShowUserButton();

        // crea un bottone per visualizzare tutti gli animali
        Button showAnimalsButton = new ShowAnimalsButton();

        root.getChildren().addAll(showUsersButton, showAnimalsButton);

        // Create a scene and set it on the stage
        Scene scene = new Scene(root, 400, 300);
        primaryStage.setTitle("Animaletti Coccolosi Propritario");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}