package gui.RUN.VETERINARY;

import java.sql.SQLException;
import gui.RUN.COMMONBUTTONS.QuitButton;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
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

        /*
         * Statement stmt = MySQLConnect.getConnection().createStatement();
         * ResultSet rs = stmt.executeQuery("SELECT * FROM proprietario");
         * while (rs.next()) {
         * Text text = new Text(rs.getString("Email") + " " + rs.getString("Bloccato"));
         * text.setFont(new Font(20));
         * root.getChildren().add(text);
         * }
         */

        // aggiungi un bottone per chiudere l'applicazione
        Button quitButton = new QuitButton();

        root.getChildren().addAll(quitButton);

        // Create a scene and set it on the stage
        Scene scene = new Scene(root, 400, 300);
        primaryStage.setTitle("Animaletti Coccolosi Propritario");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}