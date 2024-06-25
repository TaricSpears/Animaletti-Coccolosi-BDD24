//visualizza diete di un animale(prop, vet)

package gui.RUN.COMMONS;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

import database.MySQLConnect;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ShowDietsButton extends Button {
    public ShowDietsButton() {
        this.setText("Visualizza diete");
        this.setOnAction(e -> {
            // Create a VBox layout
            VBox root = new VBox();
            root.setAlignment(Pos.CENTER);
            root.setSpacing(20);

            // Create a title
            Text title = new Text("Visualizza diete");
            title.setFont(Font.font(24));

            String query = "SELECT * FROM DIETA";
            try {
                Connection connection = MySQLConnect.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    Stage dialogStage = new Stage();
                    dialogStage.setTitle("Diete");

                    VBox vBox = new VBox(title);
                    vBox.setPadding(new Insets(10));
                    vBox.setSpacing(10);
                    vBox.setAlignment(Pos.CENTER);

                    do {
                        String codiceDieta = resultSet.getString("Codice_Dieta");
                        String nome = resultSet.getString("Nome");
                        String descrizione = resultSet.getString("Descrizione");

                        Text text = new Text(
                                "Codice Dieta: " + codiceDieta + " Nome: " + nome + " Descrizione: " + descrizione);
                        text.setFont(new Font(20));
                        vBox.getChildren().add(text);
                    } while (resultSet.next());

                    Scene scene = new Scene(vBox, 800, 600);
                    dialogStage.setScene(scene);
                    dialogStage.initModality(Modality.APPLICATION_MODAL);
                    dialogStage.show();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error");
                    alert.setContentText("Nessuna dieta presente");
                    alert.showAndWait();
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }

        });

    }
}
