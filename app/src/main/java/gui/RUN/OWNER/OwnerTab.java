package gui.RUN.OWNER;

import java.sql.SQLException;

import gui.RUN.COMMONS.*;
import gui.RUN.OWNER.Buttons.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
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
        // Create a GridPane layout
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10, 10, 10, 10));

        // crea un textfield per mostrare i dati dello user loggato
        Text userData = new UserDataText(email);

        Button insertNewAddressButton = new InsertNewAddressButton(primaryStage, this);
        Button insertNewZoneButton = new InsertNewZoneButton();
        Button insertResidenceButton = new InsertResidenceButton(primaryStage, this, email);
        Button showYourResidencesButton = new ShowYourResidencesButton(email);
        Button petRatingButton = new PetRatingButton(email);
        Button showDietsButton = new ShowDietsButton(email, role);
        Button selectYourGroupsButton = new SelectYourGroupsButton(email);
        Button sendMessageButton = new SendMessageButton(email, role.value, primaryStage, this);
        Button showMessagesButton = new ShowMessagesButton(email);
        Button reportMessageButton = new ReportMessageButton(email, primaryStage, this);
        Button selectFoodsButton = new SelectFoodsButton();
        Button selectExercisesButton = new SelectExercisesButton();
        Button selectDrugsButton = new SelectDrugsButton();
        Button showMedicalFoldersButton = new ShowMedicalFoldersButton(email, role.value);
        showMedicalFoldersButton.setText("Visualizza cartella clinica di un tuo animale");
        Button parcelleButton = new ParcelleButton(email);
        Button payButton = new PayButton(email);
        Button reportButton = new ReportButton(email);
        Button showYourAnimalsButton = new ShowYourAnimalsButton(email);
        Button registerYourNewPetButton = new RegisterYourNewPetButton(primaryStage, email, this);
        Button viewSpeciesStatsButton = new ViewSpeciesStatsButton();
        Button registerAlimentationButton = new RegisterAlimentationButton(email, primaryStage,
                this);
        Button showVeterinaryButton = new ShowVeterinaryButton();
        Button acceptOperationButton = new AcceptOperationButton(email);
        Button theMostRaceButton = new TheMostRaceButton();
        Button orderVetButton = new OrderVetButton(email);
        Button showTherapiesFromRefertoButton = new ShowTherapiesFromRefertoButton(email, role, primaryStage, this);
        Button readDailyMenuButton = new ReadDailyMenuButton(email);
        Button readDailyWorkoutButton = new ReadDailyWorkoutButton(email);
        Button readPharmaDrugsButton = new ReadDailyMedicineButton(email);
        Button bookAVisitButton = new Button();
        bookAVisitButton.setText("Prenota una visita");
        bookAVisitButton.setOnAction(e -> {
            BookAVisitStage bookAVisit = new BookAVisitStage(primaryStage, email);
            bookAVisit.show();
        });
        Button showRefertiButton = new ShowRefertiButton(email, role);
        Button quitButton = new QuitButton();

        // aggiungi i bottoni al gridPane
        gridPane.add(userData, 0, 0, 2, 1);
        gridPane.add(insertNewAddressButton, 0, 1);
        gridPane.add(insertNewZoneButton, 1, 1);
        gridPane.add(insertResidenceButton, 0, 2);
        gridPane.add(showYourResidencesButton, 1, 2);
        gridPane.add(showDietsButton, 0, 3);
        gridPane.add(reportMessageButton, 1, 3);
        gridPane.add(parcelleButton, 0, 4);
        gridPane.add(payButton, 1, 4);
        gridPane.add(reportButton, 0, 5);
        gridPane.add(petRatingButton, 1, 5);
        gridPane.add(selectYourGroupsButton, 0, 6);
        gridPane.add(sendMessageButton, 1, 6);
        gridPane.add(showYourAnimalsButton, 0, 7);
        gridPane.add(registerYourNewPetButton, 1, 7);
        gridPane.add(viewSpeciesStatsButton, 0, 8);
        gridPane.add(registerAlimentationButton, 1, 8);
        gridPane.add(showMedicalFoldersButton, 0, 9);
        gridPane.add(showVeterinaryButton, 1, 9);
        gridPane.add(acceptOperationButton, 0, 10);
        gridPane.add(theMostRaceButton, 1, 10);
        gridPane.add(orderVetButton, 0, 11);
        gridPane.add(readDailyMenuButton, 1, 11);
        gridPane.add(showMessagesButton, 0, 12);
        gridPane.add(selectFoodsButton, 1, 12);
        gridPane.add(selectExercisesButton, 0, 13);
        gridPane.add(selectDrugsButton, 1, 13);
        gridPane.add(bookAVisitButton, 0, 14);
        gridPane.add(readDailyWorkoutButton, 1, 14);
        gridPane.add(showTherapiesFromRefertoButton, 0, 15);
        gridPane.add(readPharmaDrugsButton, 1, 15);
        gridPane.add(showRefertiButton, 0, 16);
        gridPane.add(quitButton, 1, 17);

        // Create a scene and set it on the stage
        Scene scene = new Scene(gridPane, 800, 600);
        primaryStage.setTitle("ANIMALETTI COCCOLOSI - VETERINARIO");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}