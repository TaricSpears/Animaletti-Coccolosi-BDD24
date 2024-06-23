package gui.RUN.OWNER;

import java.sql.SQLException;

import gui.RUN.COMMONS.QuitButton;
import gui.RUN.COMMONS.UserDataText;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class OwnerTab {

    private final Stage primaryStage;
    private final String email;

    public OwnerTab(final Stage primaryStage, final String email) {
        this.primaryStage = primaryStage;
        this.email = email;
    }

    public void show() throws SQLException {
        // Create a VBox layout
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(20);

        // crea un textfield per mostrare i dati dello user loggato
        Text userData = new UserDataText(email);

        // aggiungi un bottone per chiudere l'applicazione
        Button quitButton = new QuitButton();

        root.getChildren().addAll(userData, quitButton);

        // Create a scene and set it on the stage
        Scene scene = new Scene(root, 400, 300);
        primaryStage.setTitle("ANIMALETTI COCCOLOSI - PROPRIETARIO");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}