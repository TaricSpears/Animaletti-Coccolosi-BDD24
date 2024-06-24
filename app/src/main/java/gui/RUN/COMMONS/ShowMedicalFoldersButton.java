package gui.RUN.COMMONS;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import database.MySQLConnect;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;

public class ShowMedicalFoldersButton extends Button {
    public ShowMedicalFoldersButton(final String email, final int role) {
        this.setText("Visualizza cartella clinica di un tuo animale");
        this.setOnAction(click -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Inserisci codice identificativo");
            dialog.setHeaderText("Inserisci il codice identificativo del tuo animale");
            dialog.setContentText("Codice identificativo:");
            dialog.showAndWait().ifPresent(codiceIdentificativo -> {
                if (isValid(codiceIdentificativo, email, role)) {
                    // seleziona la data di creazione della cartella clinica
                    String query1 = "SELECT * FROM cartella_clinica WHERE Codice_Identificativo = ?";
                    String result = "Data_di_Creazione\n";
                    try {
                        Connection connection = MySQLConnect.getConnection();
                        PreparedStatement preparedStatement = connection.prepareStatement(query1);
                        preparedStatement.setString(1, codiceIdentificativo);
                        ResultSet resultSet = preparedStatement.executeQuery();
                        resultSet.next();
                        result += resultSet.getString("Data_di_Creazione") + "\n\n";
                    } catch (Exception e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Errore");
                        alert.setHeaderText("Errore");
                        alert.setContentText("Errore durante la visualizzazione della cartella clinica");
                        alert.showAndWait();
                        e.printStackTrace();
                    }
                    // seleziona le condizioni cliniche assegnate alla cartella clinica che hai
                    // trovato
                    String query2 = "SELECT a.Nome FROM assegnazione a WHERE a.Codice_Identificativo = ?";
                    try {
                        Connection connection = MySQLConnect.getConnection();
                        PreparedStatement preparedStatement = connection.prepareStatement(query2);
                        preparedStatement.setString(1, codiceIdentificativo);
                        ResultSet resultSet = preparedStatement.executeQuery();
                        result += "Condizioni cliniche\n";
                        while (resultSet.next()) {
                            result += resultSet.getString("Nome") + "\n";
                        }
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Cartella clinica");
                        alert.setHeaderText("Cartella clinica");
                        alert.setContentText(result);
                        alert.setResizable(true);
                        alert.showAndWait();
                    } catch (Exception e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Errore");
                        alert.setHeaderText("Errore");
                        alert.setContentText("Errore durante la visualizzazione della cartella clinica");
                        alert.showAndWait();
                        e.printStackTrace();
                    }
                }
            });
        });
    }

    private boolean isValid(final String codiceIdentificativo, final String email, final int role) {
        boolean flag = false;
        if (codiceIdentificativo.length() > 0 && codiceIdentificativo.length() <= 5) {
            if (role == Roles.VETERINARIO.value) {
                flag = true;
            } else {
                String query = "SELECT * FROM animale WHERE Codice_Identificativo = ? AND Email = ?";
                try {
                    Connection connection = MySQLConnect.getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setString(1, codiceIdentificativo);
                    preparedStatement.setString(2, email);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    if (resultSet.next()) {
                        flag = true;
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Errore");
                        alert.setHeaderText("Errore");
                        alert.setContentText("Il codice identificativo inserito non e' valido");
                        alert.showAndWait();
                    }
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Errore");
                    alert.setHeaderText("Errore");
                    alert.setContentText("Il codice identificativo inserito non e' valido");
                    alert.showAndWait();
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }
}
