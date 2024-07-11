package gui.RUN.ADMIN;

import java.sql.SQLException;
import gui.RUN.ADMIN.Buttons.*;
import gui.RUN.COMMONS.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
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
        // Create a GridPane layout
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10, 10, 10, 10));

        // crea un textfield per mostrare i dati dello user loggato
        Text userData = new UserDataText(email);

        Button showUsersButton = new ShowUserButton();
        Button showAnimalsButton = new ShowAnimalsButton();
        Button showUserReportsButton = new ShowUserReportsButton();
        Button petRatingButton = new PetRatingButton(email);
        Button insertNewAddressButton = new InsertNewAddressButton(primaryStage, this);
        Button insertNewZoneButton = new InsertNewZoneButton();
        Button findMessageButton = new FindMessageButton(email);
        Button blockUserButton = new BlockUserButton();
        Button selectYourGroupsButton = new SelectYourGroupsButton(email);
        Button sendMessageButton = new SendMessageButton(email, role.value, primaryStage, this);
        Button showMessagesButton = new ShowMessagesButton(email);
        Button selectFoodsButton = new SelectFoodsButton();
        Button selectExercisesButton = new SelectExercisesButton();
        Button selectDrugsButton = new SelectDrugsButton();
        Button changeRoleButton = new ChangeRoleButton(primaryStage, this, email);
        Button backButton = new BackToInitiablTabButton(primaryStage);
        Button quitButton = new QuitButton();

        // set backrgound color of this tab to ligh yellow
        gridPane.setStyle("-fx-background-color: #FFFFE0;");

        // Add the buttons to the grid
        gridPane.add(userData, 0, 0);
        gridPane.add(showUsersButton, 0, 1);
        gridPane.add(showAnimalsButton, 1, 1);
        gridPane.add(showUserReportsButton, 0, 2);
        gridPane.add(petRatingButton, 1, 2);
        gridPane.add(insertNewAddressButton, 0, 3);
        gridPane.add(insertNewZoneButton, 1, 3);
        gridPane.add(findMessageButton, 0, 4);
        gridPane.add(blockUserButton, 1, 4);
        gridPane.add(selectYourGroupsButton, 0, 5);
        gridPane.add(sendMessageButton, 1, 5);
        gridPane.add(showMessagesButton, 0, 6);
        gridPane.add(selectFoodsButton, 1, 6);
        gridPane.add(selectExercisesButton, 0, 7);
        gridPane.add(selectDrugsButton, 1, 7);
        gridPane.add(changeRoleButton, 0, 8);
        gridPane.add(backButton, 1, 8);
        gridPane.add(quitButton, 0, 9);

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