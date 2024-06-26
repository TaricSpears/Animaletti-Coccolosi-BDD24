package gui.RUN.ADMIN;

import java.sql.SQLException;
import gui.RUN.ADMIN.Buttons.*;
import gui.RUN.COMMONS.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import gui.Tab;

public class AdminTab implements Tab {

    private final Stage primaryStage;
    private final String email;
    private final Roles role = Roles.AMMINISTRATORE;

    public AdminTab(final Stage primaryStage, final String email) {
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

        // crea un bottone per visualizzare tutti gli utenti
        Button showUsersButton = new ShowUserButton();

        // crea un bottone per visualizzare tutti gli animali
        Button showAnimalsButton = new ShowAnimalsButton();

        // crea un bottone per visualizzare tutte le segnalazioni
        Button showUserReportsButton = new ShowUserReportsButton();

        // crea un bottone per eseguire pet rating
        Button petRatingButton = new PetRatingButton(email);

        // crea un bottone per inserire un unovo indirizzo
        Button insertNewAddressButton = new InsertNewAddressButton(primaryStage, this);

        // crea un bottone per inserire una nuova zona
        Button insertNewZoneButton = new InsertNewZoneButton();

        // crea un bottone per trovare un messaggio
        Button findMessageButton = new FindMessageButton(email);

        // crea un bottone per bloccare un utente
        Button blockUserButton = new BlockUserButton();

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

        root.getChildren().addAll(userData, showUsersButton, showAnimalsButton, showUserReportsButton,
                insertNewAddressButton, insertNewZoneButton, findMessageButton,
                blockUserButton,
                selectYourGroupsButton, sendMessageButton, showMessagesButton, petRatingButton,
                selectFoodsButton, selectExercisesButton, selectDrugsButton,
                quitButton);

        // Create a scene and set it on the stage
        Scene scene = new Scene(root, 400, 300);
        primaryStage.setTitle("ANIMALETTI COCCOLOSI - AMMINISTRATORE");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}