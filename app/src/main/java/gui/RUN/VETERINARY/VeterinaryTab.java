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
        root.setSpacing(5);

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

        // crea un bottone per visualizzare i pazienti
        Button showPatientsButton = new ShowPatientsButton(email);

        // crea un bottone per visualizzare i padroni dei pazienti
        Button viewOwnersButton = new ViewOwnersButton(email);

        // crea un bottone per visualizzare le condizioni cliniche registarte
        Button showClinicalConditionsButton = new ShowClinicalConditionsButton();

        // crea un bottone per inserire una nuova condizione clinica
        Button insertClinicalConditionButton = new InsertClinicalConditionButton();

        // crea un bottone per visualizzare le cartelle mediche di un paziente
        Button showMedicalFoldersButton = new ShowMedicalFoldersButton(email, role.value);
        showMedicalFoldersButton.setText("Visualizza cartella clinica di un animale");

        // crea un bottone per aggiornare la cartella medica di un paziente
        Button updateMedicalFolderButton = new UpdateMedicalFolderButton(email, primaryStage, this);

        // crea un bottone per visualizzare le richieste di visita
        Button showExamRequestsButton = new ShowExamRequestsButton(email);

        // crea un bottone per accettare e svolgere una visita
        Button acceptAndPerformExamButton = new AcceptAndPerformExamButton(email);

        // crea un bottone per richiede un intervento chirurgico
        Button requestClinicalInterventionButton = new RequestClinicalInterventionButton(primaryStage, email, this);

        // crea un bottone per visualizzare i messaggi di un gruppo
        Button showMessagesButton = new ShowMessagesButton(email);

        // crea un bottone per visualizzare tutti i cibi
        Button selectFoodsButton = new SelectFoodsButton();

        // crea un bottone per visualizzare tutti gli esercizi
        Button selectExercisesButton = new SelectExercisesButton();

        // crea un bottone per visualizzare tutti i farmaci
        Button selectDrugsButton = new SelectDrugsButton();

        // crea un bottone per visualizzare gli ultimi 3 referti per un animale
        Button showLast3Button = new ShowLast3Button(email);

        // aggiungi un bottone per chiudere l'applicazione
        Button quitButton = new QuitButton();

        root.getChildren().addAll(userData, insertFoodButton, insertExerciseButton, insertDrugButton,
                insertNewZoneButton, insertNewAddressButton, insertNewClinicButton,
                selectClinicsButton, selectYourClinicsButton, insertYourClinicButton,
                showPatientsButton, viewOwnersButton, showClinicalConditionsButton, insertClinicalConditionButton,
                showMedicalFoldersButton, updateMedicalFolderButton, showExamRequestsButton, acceptAndPerformExamButton,
                requestClinicalInterventionButton,
                selectYourGroupsButton,
                sendMessageButton, showMessagesButton, petRatingButton, showLast3Button,
                selectFoodsButton,
                selectExercisesButton, selectDrugsButton, quitButton);

        // Create a scene and set it on the stage
        Scene scene = new Scene(root, 400, 300);
        primaryStage.setTitle("ANIMALETTI COCCOLOSI - VETERINARIO");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}