package gui.RUN.ADMIN;

import java.sql.SQLException;
import gui.RUN.ADMIN.Buttons.*;
import gui.RUN.COMMONBUTTONS.QuitButton;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
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

        // crea un bottone per visualizzare tutte le segnalazioni
        Button showUserReportsButton = new ShowUserReportsButton();

        // aggiungi un bottone per chiudere l'applicazione
        Button quitButton = new QuitButton();

        root.getChildren().addAll(showUsersButton, showAnimalsButton, showUserReportsButton, quitButton);

        // Create a scene and set it on the stage
        Scene scene = new Scene(root, 400, 300);
        primaryStage.setTitle("Animaletti Coccolosi Propritario");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}