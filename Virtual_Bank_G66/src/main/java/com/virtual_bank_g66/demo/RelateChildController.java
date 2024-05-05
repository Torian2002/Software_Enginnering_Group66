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

public class RelateChildController {

    @FXML
    private TextField childName;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField currentChild;
    @FXML
    private Button btnBack;



    @FXML
    private void initialize() {
        UserSessionBean userSession = UserSessionBean.getInstance();
        if (userSession.getAssociated_child().equals(" ")) {
            currentChild.setText("You don't have any associated goal");
        } else{
            currentChild.setText(userSession.getAssociated_child());
        }

    }

    @FXML
    private void onConfirmClicked() {
        String Associated_child = childName.getText().trim();
        String password = passwordField.getText().trim();

        UserSessionBean usersession = UserSessionBean.getInstance();

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

            if (!password.equals(UserSessionBean.getInstance().getPassword())) {
                Utils.showAlert("Error", "Password is incorrect.", Alert.AlertType.ERROR);
                passwordField.clear();
                return;
            }

            usersession.setAssociated_child(Associated_child);
            usersession.setAssociated_ID(Associated_ID);
            updateUserInfo(users, usersession);

        } catch (IOException e) {
            e.printStackTrace();
            Utils.showAlert("Error", "An error occurred while processing the file.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void onBackClicked() {
        Utils.showPage("Parent_MainPage.fxml", btnBack);
    }

    private void updateUserInfo(List<String[]> users, UserSessionBean usersession) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(Utils.CSV_FILE_PATH_userInfo))) {
            for (String[] user : users) {
                if (user[0].equals(usersession.getID())) {
                    user[5] = usersession.getAssociated_child();
                    user[6] = usersession.getAssociated_ID(); // Assuming associate child ID at index 3
                }
                writer.write(String.join(",", user));
                writer.newLine();
            }
            passwordField.clear();
            childName.clear();
            currentChild.setText(usersession.getAssociated_child());
            Utils.showAlert("Congratulation", "Relate to your child successfully", Alert.AlertType.INFORMATION);
        }
    }
}
