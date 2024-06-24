package gui.RUN.VETERINARY;

import java.sql.SQLException;

import gui.RUN.COMMONS.*;
import gui.RUN.VETERINARY.Buttons.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import gui.Tab;

public class VeterinaryTab implements Tab {

    private final Stage primaryStage;
    private final String email;
    private final Roles role = Roles.VETERINARIO;

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

        // crea un bottone per visualizzare tutte gli ambulatori
        Button selectClinicsButton = new SelectClinicsButton();

        // crea un bottone per visualizzare tutte gli ambulatori in cui lavori
        Button selectYourClinicsButton = new SelectYourClinicsButton(email);

        // crea un bottone per inserire un nuovo ambulatorio in cui lavori
        InsertClinicUWorkInButton insertYourClinicButton = new InsertClinicUWorkInButton(email);

        // crea un bottone per inserire una nuova zona
        InsertNewZoneButton insertNewZoneButton = new InsertNewZoneButton();

        // crea un bottone per inserire un nuovo indirizzo
        Button insertNewAddressButton = new InsertNewAddressButton(primaryStage, this);

        // crea un bottone per inserire un nuovo ambulatorio
        InsertNewClinicButton insertNewClinicButton = new InsertNewClinicButton(primaryStage, this);

        // crea un bottone per eseguire pet rating
        Button petRatingButton = new PetRatingButton(email);

        // crea un bottone per visualizzare i tuoi gruppi
        Button selectYourGroupsButton = new SelectYourGroupsButton(email);

        // crea un bottone per inviare un messaggio
        Button sendMessageButton = new SendMessageButton(email, role.value, primaryStage, this);

        // crea un bottone per visualizzare i messaggi di un gruppo
        Button showMessagesButton = new ShowMessagesButton(email);

        // crea un bottone per visualizzare tutti i cibi
        Button selectFoodsButton = new SelectFoodsButton();

        // crea un bottone per visualizzare tutti gli esercizi
        Button selectExercisesButton = new SelectExercisesButton();

        // crea un bottone per visualizzare tutti i farmaci
        Button selectDrugsButton = new SelectDrugsButton();

        // aggiungi un bottone per chiudere l'applicazione
        Button quitButton = new QuitButton();

        root.getChildren().addAll(userData, insertFoodButton, insertExerciseButton, insertDrugButton,
                insertNewZoneButton, insertNewAddressButton, insertNewClinicButton,
                selectClinicsButton, selectYourClinicsButton, insertYourClinicButton, selectYourGroupsButton,
                sendMessageButton, showMessagesButton, petRatingButton,
                selectFoodsButton,
                selectExercisesButton, selectDrugsButton, quitButton);

        // Create a scene and set it on the stage
        Scene scene = new Scene(root, 400, 300);
        primaryStage.setTitle("ANIMALETTI COCCOLOSI - VETERINARIO");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}