package gui.RUN;

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

public class VeterinaryTab {

    private final Stage primaryStage;

    public VeterinaryTab(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void show() throws SQLException {
        // Create a VBox layout
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(20);

        /*Statement stmt = MySQLConnect.getConnection().createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM proprietario");
        while (rs.next()) {
            Text text = new Text(rs.getString("Email") + " " + rs.getString("Bloccato"));
            text.setFont(new Font(20));
            root.getChildren().add(text);
        }*/

        // Create a scene and set it on the stage
        Scene scene = new Scene(root, 400, 300);
        primaryStage.setTitle("Animaletti Coccolosi Propritario");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}