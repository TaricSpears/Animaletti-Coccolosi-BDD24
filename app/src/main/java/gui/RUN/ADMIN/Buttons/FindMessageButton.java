package gui.RUN.ADMIN.Buttons;

import java.sql.ResultSet;
import java.sql.Statement;

import database.MySQLConnect;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;

public class FindMessageButton extends Button {

    public FindMessageButton(final String email) {
        this.setText("Visualizza i messaggi");
        this.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Visualizza i messaggi");
            dialog.setHeaderText("Inserisci l'ID del messaggio");
            dialog.setContentText("ID:");
            dialog.showAndWait().ifPresent(ID_Messaggio -> {
                if (isValid(ID_Messaggio)) {
                    try {
                        Statement stmt = MySQLConnect.getConnection().createStatement();
                        String query = "SELECT * FROM messaggio WHERE ID_Messaggio = '" + ID_Messaggio + "'";
                        ResultSet rs = stmt.executeQuery(query);
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle(null);
                        alert.setHeaderText("Messaggio");
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

    private boolean isValid(String ID_Messaggio) {
        // check if the gropu exists
        boolean flag = false;
        String query = "SELECT * FROM messaggio WHERE ID_Messaggio = '" + ID_Messaggio + "'";
        try {
            Statement stmt = MySQLConnect.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                flag = true;
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(null);
                alert.setHeaderText("Errore");
                alert.setContentText("Il messaggio non esiste");
                alert.setResizable(true);
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
}
