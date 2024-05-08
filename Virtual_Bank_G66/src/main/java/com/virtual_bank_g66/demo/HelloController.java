package com.virtual_bank_g66.demo;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class HelloController {

    @FXML
    private ComboBox<String> roleComboBox;

    @FXML
    private TextField nameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button createAccountButton;

    @FXML
    void switchToAccountCreation(ActionEvent event) {
        try {
            Parent accountCreation = FXMLLoader.load(getClass().getResource("AccountCreation.fxml"));
            Scene accountCreationScene = new Scene(accountCreation);
            Stage window = (Stage) createAccountButton.getScene().getWindow();
            window.setScene(accountCreationScene);
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void login() {
        String role = roleComboBox.getValue();
        String name = nameField.getText();
        String password = passwordField.getText();

        if (role != null && name != null && password != null) {
            if (checkCredentials(role, name, password)) {
                // Create and save user sessions and communicate across multiple pages in the end of checkCredentials
                switchToMainPage(role);
            } else {
                // Show error alert
                Utils.showAlert("Login Failed", "Incorrect role, name, or password. Please try again.", Alert.AlertType.ERROR);
                // Optionally, clear the fields or do other necessary UI updates
                nameField.clear();
                passwordField.clear();
            }
        } else {
            // Show error alert
            Utils.showAlert("Login Failed", "At least one of the required fields has not been filled. Please try again.", Alert.AlertType.ERROR);
            // Optionally, clear the fields or do other necessary UI updates
            nameField.clear();
            passwordField.clear();
        }
    }

    // Placeholder method to switch to main page
    private void switchToMainPage(String role) {
        try {
            if (role.equals("Parent")) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Parent_MainPage.fxml"));
                Parent mainPage = loader.load();
                Scene mainScene = new Scene(mainPage);

                Stage stage = (Stage) nameField.getScene().getWindow(); // Assuming nameField is part of the login form
                stage.setScene(mainScene);
                stage.show();
            } else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Child_MainPage.fxml"));
                Parent mainPage = loader.load();
                Scene mainScene = new Scene(mainPage);

                Stage stage = (Stage) nameField.getScene().getWindow(); // Assuming nameField is part of the login form
                stage.setScene(mainScene);
                stage.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
    }

    private boolean checkCredentials(String role, String name, String password) {
        String csvFile = Utils.CSV_FILE_PATH_userInfo;
        String line;
        String cvsSplitBy = ","; // CSV delimiter

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            while ((line = br.readLine()) != null) {
                // Use comma as separator
                String[] userInfo = line.split(cvsSplitBy);

                // Check if array has at least 5 elements (id, role, name, password, mail)
                if (userInfo.length >= 5) {
                    String fileRole = userInfo[1];
                    String fileName = userInfo[2];
                    String filePassword = userInfo[3];
                    // Assuming passwords are stored in a secure manner, e.g., hashed
                    if (fileRole.equals(role) && fileName.equals(name) && filePassword.equals(password)) {
                        // Create and save user sessions and communicate across multiple pages
                        UserSessionBean.getInstance(userInfo[2], userInfo[1], userInfo[0], userInfo[3],  userInfo[4], userInfo[5], userInfo[6]);
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exceptions or errors appropriately
        }
         return false;
    }
}
