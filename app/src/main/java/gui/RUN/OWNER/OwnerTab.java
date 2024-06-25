package gui.RUN.OWNER;

import java.sql.SQLException;

import gui.RUN.COMMONS.*;
import gui.RUN.OWNER.Buttons.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import gui.Tab;

public class OwnerTab implements Tab {

    private final Stage primaryStage;
    private final String email;
    private final Roles role = Roles.PROPRIETARIO;

    public OwnerTab(final Stage primaryStage, final String email) {
        this.primaryStage = primaryStage;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void show() throws SQLException {
        // Create a VBox layout
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(5);

        // crea un textfield per mostrare i dati dello user loggato
        Text userData = new UserDataText(email);

        // crea un bottone per inserire un unovo indirizzo
        Button insertNewAddressButton = new InsertNewAddressButton(primaryStage, this);

        // crea un bottone per inserire una nuova zona
        Button insertNewZoneButton = new InsertNewZoneButton();

        // crea un bottone per inserire una tua nuova residenza
        Button insertResidenceButton = new InsertResidenceButton(primaryStage, this, email);

        // crea un bottone per visualizzare le tue residenze
        Button showYourResidencesButton = new ShowYourResidencesButton(email);

        // crea un bottone per eseguire pet rating
        Button petRatingButton = new PetRatingButton(email);

        // crea bottone per visualizzare le diete
        Button showDietsButton = new ShowDietsButton();

        // crea un bottone per visualizzare i tuoi gruppi
        Button selectYourGroupsButton = new SelectYourGroupsButton(email);

        // crea un bottone per inviare un messaggio
        Button sendMessageButton = new SendMessageButton(email, role.value, primaryStage, this);

        // crea un bottone per visualizzare i messaggi di un gruppo
        Button showMessagesButton = new ShowMessagesButton(email);

        // crea un btotone pe segnalare un messaggio
        Button reportMessageButton = new ReportMessageButton(email, primaryStage, this);

        // crea un bottone per visualizzare tutti i cibi
        Button selectFoodsButton = new SelectFoodsButton();

        // crea un bottone per visualizzare tutti gli esercizi
        Button selectExercisesButton = new SelectExercisesButton();

        // crea un bottone per visualizzare tutti i farmaci
        Button selectDrugsButton = new SelectDrugsButton();

        // crea un bottone per visualizzare la cartella clinica di un tuo animale
        Button showMedicalFoldersButton = new ShowMedicalFoldersButton(email, role.value);
        showMedicalFoldersButton.setText("Visualizza cartella clinica di un tuo animale");

        // visualizza parcelle non pagate: visualizza parcelle afferenti al prop con
        // pagata a false
        Button parcelleButton = new ParcelleButton(email);

        // crea un bottone per pagare una parcella
        Button payButton = new PayButton(email);

        // crea un bottone per segnalere un utente
        Button reportButton = new ReportButton(email);

        // aggiungi un bottone per chiudere l'applicazione
        Button showYourAnimalsButton = new ShowYourAnimalsButton(email);

        // crea un botttone per registrare un nuovo animale
        Button registerYourNewPetButton = new RegisterYourNewPetButton(primaryStage, email, this);

        // crea un bottone per visualizzare le statistiche delle specie
        Button viewSpeciesStatsButton = new ViewSpeciesStatsButton();

        // crea un bottone per visualizzare i veterinari
        Button showVeterinaryButton = new ShowVeterinaryButton();

        // crea un bottone per acctettare un intervento
        Button acceptOperationButton = new AcceptOperationButton(email);

        // crea un bottone per sapere quali sono le razze più propense ad avere una
        Button theMostRaceButton = new TheMostRaceButton();

        // crea un bottone per ordinare i veterinari per valutazione
        Button orderVetButton = new OrderVetButton(email);

        // crea un bottone per mostrare le ultime terapie
        Button showLastTherapiesButton = new ShowLatestTherapiesButton(email, role);

        // crea un bottone per prenotare una visita
        Button bookAVisitButton = new Button();
        bookAVisitButton.setText("Prenota una visita");
        bookAVisitButton.setOnAction(e -> {
            BookAVisitStage bookAVisit = new BookAVisitStage(primaryStage, email);
            bookAVisit.show();
        });

        // aggiungi un bottone per chiudere l'applicazione
        Button quitButton = new QuitButton();

        root.getChildren().addAll(userData, showYourAnimalsButton, registerYourNewPetButton,
                showMedicalFoldersButton,
                insertNewAddressButton,
                insertNewZoneButton, showDietsButton,
                insertResidenceButton, showYourResidencesButton, selectYourGroupsButton, sendMessageButton,
                showMessagesButton, reportMessageButton, petRatingButton, viewSpeciesStatsButton,
                selectFoodsButton, selectExercisesButton,
                selectDrugsButton, acceptOperationButton, showVeterinaryButton, bookAVisitButton, parcelleButton,
                payButton, reportButton, theMostRaceButton, orderVetButton, showLastTherapiesButton,
                quitButton);

        // Create a scene and set it on the stage
        Scene scene = new Scene(root, 400, 300);
        primaryStage.setTitle("ANIMALETTI COCCOLOSI - PROPRIETARIO");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}