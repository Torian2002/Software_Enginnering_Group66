package com.virtual_bank_g66.demo;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import java.io.*;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionLimitController {

    @FXML
    private TextField limit;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField Currentlimit;
    @FXML
    private Button btnBack;


    @FXML
    private void initialize() {
        UserSessionBean userSession= UserSessionBean.getInstance();
        if (userSession.getAssociated_ID().equals(" ") || userSession.getAssociated_child().equals(" ")) {
            Utils.showAlert("Warning", "You must first associate a child account.", Alert.AlertType.WARNING);
        }
        FileUtil.getLimit(userSession.getAssociated_ID(), Currentlimit);
    }

    @FXML
    private void onConfirmClicked() {
        String enteredPassword = passwordField.getText();
        UserSessionBean userSessionBean = UserSessionBean.getInstance();


        if (limit.getText().isEmpty()) {
            Utils.showAlert("Error", "Invalid input for new limit.", Alert.AlertType.ERROR);
            passwordField.clear();
            return;
        }

        if (!enteredPassword.equals(userSessionBean.getPassword())) {
            Utils.showAlert("Error", "Incorrect password.", Alert.AlertType.ERROR);
            passwordField.clear();
            return;
        }

        try {
            List<String[]> lines = Files.lines(Paths.get(Utils.CSV_FILE_PATH_moneyInfo))
                    .map(line -> line.split(","))
                    .collect(Collectors.toList());

            for (String[] line : lines) {
                if (line[0].equals(userSessionBean.getAssociated_ID())) {
                    line[3] = limit.getText(); // Assuming limit is at index 2
                    break;
                }
            }

            try (BufferedWriter bw = Files.newBufferedWriter(Paths.get(Utils.CSV_FILE_PATH_moneyInfo))) {
                for (String[] line : lines) {
                    bw.write(String.join(",", line));
                    bw.newLine();
                }
            }

            Utils.showAlert("Success", "Transaction limit updated successfully.", Alert.AlertType.INFORMATION);
            Currentlimit.setText(limit.getText());
            limit.clear();
            passwordField.clear();
        } catch (IOException e) {
            Utils.showAlert("File Error", "Failed to read or write money information file.", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    private void onBackClicked() {
        Utils.showPage("Parent_MainPage.fxml", btnBack);
    }

}
