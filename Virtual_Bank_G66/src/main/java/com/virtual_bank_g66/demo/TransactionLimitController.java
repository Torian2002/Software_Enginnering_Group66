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

/**
 * The TransactionLimitController class manages the setting and updating of transaction limits
 * for a child account by a parent. It handles password verification and updates the transaction
 * limit stored in a CSV file.
 *
 * @version 5.0 May 25th, 2024
 * @author Jiabo Tong
 * @author Jiameng Chen
 */
public class TransactionLimitController {

    @FXML
    private TextField limit;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField Currentlimit;
    @FXML
    private Button btnBack;

    Utils Utils = new Utils();
    FileUtil FileUtil = new FileUtil();

    /**
     * Initializes the controller by checking if a child account is associated and
     * retrieves the current transaction limit.
     */
    @FXML
    private void initialize() {
        UserInfoBean userInfo= UserInfoBean.getInstance();
        if (userInfo.getAssociated_ID().equals(" ") || userInfo.getAssociated_child().equals(" ")) {
            Utils.showAlert("Warning", "You must first associate a child account.", Alert.AlertType.WARNING);
        }
        FileUtil.getLimit(userInfo.getAssociated_ID(), Currentlimit);
    }

    /**
     * Handles the confirmation of a new transaction limit. Checks the validity of the input,
     * verifies the password, and updates the transaction limit in the CSV file.
     */
    @FXML
    private void onConfirmClicked() {
        String enteredPassword = passwordField.getText();
        UserInfoBean userInfoBean = UserInfoBean.getInstance();


        if (limit.getText().isEmpty()) {
            Utils.showAlert("Error", "Invalid input for new limit.", Alert.AlertType.ERROR);
            passwordField.clear();
            return;
        }

        if (!enteredPassword.equals(userInfoBean.getPassword())) {
            Utils.showAlert("Error", "Incorrect password.", Alert.AlertType.ERROR);
            passwordField.clear();
            return;
        }

        try {
            List<String[]> lines = Files.lines(Paths.get(Utils.CSV_FILE_PATH_moneyInfo))
                    .map(line -> line.split(","))
                    .collect(Collectors.toList());

            for (String[] line : lines) {
                if (line[0].equals(userInfoBean.getAssociated_ID())) {
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

    /**
     * Handles the back button click event to navigate back to the parent's main page.
     */
    @FXML
    private void onBackClicked() {
        Utils.showPage("Parent_MainPage.fxml", btnBack);
    }

}
