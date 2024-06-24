package gui.RUN.COMMONS;

import java.sql.ResultSet;
import java.sql.Statement;

import database.MySQLConnect;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;

public class ShowMessagesButton extends Button {

    public ShowMessagesButton(final String email) {
        this.setText("Visualizza i messaggi");
        this.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Visualizza i messaggi");
            dialog.setHeaderText("Inserisci l'ID del gruppo");
            dialog.setContentText("ID:");
            dialog.showAndWait().ifPresent(ID_Gruppo -> {
                if (isValid(ID_Gruppo, email)) {
                    try {
                        Statement stmt = MySQLConnect.getConnection().createStatement();
                        String query = "SELECT * FROM messaggio WHERE ID_Gruppo = '" + ID_Gruppo + "'";
                        ResultSet rs = stmt.executeQuery(query);
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle(null);
                        alert.setHeaderText("Messaggi");
                        String content = "";
                        content += "ID" + "\t" + "Email" + "\t" + "Contenuto" + "\n";
                        while (rs.next()) {
                            content += rs.getString("ID_Messaggio") + "\t";
                            content += rs.getString("Email") + "\t";
                            content += rs.getString("Contenuto") + "\n";
                        }

                        TextArea area = new TextArea(content);
                        area.setWrapText(true);
                        area.setEditable(false);
                        alert.getDialogPane().setContent(area);
                        alert.setResizable(true);
                        alert.showAndWait();

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
            ;
        });
    }

    private boolean isValid(String iD_Gruppo, String email) {
        // check if the gropu exists
        boolean flag = false;
        String query = "SELECT * FROM gruppo WHERE ID_Gruppo = '" + iD_Gruppo + "'";
        try {
            Statement stmt = MySQLConnect.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                // check if the user is in the group
                String query2 = "SELECT * FROM partecipazione WHERE ID_Gruppo = '" + iD_Gruppo + "' AND email = '"
                        + email
                        + "'";
                Statement stmt2 = MySQLConnect.getConnection().createStatement();
                ResultSet rs2 = stmt2.executeQuery(query2);
                if (rs2.next()) {
                    flag = true;
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(null);
                alert.setHeaderText("Errore");
                alert.setContentText("Il gruppo non esiste");
                alert.setResizable(true);
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
}
