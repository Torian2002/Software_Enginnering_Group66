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


/**
 * Controller class for handling account creation in the Virtual Bank application.
 *
 * @version 5.0 May 25th, 2024
 * @author Jiabo Tong
 * @author Ruoqi Liu
 */

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

    /**
     * Handles the event when the return button is clicked.
     * This method returns the user to the login screen.
     *
     * @param event The action event triggered by clicking the return button.
     */

    @FXML
    private void returnToLoginScreen(ActionEvent event) {
        Utils.showPage("Login.fxml", returnButton);
    }

    /**
     * Handles the event when the create account button is clicked.
     * This method validates the input, checks the password format, and attempts to create a new account.
     *
     * @param event The action event triggered by clicking the create account button.
     */

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

    /**
     * Validates the user input from the form fields.
     * This method checks if all fields are filled and if the password fields match.
     *
     * @return true if the input is valid, false otherwise.
     */

    private boolean validateInput() {
        if (nameField.getText().isEmpty() || emailField.getText().isEmpty() ||
                roleComboBox.getSelectionModel().isEmpty() || passwordField.getText().isEmpty() ||
                !passwordField.getText().equals(confirmPasswordField.getText())) {
            Utils.showAlert("Validation Error", "Please fill all fields and make sure the passwords match.", Alert.AlertType.ERROR);
            return false;
        }
        return true;
    }

    /**
     * Navigates the user to the login screen.
     * This method is called after a successful account creation.
     */

    @FXML
    private void goToLoginScreen() {
        Utils.showPage("Login.fxml", createAccountButton);
    }
}
