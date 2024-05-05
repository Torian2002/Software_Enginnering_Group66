package com.virtual_bank_g66.demo;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;

public class Utils {

    public static final String CSV_FILE_PATH_userInfo = "src/main/resources/com/virtual_bank_g66/demo/csv/userInfo.csv";
    public static final String CSV_FILE_PATH_moneyInfo = "src/main/resources/com/virtual_bank_g66/demo/csv/moneyInfo.csv";
    public static final String CSV_FILE_PATH_transactionRecord = "src/main/resources/com/virtual_bank_g66/demo/csv/transactionRecord.csv";
    public static final String CSV_FILE_PATH_tasks = "src/main/resources/com/virtual_bank_g66/demo/csv/tasks.csv";
    public static final float Initial_Account_Value = 0;

    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    public static void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void showPage(String target, Button btn)
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