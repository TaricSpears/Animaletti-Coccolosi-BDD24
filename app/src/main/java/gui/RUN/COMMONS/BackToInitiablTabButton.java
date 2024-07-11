package gui.RUN.COMMONS;

import gui.START.InitialTab;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class BackToInitiablTabButton extends Button {
    public BackToInitiablTabButton(final Stage primaryStage) {
        super("TORNA ALLA SCHERMATA INIZIALE");
        setOnAction(e -> {
            try {
                InitialTab initialTab = new InitialTab(primaryStage);
                initialTab.show();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
    }
}
