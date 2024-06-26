//parcella:inserisci IDprop(per pagamento)e i campi della parcella.setta a false pagata

package gui.RUN.VETERINARY.Buttons;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.cj.x.protobuf.MysqlxPrepare.Prepare;

import database.MySQLConnect;
import gui.Tab;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AddParcelButton extends Button {
    public AddParcelButton(final String Email) {
        this.setText("Inserisci una nuova parcella");
        this.setOnAction(click -> {
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Inserisci i dati della parcella");

            Text title = new Text("Inserisci i dati della parcella:");
            title.setFont(new Font(20));

            TextField importo = new TextField();
            importo.setPromptText("Importo");

            TextField descrizione = new TextField();
            descrizione.setPromptText("Descrizione");

            TextField emailProprietario = new TextField();
            emailProprietario.setPromptText("Email proprietario");

            Button submit = new Button("Invia");
            submit.setOnAction(e -> {
                try {
                    String query2 = "SELECT a.Email FROM animale a, visita v WHERE a.Codice_Identificativo = v.Codice_Identificativo AND v.ACC_Email = ? GROUP BY a.Codice_Identificativo";

                    Connection connection = MySQLConnect.getConnection();

                    PreparedStatement preparedStatement2 = connection.prepareStatement(query2);

                    preparedStatement2.setString(1, Email);

                    ResultSet resultSet2 = preparedStatement2.executeQuery();
                    if (!resultSet2.next()) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Errore");
                        alert.setHeaderText("Errore");
                        alert.setContentText("Non ci sono proprietari per l'animale");
                        alert.showAndWait();
                        return;
                    }
                    String insertQuery = "INSERT INTO parcella (Importo, Descrizione, Email) VALUES (?, ?, ?)";
                    String selectCodParcella = "SELECT CodParcella FROM parcella ORDER BY CodParcella DESC LIMIT 1";

                    PreparedStatement preparedStatement3 = connection.prepareStatement(insertQuery);
                    preparedStatement3.setString(1, importo.getText());
                    preparedStatement3.setString(2, descrizione.getText());
                    preparedStatement3.setString(3, emailProprietario.getText());
                    preparedStatement3.executeUpdate();
                    ResultSet resultSet3 = preparedStatement3.executeQuery(selectCodParcella);
                    resultSet3.next();
                    String codParcella = resultSet3.getString("CodParcella");

                    String insertPagamento = "INSERT INTO pagamento (CodParcella, Pagata, Email) VALUES (?, 0, ?)";
                    PreparedStatement preparedStatement4 = connection.prepareStatement(insertPagamento);
                    preparedStatement4.setString(1, codParcella);
                    preparedStatement4.setString(2, emailProprietario.getText());
                    preparedStatement4.executeUpdate();

                } catch (Exception ex) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Errore");
                    alert.setHeaderText("Errore");
                    alert.setContentText("Errore durante l'inserimento della parcella");
                    alert.showAndWait();
                    ex.printStackTrace();
                }
            });
            Button quitButton = new Button("Chiudi");
            quitButton.setOnAction(e -> dialogStage.close());

            VBox vBox = new VBox(title, importo, descrizione, emailProprietario, submit, quitButton);
            vBox.setSpacing(10);
            vBox.setAlignment(Pos.CENTER);

            Scene scene = new Scene(vBox, 400, 400);
            dialogStage.setScene(scene);
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.show();
        });
    }
}
