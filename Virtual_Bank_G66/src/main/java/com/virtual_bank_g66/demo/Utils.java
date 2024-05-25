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

public class Utils {

    public   String CSV_FILE_PATH_userInfo = "src/main/resources/com/virtual_bank_g66/demo/csv/userInfo.csv";
    public   String CSV_FILE_PATH_moneyInfo = "src/main/resources/com/virtual_bank_g66/demo/csv/moneyInfo.csv";
    public   String CSV_FILE_PATH_transactionRecord = "src/main/resources/com/virtual_bank_g66/demo/csv/transactionRecord.csv";
    public   String CSV_FILE_PATH_tasks = "src/main/resources/com/virtual_bank_g66/demo/csv/tasks.csv";
    public   float Initial_Account_Value = 0;
    public   int Initial_Saving_Period = 60;
    public   float Current_Interest_Rate = 0.025F;
    public   float Saving_Interest_Rate = 0.05F;

    public String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    public  void showAlert(String title, String content, Alert.AlertType type) {
        Platform.runLater(() -> {
            Alert alert = new Alert(type);
            alert.setTitle(title);
            alert.setContentText(content);
            alert.showAndWait();
        });
    }

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