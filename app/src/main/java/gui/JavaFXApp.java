package gui;

import gui.START.InitialTab;
import javafx.application.Application;
import javafx.stage.Stage;

public class JavaFXApp extends Application{

    public static void run(final String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        InitialTab initialTab = new InitialTab(primaryStage);
        initialTab.show();
    }
    
}
