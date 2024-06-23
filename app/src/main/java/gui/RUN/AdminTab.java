package gui.RUN;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import database.MySQLConnect;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AdminTab {

    private final Stage primaryStage;

    public AdminTab(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void show() throws SQLException {
        // Create a VBox layout
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(20);

        //crea un bottone per visualizzare tutti gli utenti. devono essere visualizzati in una finestra a parte
        Button showUsersButton = new Button("Visualizza tutti gli utenti");
        showUsersButton.setOnAction(e -> {
            try {
                Statement stmt = MySQLConnect.getConnection().createStatement();
                String query = "SELECT * FROM utente";
                ResultSet rs = stmt.executeQuery(query);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(null);
                alert.setHeaderText("Tabella utente");
                String content = "";
                while (rs.next()) {
                    content += rs.getString("Nome") + "\t" + rs.getString("Cognome") + "\t" + rs.getString("Email") + "\n";
                }
                alert.setContentText(content);
                alert.showAndWait();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });

        root.getChildren().add(showUsersButton);

        // Create a scene and set it on the stage
        Scene scene = new Scene(root, 400, 300);
        primaryStage.setTitle("Animaletti Coccolosi Propritario");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}