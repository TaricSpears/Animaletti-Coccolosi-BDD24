package gui.RUN.COMMONS;

import javafx.scene.control.Button;

public class QuitButton extends Button {
    public QuitButton() {
        this.setText("ESCI");
        this.setOnAction(e -> {
            System.exit(0);
        });
        this.setStyle("-fx-font-weight: bold;");
    }
}
