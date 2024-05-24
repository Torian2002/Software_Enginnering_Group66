package com.virtual_bank_g66.demo;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.*;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class EditInfoController {
    @FXML
    private TextField editNameField;

    @FXML
    private PasswordField ConfirmPasswordField;

    @FXML
    private PasswordField editPasswordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private TextField editMailField;

    @FXML
    private Button btnBack;

    @FXML
    public void initialize() {
        loadCurrentUserInfo();
    }

    private void loadCurrentUserInfo() {
        UserInfoBean userInfo = UserInfoBean.getInstance();
        editMailField.setText(userInfo.getMail()); // Set the email field with the current user's email
        editNameField.setText(userInfo.getUserName()); // Set the name field with the current user's email
    }

    @FXML
    private void onSaveChangesClicked() {
        UserInfoBean userInfo = UserInfoBean.getInstance();
        String currentPassword = userInfo.getPassword(); // Assuming the password is stored here for the example

        if (!ConfirmPasswordField.getText().equals(currentPassword)) {
            Utils.showAlert("Error", "Original password is incorrect.", Alert.AlertType.ERROR);
            ConfirmPasswordField.clear();
            editPasswordField.clear();
            confirmPasswordField.clear();
            return;
        }

        if (!editPasswordField.getText().isEmpty() && !editPasswordField.getText().equals(confirmPasswordField.getText())) {
            Utils.showAlert("Error", "New passwords do not match.", Alert.AlertType.ERROR);
            editPasswordField.clear();
            confirmPasswordField.clear();
            return;
        }

        try {
            updateUserInfo(userInfo.getID(), editNameField.getText(), editPasswordField.getText(), editMailField.getText());
            userInfo.updateSessionDetails(editNameField.getText(), editPasswordField.getText(), editMailField.getText());
            Utils.showAlert("Success", "Information updated successfully.", Alert.AlertType.INFORMATION);

        } catch (IOException e) {
            e.printStackTrace();
            Utils.showAlert("Error", "Failed to update information.", Alert.AlertType.ERROR);
        }
    }

    private void updateUserInfo(String userId, String newName, String newPassword, String newEmail) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(Utils.CSV_FILE_PATH_userInfo));
        List<String> updatedLines = lines.stream().map(line -> {
            String[] values = line.split(",");
            if (!values[0].equals("ID")) {
                if (values[0].equals(userId)) {
                    if (!newName.isEmpty())
                        values[2] = newName;
                    if (!newPassword.isEmpty())
                        values[3] = newPassword;
                    if (!newEmail.isEmpty())
                        values[4] = newEmail;
                }
            }
            return String.join(",", values);
        }).collect(Collectors.toList());
        Files.write(Paths.get(Utils.CSV_FILE_PATH_userInfo), updatedLines);
    }


    @FXML
    public void onBackClicked() {
        UserInfoBean userInfo = UserInfoBean.getInstance();
        String role = userInfo.getRole();
        if (role.equals("Child")){
            Utils.showPage("Child_InfoPage.fxml", btnBack);
        } else {
            Utils.showPage("Parent_InfoPage.fxml", btnBack);
        }
    }
}
