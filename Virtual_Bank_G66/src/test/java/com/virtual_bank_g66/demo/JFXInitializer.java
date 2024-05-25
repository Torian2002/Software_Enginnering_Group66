package com.virtual_bank_g66.demo;

import javafx.application.Application;
import javafx.stage.Stage;

public class JFXInitializer extends Application {
    @Override
    public void start(Stage primaryStage) {
        // This method is needed to initialize JavaFX toolkit
    }

    public static void initialize() {
        new Thread(() -> Application.launch(JFXInitializer.class)).start();
        try {
            Thread.sleep(500); // Give JavaFX time to initialize
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
