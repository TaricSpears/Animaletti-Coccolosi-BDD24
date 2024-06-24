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
        root.setSpacing(20);

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

        // crea un bottone per visualizzare tutti i cibi
        Button selectFoodsButton = new SelectFoodsButton();

        // crea un bottone per visualizzare tutti gli esercizi
        Button selectExercisesButton = new SelectExercisesButton();

        // crea un bottone per visualizzare tutti i farmaci
        Button selectDrugsButton = new SelectDrugsButton();

        // aggiungi un bottone per chiudere l'applicazione
        Button quitButton = new QuitButton();
        Button showYourAnimalsButton = new ShowYourAnimalsButton(email);
        Button bookAVisitButton = new Button();
        bookAVisitButton.setText("Prenota una visita");
        bookAVisitButton.setOnAction(e -> {
            BookAVisitStage bookAVisit = new BookAVisitStage(primaryStage, email);
            bookAVisit.show();
        });

        root.getChildren().addAll(userData, showYourAnimalsButton, insertNewAddressButton, insertNewZoneButton,
                insertResidenceButton, showYourResidencesButton,
                selectFoodsButton, selectExercisesButton,
                selectDrugsButton, bookAVisitButton, quitButton);

        // Create a scene and set it on the stage
        Scene scene = new Scene(root, 400, 300);
        primaryStage.setTitle("ANIMALETTI COCCOLOSI - PROPRIETARIO");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}