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
        Button showRefertiButton = new ShowRefertiButton(email, role);
        Button addParcelButton = new AddParcelButton(email);
        Button showTherapiesFromRefertoButton = new ShowTherapiesFromRefertoButton(email, role, primaryStage, this);
        Button insertTherapyForRefertoButton = new InsertTherapyForRefertoButton(email, primaryStage, this);
        Button populateTherapyWithDietButton = new PopulateTherapyWithDietButton(email, primaryStage, this);
        Button populateTherapyWithRegimeButton = new PopulateTherapyWithRegimeButton(email, primaryStage, this);
        Button populateTherapyWithWorkoutButton = new PopulateTherapyWithWorkoutButton(email, primaryStage, this);
        Button changeRoleButton = new ChangeRoleButton(primaryStage, this, email);
        Button backButton = new BackToInitiablTabButton(primaryStage);
        Button quitButton = new QuitButton();

        // set backgroundn color of tthis tab to light blue
        gridPane.setStyle("-fx-background-color: #e69797;");

        // aggiungi i bottoni al gridPane
        gridPane.add(userData, 0, 0, 2, 1);
        gridPane.add(insertFoodButton, 0, 1);
        gridPane.add(insertExerciseButton, 1, 1);
        gridPane.add(insertDrugButton, 2, 1);
        gridPane.add(selectClinicsButton, 0, 2);
        gridPane.add(selectYourClinicsButton, 1, 2);
        gridPane.add(insertYourClinicButton, 2, 2);
        gridPane.add(insertNewZoneButton, 0, 3);
        gridPane.add(insertNewAddressButton, 1, 3);
        gridPane.add(insertNewClinicButton, 2, 3);
        gridPane.add(petRatingButton, 0, 4);
        gridPane.add(selectYourGroupsButton, 1, 4);
        gridPane.add(sendMessageButton, 2, 4);
        gridPane.add(showPatientsButton, 0, 5);
        gridPane.add(viewOwnersButton, 1, 5);
        gridPane.add(showClinicalConditionsButton, 2, 5);
        gridPane.add(insertClinicalConditionButton, 0, 6);
        gridPane.add(showMedicalFoldersButton, 1, 6);
        gridPane.add(updateMedicalFolderButton, 2, 6);
        gridPane.add(showExamRequestsButton, 0, 7);
        gridPane.add(acceptAndPerformExamButton, 1, 7);
        gridPane.add(requestClinicalInterventionButton, 2, 7);
        gridPane.add(addParcelButton, 0, 8);
        gridPane.add(showMessagesButton, 1, 8);
        gridPane.add(selectFoodsButton, 2, 8);
        gridPane.add(selectExercisesButton, 0, 9);
        gridPane.add(selectDrugsButton, 0, 9);
        gridPane.add(showDietsButton, 1, 9);
        gridPane.add(showRefertiButton, 2, 9);
        gridPane.add(showTherapiesFromRefertoButton, 0, 10);
        gridPane.add(insertTherapyForRefertoButton, 1, 10);
        gridPane.add(populateTherapyWithDietButton, 2, 10);
        gridPane.add(populateTherapyWithRegimeButton, 0, 11);
        gridPane.add(populateTherapyWithWorkoutButton, 1, 11);
        gridPane.add(changeRoleButton, 0, 12);
        gridPane.add(backButton, 1, 12);
        gridPane.add(quitButton, 2, 12);

        // set the style of all buttons
        gridPane.getChildren().stream().filter(node -> node instanceof Button).forEach(node -> {
            Button button = (Button) node;
            button.setStyle(
                    "-fx-background-color: D1EAEA; -fx-text-fill: #374545; -fx-font-size: 12px; -fx-font-weight: bold; -fx-padding: 5px 12px; -fx-border-radius: 12px; -fx-cursor: hand;");
        });

        // Create a scene and set it on the stage
        Scene scene = new Scene(gridPane, 800, 600);
        primaryStage.setTitle("ANIMALETTI COCCOLOSI - VETERINARIO");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}