package gui.RUN.OWNER.Buttons;

import gui.Tab;
import gui.RUN.OWNER.Actions.RegisterAlimentation;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class RegisterAlimentationButton extends Button {
    public RegisterAlimentationButton(final String email, final Stage primaryStage, final Tab previousTab) {
        this.setText("Registra Alimentazione");
        this.setOnAction(click -> {
            RegisterAlimentation registerAlimentation = new RegisterAlimentation();
            registerAlimentation.show(email, primaryStage, previousTab);
        });
    }
}
