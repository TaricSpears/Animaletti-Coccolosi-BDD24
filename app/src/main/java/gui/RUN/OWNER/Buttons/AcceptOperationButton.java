package gui.RUN.OWNER.Buttons;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import database.MySQLConnect;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class AcceptOperationButton extends Button {

    public AcceptOperationButton(String email) {
        this.setText("Accetta Operazione");
        this.setOnAction(e -> {
            Dialog<String> dialog = new Dialog<>();
            dialog.setTitle("Accept Operation");
            String query = "SELECT * FROM Intervento WHERE ID_Intervento NOT IN (SELECT ID_Intervento FROM Accettazione_I) AND Codice_Identificativo IN (SELECT Codice_Identificativo FROM animale WHERE Email = '"
                    + email + "') ORDER BY Data LIMIT 1";
            try {
                PreparedStatement preparedStatement = MySQLConnect.getConnection().prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {

                    Text text = new Text("Do you want to accept the following operation?");
                    text.setFont(new Font(20));
                    Text text2 = new Text("Date: " + resultSet.getDate("Data"));
                    Text text4 = new Text("Time: " + resultSet.getTime("Ora"));
                    Text text1 = new Text("ID: " + resultSet.getInt("ID_Intervento"));
                    Text text3 = new Text("Description: " + resultSet.getString("Descrizione"));
                    Text text5 = new Text("ID_Animal: " + resultSet.getInt("Codice_Identificativo"));
                    Text text6 = new Text("ID_Vet: " + resultSet.getString("Email"));
                    VBox vBox = new VBox(text, text1, text2, text3, text4, text5, text6);
                    vBox.setAlignment(Pos.CENTER);
                    dialog.getDialogPane().setContent(vBox);
                    ButtonType accept = new ButtonType("Accept");
                    ButtonType refuse = new ButtonType("Refuse");
                    dialog.getDialogPane().getButtonTypes().addAll(accept, refuse);
                    dialog.showAndWait().ifPresent(response -> {
                        if (response.equals("Accept")) {
                            try {
                                String query2 = "SELECT * FROM Intervento WHERE ID_Intervento IN (SELECT ID_Intervento FROM Accettazione_I) AND Data = '"
                                        + resultSet.getDate("Data") + "' AND Ora = '" + resultSet.getTime("Ora")
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
                                alert.setTitle("Operation Accepted");
                                alert.setHeaderText("Operation Accepted");
                                alert.showAndWait();
                            } catch (Exception e1) {
                                e1.printStackTrace();
                            }
                        } else if (response.equals("Refuse")) {
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
                    });
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
