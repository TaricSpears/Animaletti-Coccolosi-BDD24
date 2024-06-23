package gui.RUN.VETERINARY;

import java.sql.SQLException;

import gui.RUN.COMMONS.QuitButton;
import gui.RUN.COMMONS.UserDataText;
import gui.RUN.VETERINARY.Buttons.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class VeterinaryTab {

    private final Stage primaryStage;
    private final String email;

    public VeterinaryTab(final Stage primaryStage, final String email) {
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

        // crea un bottone per inserire un nuovo cibo
        InsertFoodButton insertFoodButton = new InsertFoodButton();

        // crea un bottone per inserire un nuovo esercizio
        InsertExerciseButton insertExerciseButton = new InsertExerciseButton();

        // crea un bottone per inserire un nuovo farmaco
        InsertDrugButton insertDrugButton = new InsertDrugButton();

        // aggiungi un bottone per chiudere l'applicazione
        Button quitButton = new QuitButton();

        root.getChildren().addAll(userData, insertFoodButton, insertExerciseButton, insertDrugButton, quitButton);

        // Create a scene and set it on the stage
        Scene scene = new Scene(root, 400, 300);
        primaryStage.setTitle("ANIMALETTI COCCOLOSI - VETERINARIO");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}