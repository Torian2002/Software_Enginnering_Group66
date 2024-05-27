package com.virtual_bank_g66.demo;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * JFXInitializer class for initializing JavaFX environment.
 * <p>
 * This class extends the JavaFX Application class to provide a mechanism to initialize
 * the JavaFX toolkit in non-GUI test environments.
 * </p>
 * 
 * @version 1.0 April 1ath,2024
 * @author Jiabo Tong
 */

public class JFXInitializer extends Application {
    
    /**
     * The start method is called after the JavaFX application has been initialized.
     * 
     * @param primaryStage the primary stage for this application, onto which the application scene can be set.
     *                     Applications may create other stages, if needed, but they will not be primary stages.
     */
    @Override
    public void start(Stage primaryStage) {
    }
    
    /**
     * Initializes the JavaFX environment.
     * <p>
     * This method launches the JavaFX application in a new thread to initialize the JavaFX toolkit.
     * It ensures that the JavaFX platform is ready for use in testing environments where GUI is not required.
     * </p>
     */
    public static void initialize() {
        new Thread(() -> Application.launch(JFXInitializer.class)).start();
        try {
            Thread.sleep(500); 
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
