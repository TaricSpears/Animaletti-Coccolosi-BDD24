package gui.RUN.OWNER.Buttons;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import database.MySQLConnect;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class OrderVetButton extends Button {
    public OrderVetButton(String email) {
        this.setText("Ordina Vet.i per voto medio nella tua zona/e");
        this.setOnAction(e -> {
            String query = "SELECT c.Email as VetEmail, a.ZONA_nome as NomeZona, a.Nome as NomeAmb, v.Valutazione_media as Vmedia "
                    +
                    "FROM collocazione c " +
                    "JOIN ambulatorio a ON c.Nome = a.Nome " +
                    "JOIN veterinario v ON c.Email = v.Email " +
                    "WHERE a.ZONA_nome IN (SELECT r.ZONA_nome " +
                    "FROM residenza r " +
                    "WHERE r.Email = ?) " +
                    "ORDER BY a.ZONA_nome DESC, v.Valutazione_media DESC";

            try {
                Connection connection = MySQLConnect.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, email);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    Stage dialogStage = new Stage();
                    dialogStage.setTitle("Veterinari nella tua zona");

                    Text title = new Text("Veterinari nella tua zona ordinati per valutazione media:");
                    title.setFont(new Font(20));

                    VBox vBox = new VBox(title);
                    vBox.setPadding(new Insets(10));
                    vBox.setSpacing(10);
                    vBox.setAlignment(Pos.CENTER);

                    do {
                        String vetEmail = resultSet.getString("VetEmail");
                        String zona = resultSet.getString("NomeZona");
                        String nomeAmb = resultSet.getString("NomeAmb");
                        String valutazioneMedia = resultSet.getString("Vmedia");

                        vBox.getChildren().addAll(
                                new Text("Email: " + vetEmail),
                                new Text("NomeZona: " + zona),
                                new Text("NomeAmbulatorio: " + nomeAmb),
                                new Text("Valutazione Media: " + valutazioneMedia),
                                new Text());
                    } while (resultSet.next());

                    Scene scene = new Scene(vBox);
                    dialogStage.setScene(scene);
                    dialogStage.initModality(Modality.APPLICATION_MODAL);
                    dialogStage.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Errore");
                    alert.setHeaderText("Errore");
                    alert.setContentText("Nessun veterinario nella tua zona");
                    alert.showAndWait();
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
    }
}
