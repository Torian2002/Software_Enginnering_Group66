package com.virtual_bank_g66.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The RelateChildController class is responsible for managing the relationship
 * between a parent user and their child in the application. It handles the UI interactions
 * and updates the user information based on the input provided.
 *
 * @version 5.0 May 25th, 2024
 * @author Jiabo Tong
 * @author Kexin Zhang
 */
public class RelateChildController {

    @FXML
    private TextField childName;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField currentChild;
    @FXML
    private Button btnBack;

    Utils Utils = new Utils();
    FileUtil FileUtil = new FileUtil();

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded. It sets the current associated child
     * if available.
     */
    @FXML
    private void initialize() {
        UserInfoBean userInfo = UserInfoBean.getInstance();
        if (userInfo.getAssociated_child().equals(" ")) {
            currentChild.setText("You don't have any associated goal");
        } else{
            currentChild.setText(userInfo.getAssociated_child());
        }

    }

    /**
     * Handles the confirm button click event. It validates the input child name
     * and password, and if correct, associates the child with the parent user.
     */
    @FXML
    private void onConfirmClicked() {
        String Associated_child = childName.getText().trim();
        String password = passwordField.getText().trim();

        UserInfoBean userInfo = UserInfoBean.getInstance();

        try {
            List<String[]> users = Files.lines(Paths.get(Utils.CSV_FILE_PATH_userInfo))
                    .map(line -> line.split(","))
                    .collect(Collectors.toList());

            boolean childExists = false;
            String Associated_ID = "";
            for (String[] user : users) {
                if (user[1].equals("Child") && user[2].equals(Associated_child)) {
                    childExists = true;
                    Associated_ID = user[0];
                    break;
                }
            }

            if (!childExists) {
                Utils.showAlert("Error", "Child name does not exist.", Alert.AlertType.ERROR);
                passwordField.clear();
                childName.clear();
                return;
            }

            if (!password.equals(UserInfoBean.getInstance().getPassword())) {
                Utils.showAlert("Error", "Password is incorrect.", Alert.AlertType.ERROR);
                passwordField.clear();
                return;
            }

            userInfo.setAssociated_child(Associated_child);
            userInfo.setAssociated_ID(Associated_ID);
            updateUserInfo(users, userInfo);

        } catch (IOException e) {
            e.printStackTrace();
            Utils.showAlert("Error", "An error occurred while processing the file.", Alert.AlertType.ERROR);
        }
    }

    /**
     * Handles the back button click event. It navigates the user back to the parent main page.
     */
    @FXML
    private void onBackClicked() {
        Utils.showPage("Parent_MainPage.fxml", btnBack);
    }

    /**
     * Updates the user information in the CSV file after successfully associating
     * a child with the parent user.
     *
     * @param users the list of user information from the CSV file
     * @param userInfo the user information bean with updated data
     * @throws IOException if an I/O error occurs while writing to the file
     */
    private void updateUserInfo(List<String[]> users, UserInfoBean userInfo) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(Utils.CSV_FILE_PATH_userInfo))) {
            for (String[] user : users) {
                if (user[0].equals(userInfo.getID())) {
                    user[5] = userInfo.getAssociated_child();
                    user[6] = userInfo.getAssociated_ID(); // Assuming associate child ID at index 3
                }
                writer.write(String.join(",", user));
                writer.newLine();
            }
            passwordField.clear();
            childName.clear();
            currentChild.setText(userInfo.getAssociated_child());
            Utils.showAlert("Congratulation", "Relate to your child successfully", Alert.AlertType.INFORMATION);
        }
    }
}
