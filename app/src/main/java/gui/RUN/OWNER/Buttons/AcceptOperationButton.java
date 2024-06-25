package gui.RUN.OWNER.Buttons;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

import database.MySQLConnect;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class AcceptOperationButton extends Button {

    public AcceptOperationButton(String email) {
        this.setText("Accetta Operazione");
        this.setOnAction(e -> {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Accept Operation");
            String query = "SELECT * FROM Intervento WHERE ID_Intervento NOT IN (SELECT ID_Intervento FROM Accettazione_I) AND Codice_Identificativo IN (SELECT Codice_Identificativo FROM animale WHERE Email = '"
                    + email + "') ORDER BY Data LIMIT 1";
            try {
                PreparedStatement preparedStatement = MySQLConnect.getConnection().prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {

                    Text text = new Text("Do you want to accept the following operation?");
                    text.setFont(new Font(20));
                    Text text2 = new Text("Date: " + resultSet.getString("Data"));
                    Text text4 = new Text("Time: " + resultSet.getString("Ora"));
                    final String ID_Intervento = resultSet.getString("ID_Intervento");
                    Text text1 = new Text("ID_intervento: " + ID_Intervento);
                    Text text3 = new Text("Description: " + resultSet.getString("Descrizione"));
                    Text text5 = new Text("CID_Animale: " + resultSet.getInt("Codice_Identificativo"));
                    final String Email_Vet = resultSet.getString("Email");
                    Text text6 = new Text("Email_Vet: " + Email_Vet);
                    VBox vBox = new VBox(text, text1, text2, text3, text4, text5, text6);
                    vBox.setAlignment(Pos.CENTER);
                    dialog.getDialogPane().setContent(vBox);
                    dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.NO);
                    Optional<ButtonType> result = dialog.showAndWait();

                    if (result.get() == ButtonType.OK) {
                        try {
                            String query2 = "SELECT * FROM Intervento WHERE ID_Intervento IN (SELECT ID_Intervento FROM Accettazione_I) AND Data = '"
                                    + resultSet.getString("Data") + "' AND Ora = '" + resultSet.getString("Ora")
                                    + "' AND Email = '" + resultSet.getString("Email") + "'";
                            PreparedStatement preparedStatement2 = MySQLConnect.getConnection()
                                    .prepareStatement(query2);
                            ResultSet resultSet2 = preparedStatement2.executeQuery();
                            if (resultSet2.next()) {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Operation Already Accepted");
                                alert.setHeaderText("Operation Already Accepted");
                                alert.showAndWait();
                                return;
                            }
                            String query1 = "INSERT INTO Accettazione_I VALUES(?,?,?)";
                            PreparedStatement preparedStatement1 = MySQLConnect.getConnection()
                                    .prepareStatement(query1);
                            preparedStatement1.setInt(1, resultSet.getInt("ID_Intervento"));
                            preparedStatement1.setInt(2, 1);
                            preparedStatement1.setInt(3, resultSet.getInt("Codice_Identificativo"));
                            preparedStatement1.executeUpdate();
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Operazione accettata ed eseguita con successo!");
                            alert.setHeaderText("Operazione accettata ed eseguita con successo!");
                            alert.showAndWait();
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                        // aggiungi possibilita di lasciare una valutazione al veterinario
                        Alert alert2 = new Alert(Alert.AlertType.INFORMATION,
                                "Vuoi lasciare una valutazione al questo veterinario?",
                                ButtonType.YES, ButtonType.NO);
                        alert2.setTitle("VALUTAZIONE");
                        alert2.setHeaderText("VALUTAZIONE INTERVENTO");
                        Optional<ButtonType> risultato = alert2.showAndWait();

                        if (risultato.get() == ButtonType.YES) {
                            TextInputDialog dialog2 = new TextInputDialog();
                            dialog2.setTitle("Valutazione");
                            dialog2.setHeaderText("Inserisci una valutazione da 0 a 9");
                            dialog2.setContentText("Valutazione:");
                            dialog2.showAndWait().ifPresent(valutazione -> {
                                if (valutazione == null || valutazione.isEmpty() || !valutazione.matches("[0-9]")) {
                                    Alert alert3 = new Alert(Alert.AlertType.ERROR);
                                    alert3.setTitle("Valutazione non valida");
                                    alert3.setHeaderText("Valutazione non valida");
                                    alert3.showAndWait();
                                    return;
                                } else {
                                    try {
                                        String query3 = "INSERT INTO valutazione_v VALUES(?,?,?)";
                                        PreparedStatement preparedStatement3 = MySQLConnect.getConnection()
                                                .prepareStatement(query3);
                                        preparedStatement3.setString(1, ID_Intervento);
                                        preparedStatement3.setString(2, Email_Vet);
                                        preparedStatement3.setInt(3, Integer.parseInt(valutazione));
                                        preparedStatement3.executeUpdate();
                                        Alert alert3 = new Alert(Alert.AlertType.INFORMATION);
                                        alert3.setTitle("Valutazione inserita con successo");
                                        alert3.setHeaderText("Valutazione inserita con successo");
                                        alert3.showAndWait();
                                    } catch (Exception e1) {
                                        e1.printStackTrace();
                                    }
                                }
                            });
                        }
                    } else if (result.get() == ButtonType.NO) {
                        try {
                            String query1 = "INSERT INTO Accettazione_I VALUES(?,?,?)";
                            PreparedStatement preparedStatement1 = MySQLConnect.getConnection()
                                    .prepareStatement(query1);
                            preparedStatement1.setInt(1, resultSet.getInt("ID_Intervento"));
                            preparedStatement1.setInt(2, 0);
                            preparedStatement1.setInt(3, resultSet.getInt("Codice_Identificativo"));
                            preparedStatement1.executeUpdate();
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Operation Refused");
                            alert.setHeaderText("Operation Refused");
                            alert.showAndWait();
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }
                } else {
                    dialog.setContentText(email + " has no operations to accept");
                    dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
                    dialog.showAndWait();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }
}
