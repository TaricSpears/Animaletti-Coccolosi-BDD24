package gui.RUN.COMMONS;

import java.sql.ResultSet;
import java.sql.Statement;

import database.MySQLConnect;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

public class SelectFoodsButton extends Button {
    public SelectFoodsButton() {
        this.setText("Visualizza cibi");
        this.setOnAction(e -> {
            try {
                Statement stmt = MySQLConnect.getConnection().createStatement();
                String query = "SELECT * FROM cibo";
                ResultSet rs = stmt.executeQuery(query);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(null);
                alert.setHeaderText("Tabella cibi");
                String content = "";
                while (rs.next()) {
                    content += rs.getString("Nome") + "\n";
                }
                alert.setContentText(content);
                alert.setResizable(true);
                alert.showAndWait();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }
}
