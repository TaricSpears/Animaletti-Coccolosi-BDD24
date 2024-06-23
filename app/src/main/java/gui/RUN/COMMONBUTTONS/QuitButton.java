package gui.RUN.COMMONBUTTONS;

import javafx.scene.control.Button;

public class QuitButton extends Button {
    public QuitButton() {
        this.setText("Esci");
        this.setOnAction(e -> {
            System.exit(0);
        });
    }
}
