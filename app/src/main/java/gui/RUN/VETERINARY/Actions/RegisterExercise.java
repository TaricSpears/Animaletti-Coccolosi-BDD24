package gui.RUN.VETERINARY.Actions;

import gui.Tab;
import gui.RUN.COMMONS.CheckBoxMatrixWorkout;
import javafx.stage.Stage;

public class RegisterExercise {
    public void show(final String idTerapia, final Stage primaryStage, final Tab previousTab) {
        CheckBoxMatrixWorkout checkBoxMatrixWorkout = new CheckBoxMatrixWorkout();
        checkBoxMatrixWorkout.show(primaryStage, previousTab, Integer.parseInt(idTerapia));
    }
}
