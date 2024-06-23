package gui.RUN.OWNER.Buttons;

import javafx.scene.control.Button;

public class BookAVisitButton extends Button{
    public BookAVisitButton(String email){
        // Set the button text
        this.setText("Prenota una visita");
        
        this.setOnAction(e -> {
            //open a new window to book a visit where the user can select the date and time and the animal
            
        });
    }
}
