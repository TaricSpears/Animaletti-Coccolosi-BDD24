package gui.RUN.VETERINARY;

import java.sql.SQLException;
import gui.RUN.COMMONS.*;
import gui.RUN.VETERINARY.Buttons.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
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
        // Create a GridPane layout
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10, 10, 10, 10));

        // crea un textfield per mostrare i dati dello user loggato
        Text userData = new UserDataText(email);

        // crea i bottoni
        Button insertFoodButton = new InsertFoodButton();
        Button insertExerciseButton = new InsertExerciseButton();
        Button insertDrugButton = new InsertDrugButton();
        Button selectClinicsButton = new SelectClinicsButton();
        Button selectYourClinicsButton = new SelectYourClinicsButton(email);
        Button insertYourClinicButton = new InsertClinicUWorkInButton(email);
        Button insertNewZoneButton = new InsertNewZoneButton();
        Button insertNewAddressButton = new InsertNewAddressButton(primaryStage, this);
        Button insertNewClinicButton = new InsertNewClinicButton(primaryStage, this);
        Button petRatingButton = new PetRatingButton(email);
        Button selectYourGroupsButton = new SelectYourGroupsButton(email);
        Button sendMessageButton = new SendMessageButton(email, role.value, primaryStage, this);
        Button showPatientsButton = new ShowPatientsButton(email);
        Button viewOwnersButton = new ViewOwnersButton(email);
        Button showClinicalConditionsButton = new ShowClinicalConditionsButton();
        Button insertClinicalConditionButton = new InsertClinicalConditionButton();
        Button showMedicalFoldersButton = new ShowMedicalFoldersButton(email, role.value);
        showMedicalFoldersButton.setText("Visualizza cartella clinica di un animale");
        Button updateMedicalFolderButton = new UpdateMedicalFolderButton(email, primaryStage, this);
        Button showExamRequestsButton = new ShowExamRequestsButton(email);
        Button acceptAndPerformExamButton = new AcceptAndPerformExamButton(email, primaryStage, this);
        Button requestClinicalInterventionButton = new RequestClinicalInterventionButton(primaryStage, email, this);
        Button showMessagesButton = new ShowMessagesButton(email);
        Button selectFoodsButton = new SelectFoodsButton();
        Button selectExercisesButton = new SelectExercisesButton();
        Button selectDrugsButton = new SelectDrugsButton();
        Button showDietsButton = new ShowDietsButton(email, role);
        Button showLast3Button = new ShowLast3Button(email);
        Button addParcelButton = new AddParcelButton(email);
        Button showLastTherapiesButton = new ShowLatestTherapiesButton(email, role);
        Button quitButton = new QuitButton();

        // aggiungi i bottoni al gridPane
        gridPane.add(userData, 0, 0, 2, 1);
        gridPane.add(insertFoodButton, 0, 1);
        gridPane.add(insertExerciseButton, 1, 1);
        gridPane.add(insertDrugButton, 0, 2);
        gridPane.add(selectClinicsButton, 1, 2);
        gridPane.add(selectYourClinicsButton, 0, 3);
        gridPane.add(insertYourClinicButton, 1, 3);
        gridPane.add(insertNewZoneButton, 0, 4);
        gridPane.add(insertNewAddressButton, 1, 4);
        gridPane.add(insertNewClinicButton, 0, 5);
        gridPane.add(petRatingButton, 1, 5);
        gridPane.add(selectYourGroupsButton, 0, 6);
        gridPane.add(sendMessageButton, 1, 6);
        gridPane.add(showPatientsButton, 0, 7);
        gridPane.add(viewOwnersButton, 1, 7);
        gridPane.add(showClinicalConditionsButton, 0, 8);
        gridPane.add(insertClinicalConditionButton, 1, 8);
        gridPane.add(showMedicalFoldersButton, 0, 9);
        gridPane.add(updateMedicalFolderButton, 1, 9);
        gridPane.add(showExamRequestsButton, 0, 10);
        gridPane.add(acceptAndPerformExamButton, 1, 10);
        gridPane.add(requestClinicalInterventionButton, 0, 11);
        gridPane.add(addParcelButton, 1, 11);
        gridPane.add(showMessagesButton, 0, 12);
        gridPane.add(selectFoodsButton, 1, 12);
        gridPane.add(selectExercisesButton, 0, 13);
        gridPane.add(selectDrugsButton, 1, 13);
        gridPane.add(showDietsButton, 0, 14);
        gridPane.add(showLast3Button, 1, 14);
        gridPane.add(showLastTherapiesButton, 0, 15);
        gridPane.add(quitButton, 1, 15);

        // Create a scene and set it on the stage
        Scene scene = new Scene(gridPane, 800, 600);
        primaryStage.setTitle("ANIMALETTI COCCOLOSI - VETERINARIO");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}