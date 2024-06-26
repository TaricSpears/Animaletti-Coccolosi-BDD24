package gui.RUN.VETERINARY.Buttons;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import database.MySQLConnect;
import gui.Tab;
import gui.RUN.COMMONS.CheckBoxMatrixRegime;
import gui.RUN.COMMONS.CheckBoxMatrixWorkout;
import gui.RUN.OWNER.Buttons.RegisterAlimentation;
import gui.START.InitialTab;
import gui.START.LoginRolePicker;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PopulateTherapyButton extends Button {
    public PopulateTherapyButton(final String email, final Stage primaryStage, final Tab previousTab) {
        this.setText("Popola terapia");

        this.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Popola terapia");
            dialog.setHeaderText("Inserisci il codice identificativo della terapia");
            dialog.setContentText("Codice identificativo:");
            dialog.showAndWait().ifPresent(idTerapia -> {
                if (isValid(idTerapia)) {
                    // Create a VBox layout
                    VBox root = new VBox();
                    root.setAlignment(Pos.CENTER);
                    root.setSpacing(20);

                    // Create a title
                    Text title = new Text("Animaletti Coccolosi");
                    title.setFont(Font.font(24));

                    CheckBox checkBox1 = new CheckBox("Inserisci dieta");

                    CheckBox checkBox2 = new CheckBox("Inserisci workout settimanale");

                    CheckBox checkBox3 = new CheckBox("Inserisci regime farmacologico settimanale");

                    // Create login button
                    Button inserisciButton = new Button("Inserisci");
                    inserisciButton.setOnAction(ev -> {
                        if (checkBox1.isSelected()) {
                            RegisterAlimentation registerAlimentation = new RegisterAlimentation();
                            registerAlimentation.show(email, primaryStage, idTerapia);
                        }
                        if (checkBox2.isSelected()) {
                            CheckBoxMatrixWorkout checkBoxMatrixWorkout = new CheckBoxMatrixWorkout();
                            checkBoxMatrixWorkout.show(primaryStage, previousTab, Integer.parseInt(idTerapia));
                        }
                        if (checkBox3.isSelected()) {
                            CheckBoxMatrixRegime checkBoxMatrixRegime = new CheckBoxMatrixRegime();
                            checkBoxMatrixRegime.show(primaryStage, previousTab, Integer.parseInt(idTerapia));
                        }
                    });

                    // Add components to the layout
                    root.getChildren().addAll(title, checkBox1, checkBox2, checkBox3, inserisciButton);

                    Scene scene = new Scene(root, 400, 300);
                    primaryStage.setTitle("Animaletti Coccolosi");
                    primaryStage.setScene(scene);
                    primaryStage.show();
                }
            });
        });
    }

    private boolean isValid(String idTerapia) {
        if (idTerapia.isEmpty()) {
            return false;
        }
        String query = "SELECT * FROM terapia WHERE Codice_Terapia = ?";
        try {
            Connection connection = MySQLConnect.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, idTerapia);
            ResultSet rs = preparedStatement.executeQuery();
            if (!rs.next()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Errore");
                alert.setHeaderText("Errore nell'inserimento del codice identificativo");
                alert.setContentText("Il campo non può essere vuoto");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}