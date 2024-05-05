package com.virtual_bank_g66.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.*;

public class AccountCreationController {

    @FXML
    private ComboBox<String> roleComboBox;

    @FXML
    private TextField nameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private TextField emailField;

    @FXML
    private Button createAccountButton;

    @FXML
    private Button returnButton;

    @FXML
    private void returnToLoginScreen(ActionEvent event) {
        try {
            Parent login= FXMLLoader.load(getClass().getResource("Login.fxml"));
            Scene loginScene = new Scene(login);
            Stage window = (Stage) returnButton.getScene().getWindow();
            window.setScene(loginScene);
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // Method called when the "Create Account" button is clicked
    @FXML
    private void handleCreateAccount(ActionEvent event) {
        if (validateInput())
        {
            createAccount();
            goToLoginScreen();
        }
    }

    private boolean validateInput() {
        // Verify the logic of the input, ensure that all fields are filled in, and that the password matches, etc
        if (nameField.getText().isEmpty() || emailField.getText().isEmpty() ||
                roleComboBox.getSelectionModel().isEmpty() || passwordField.getText().isEmpty() ||
                !passwordField.getText().equals(confirmPasswordField.getText())) {
            Utils.showAlert("Validation Error", "Please fill all fields and make sure the passwords match.", Alert.AlertType.ERROR);
            return false;
        }
        return true;
    }

    private void createAccount() {

        File file_userInfo = new File(Utils.CSV_FILE_PATH_userInfo);

        // If dir doesn't exist we need to build one
        File userinfoDir = file_userInfo.getParentFile();
        if (!userinfoDir.exists()) {
            if (!userinfoDir.mkdirs()) {
                Utils.showAlert("Directory Error", "Failed to create userinfo directories.", Alert.AlertType.ERROR);
                return;
            }
        }
        int accountNumber = 1; // count the id
        boolean accountExists = false;

        if (file_userInfo.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file_userInfo))) {
                String line;
                while ((line = br.readLine()) != null) {
                    accountNumber++; // Increment account number for each line
                    String[] data = line.split(",");
                    if (data[1].equals(roleComboBox.getValue()) && data[2].equals(nameField.getText())) {
                        accountExists = true;
                    }
                }
            } catch (IOException e) {
                Utils.showAlert("IO Error", "An error occurred while reading the userInfo.csv: " + e.getMessage(), Alert.AlertType.ERROR);
                e.printStackTrace();
                return;
            }
        } else {
            try {
                if (!file_userInfo.createNewFile()) {
                    Utils.showAlert("File Error", "Failed to create the userInfo.csv.", Alert.AlertType.ERROR);
                    return;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if (accountExists) {
            Utils.showAlert("Account Error", "An account with this name and role already exists.", Alert.AlertType.ERROR);
            return;
        }

        String csvData_userInfo = String.format("%d,%s,%s,%s,%s,%s,%s,%n",
                accountNumber,
                roleComboBox.getValue(),
                nameField.getText(),
                passwordField.getText(),
                emailField.getText(),
                " ",
                " ");


        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file_userInfo, true))) {
            bw.write(csvData_userInfo);
            bw.flush();
        } catch (IOException e) {
            Utils.showAlert("IO Error", "An error occurred while writing to the moneyInfo.csv: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }

        if (roleComboBox.getValue().equals("Child")) {
            writeMoneyInfo(accountNumber);
        }

    }

    private void writeMoneyInfo(int accountNumber)
    {
        File file_moneyInfo = new File(Utils.CSV_FILE_PATH_moneyInfo);

        File moneyInfoDir = file_moneyInfo.getParentFile();
        if (!moneyInfoDir.exists()) {
            if (!moneyInfoDir.mkdirs()) {
                Utils.showAlert("Directory Error", "Failed to create moneyinfo directories.", Alert.AlertType.ERROR);
                return;
            }
        }
        if (!file_moneyInfo.exists()) {
            try {
                if (!file_moneyInfo.createNewFile()) {
                    Utils.showAlert("File Error", "Failed to create the moneyInfo.csv.", Alert.AlertType.ERROR);
                    return;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        String csvData_moneyInfo = String.format("%d,%f,%f,%f,%f,%n",
                accountNumber,
                Utils.Initial_Account_Value,
                Utils.Initial_Account_Value,
                Utils.Initial_Account_Value,
                Utils.Initial_Account_Value);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file_moneyInfo, true))) {

            bw.write(csvData_moneyInfo);
            bw.flush();
        } catch (IOException e) {
            Utils.showAlert("IO Error", "An error occurred while writing to the file: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }

        Utils.showAlert("Registration Successful", "Your account has been created successfully!",AlertType.INFORMATION);
    }

    @FXML
    private void goToLoginScreen() {
        Utils.showPage("Login.fxml", createAccountButton);
    }

}