package gui.RUN.OWNER;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import database.MySQLConnect;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class OwnerTab {

    private final Stage primaryStage;

    public OwnerTab(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void show() throws SQLException {
        // Create a VBox layout
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(20);

        // Create a scene and set it on the stage
        Scene scene = new Scene(root, 400, 300);
        primaryStage.setTitle("Animaletti Coccolosi Propritario");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}