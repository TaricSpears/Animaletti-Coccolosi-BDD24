package gui.RUN.COMMONS;

import java.sql.ResultSet;
import java.sql.Statement;

import database.MySQLConnect;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;

public class PetRatingButton extends Button {
    public PetRatingButton(final String Email) {
        this.setText("Pet Rating");
        this.setOnAction(e -> {
            String query1 = "SELECT * FROM animale a WHERE a.Codice_Identificativo NOT IN ( SELECT ani.Codice_Identificativo FROM animale ani JOIN valutazione_a v ON ani.Codice_Identificativo = v.Codice_Identificativo WHERE v.Email = '"
                    + Email + "') ORDER BY RAND() LIMIT 1";
            try {
                Statement stmt = MySQLConnect.getConnection().createStatement();
                ResultSet rs = stmt.executeQuery(query1);
                if (rs.next()) {
                    String codice = rs.getString("Codice_Identificativo");
                    String nome = rs.getString("Nome");
                    String ddn = rs.getString("Data_di_Nascita");
                    String peso = rs.getString("Peso");
                    String vmedia = rs.getString("Valutazione_media");
                    String nomeSpecie = rs.getString("APP_Nome");
                    String output = "Nome\t\tCodicie Identificativo\t\tData di Nascita\t\tPeso\t\tValutazione Media\t\tNome Specie\n";
                    output += nome + "\t\t" + codice + "\t\t" + ddn + "\t\t" + peso + "\t\t" + vmedia + "\t\t"
                            + nomeSpecie;
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle(null);
                    alert.setHeaderText("Pet Rating");
                    alert.setContentText(output);
                    alert.setResizable(true);
                    alert.showAndWait();
                    TextInputDialog dialog = new TextInputDialog();
                    dialog.setTitle("Pet Rating");
                    dialog.setHeaderText("Inserisci la tua valutazione");
                    dialog.setContentText("Valutazione:");
                    dialog.showAndWait().ifPresent(valutazione -> {
                        if (valutazione.matches("[0-9]+")) {
                            String query2 = "INSERT INTO valutazione_a (Codice_Identificativo, Email, Voto) VALUES ('"
                                    + codice + "', '" + Email + "', '" + valutazione + "')";
                            try {
                                stmt.executeUpdate(query2);
                                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                                alert2.setTitle(null);
                                alert2.setHeaderText("Successo");
                                alert2.setContentText("Valutazione inserita con successo");
                                alert2.showAndWait();
                                // ricalcola valutazione media dell animale
                                String query3 = "SELECT AVG(Voto) AS Valutazione_media FROM valutazione_a WHERE Codice_Identificativo = '"
                                        + codice + "'";
                                ResultSet rs2 = stmt.executeQuery(query3);
                                rs2.next();
                                String valutazione_media = rs2.getString("Valutazione_media");
                                String query4 = "UPDATE animale SET Valutazione_media = '" + valutazione_media
                                        + "' WHERE Codice_Identificativo = '" + codice + "'";
                                stmt.executeUpdate(query4);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        } else {
                            Alert alert2 = new Alert(Alert.AlertType.ERROR);
                            alert2.setTitle(null);
                            alert2.setHeaderText("Errore");
                            alert2.setContentText("Inserire un valore numerico");
                            alert2.showAndWait();
                        }
                    });
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle(null);
                    alert.setHeaderText("Pet Rating");
                    alert.setContentText("Non ci sono animali da valutare");
                    alert.showAndWait();
                }
            } catch (Exception xe) {
                xe.printStackTrace();
            }
        });
    }
}
