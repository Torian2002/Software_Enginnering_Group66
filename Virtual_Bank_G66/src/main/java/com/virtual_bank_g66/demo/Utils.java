package com.virtual_bank_g66.demo;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * The Utils class provides various utility methods and constants used across the application.
 * This includes file paths, default values, formatting methods, and common UI actions.
 *
 * @version 5.0 May 25th, 2024
 * @author Jiabo Tong
 */
public class Utils {

    /** Path to the user information CSV file. */
    public   String CSV_FILE_PATH_userInfo = "src/main/resources/com/virtual_bank_g66/demo/csv/userInfo.csv";
    /** Path to the money information CSV file. */
    public   String CSV_FILE_PATH_moneyInfo = "src/main/resources/com/virtual_bank_g66/demo/csv/moneyInfo.csv";
    /** Path to the transaction record CSV file. */
    public   String CSV_FILE_PATH_transactionRecord = "src/main/resources/com/virtual_bank_g66/demo/csv/transactionRecord.csv";
    /** Path to the tasks CSV file. */
    public   String CSV_FILE_PATH_tasks = "src/main/resources/com/virtual_bank_g66/demo/csv/tasks.csv";
    /** Initial account value, default is 0. */
    public   float Initial_Account_Value = 0;
    /** Initial saving period in days, default is 60. */
    public   int Initial_Saving_Period = 60;
    /** Current interest rate, default is 2.5%. */
    public   float Current_Interest_Rate = 0.025F;
    /** Saving interest rate, default is 5%. */
    public   float Saving_Interest_Rate = 0.05F;

    /**
     * Formats a LocalDateTime object to a string in the "yyyy-MM-dd HH:mm" pattern.
     *
     * @param dateTime the LocalDateTime object to format
     * @return a formatted date-time string
     */
    public String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    /**
     * Displays an alert dialog with the given title, content, and alert type.
     *
     * @param title the title of the alert
     * @param content the content of the alert
     * @param type the type of the alert (e.g., INFORMATION, WARNING, ERROR)
     */
    public  void showAlert(String title, String content, Alert.AlertType type) {
        Platform.runLater(() -> {
            Alert alert = new Alert(type);
            alert.setTitle(title);
            alert.setContentText(content);
            alert.showAndWait();
        });
    }

    /**
     * Navigates to a different page specified by the FXML file path and sets it as the current scene.
     *
     * @param target the FXML file path of the target page
     * @param btn the button that triggers the page change
     */
    public void showPage(String target, Button btn)
    {
        try {
            FXMLLoader loader = new FXMLLoader(Utils.class.getResource(target));
            Parent Page = loader.load();
            Scene mainScene = new Scene(Page);
            Stage stage = (Stage) btn.getScene().getWindow();
            stage.setScene(mainScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}