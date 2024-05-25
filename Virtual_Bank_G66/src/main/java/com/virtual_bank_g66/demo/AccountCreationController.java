package com.virtual_bank_g66.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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

    Utils Utils = new Utils();
    FileUtil FileUtil = new FileUtil();

    private AccountService accountService = new AccountService(Utils.CSV_FILE_PATH_userInfo,
            Utils.CSV_FILE_PATH_moneyInfo,
            Utils);

    @FXML
    private void returnToLoginScreen(ActionEvent event) {
        Utils.showPage("Login.fxml", returnButton);
    }

    @FXML
    private void handleCreateAccount(ActionEvent event) {
        if (validateInput() && AccountService.isValid(passwordField.getText())) {
            boolean success = accountService.createAccount(
                    roleComboBox.getValue(),
                    nameField.getText(),
                    passwordField.getText(),
                    emailField.getText());
            if (success) {
                goToLoginScreen();
            }
        } else {
            Utils.showAlert("Password Format Error", "Please reset your password as required.", Alert.AlertType.ERROR);
        }
    }

    private boolean validateInput() {
        if (nameField.getText().isEmpty() || emailField.getText().isEmpty() ||
                roleComboBox.getSelectionModel().isEmpty() || passwordField.getText().isEmpty() ||
                !passwordField.getText().equals(confirmPasswordField.getText())) {
            Utils.showAlert("Validation Error", "Please fill all fields and make sure the passwords match.", Alert.AlertType.ERROR);
            return false;
        }
        return true;
    }

    @FXML
    private void goToLoginScreen() {
        Utils.showPage("Login.fxml", createAccountButton);
    }
}
