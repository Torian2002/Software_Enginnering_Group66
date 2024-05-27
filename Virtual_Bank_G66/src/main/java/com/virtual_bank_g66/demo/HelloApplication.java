package com.virtual_bank_g66.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The HelloApplication class serves as the entry point for the JavaFX application.
 * It loads the initial FXML layout and sets up the primary stage.
 *
 * @version 1.0 April 10th, 2024 - create HelloApplication as default startup class
 * @author Jiabo Tong
 */
public class HelloApplication extends Application {

    /**
     * The main entry point for all JavaFX applications. The start method is called
     * after the init method has returned, and after the system is ready for the
     * application to begin running.
     *
     * @param stage the primary stage for this application, onto which
     *              the application scene can be set.
     * @throws IOException if the FXML file cannot be loaded.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 600);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The main method is ignored in correctly deployed JavaFX application.
     * main() serves as fallback in case the application is launched directly
     * from the command line.
     *
     * @param args the command line arguments.
     */
    public static void main(String[] args) {
        launch();
    }
}