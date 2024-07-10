package gui.RUN.VETERINARY.Actions;

import gui.Tab;
import gui.RUN.COMMONS.CheckBoxMatrixRegime;
import javafx.stage.Stage;

public class RegisterRegime {
    public void show(final String idTerapia, final Stage primaryStage, final Tab previousTab) {
        CheckBoxMatrixRegime checkBoxMatrixRegime = new CheckBoxMatrixRegime();
        checkBoxMatrixRegime.show(primaryStage, previousTab, Integer.parseInt(idTerapia));
    }
}
