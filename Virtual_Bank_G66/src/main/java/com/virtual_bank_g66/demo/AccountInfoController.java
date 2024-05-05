package com.virtual_bank_g66.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class AccountInfoController {

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnBack;

    @FXML
    private TextField roleField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField currentAccountField;

    @FXML
    private TextField savingAccountField;

    @FXML
    private TextField limitField;
    @FXML
    private TextField currentChild;

    public void initialize() {
        UserSessionBean userSessionBean = UserSessionBean.getInstance();
        loadUserData(userSessionBean);
    }

    private void loadUserData(UserSessionBean userSession) {
        nameField.setText(userSession.getUserName());
        roleField.setText(userSession.getRole());

        if ("Child".equals(userSession.getRole())) {
            HashMap<String, String> data = readMoneyInfoCsv(String.valueOf(userSession.getID()));
            updateFields(data);
        } else if ("Parent".equals(userSession.getRole())) {
            HashMap<String, String> userInfo = readUserInfoCsv(userSession.getID());
            if (userInfo != null && !userInfo.get("Associated_ID").equals(" ")) {
                userSession.setAssociated_child(userInfo.get("Associated_child"));
                userSession.setAssociated_ID(userInfo.get("Associated_ID"));
                HashMap<String, String> data = readMoneyInfoCsv(userInfo.get("Associated_ID"));
                updateFields(data);
                currentChild.setText(userSession.getAssociated_child());
            } else {
                Utils.showAlert("Alert", "You should relate to your child first.", Alert.AlertType.ERROR);
                currentChild.setText("You don't have any associated goal");
            }
        }
    }

    private HashMap<String, String> readMoneyInfoCsv(String id) {
        String line;
        HashMap<String, String> data = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(Utils.CSV_FILE_PATH_moneyInfo))) {
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values[0].equals(id)) {
                    data.put("Current", values[1]);
                    data.put("Saving", values[2]);
                    data.put("Goal", values[3]);
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    private HashMap<String, String> readUserInfoCsv(String userID) {
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(Utils.CSV_FILE_PATH_userInfo))) {
            boolean firstLine = true;
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
                String[] values = line.split(",");
                if (values[0].equals(userID)) {
                    HashMap<String, String> userInfo = new HashMap<>();
                    userInfo.put("Associated_child", values[5]);
                    userInfo.put("Associated_ID", values[6]);
                    return userInfo;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void updateFields(HashMap<String, String> data) {
        if (data != null) {
            currentAccountField.setText(data.getOrDefault("Current", "0"));
            savingAccountField.setText(data.getOrDefault("Saving", "0"));
            limitField.setText(data.getOrDefault("Goal", "0"));
        }
    }

    @FXML
    private void onEditClicked() {
        Utils.showPage("EditInfo.fxml",btnEdit);
    }

    @FXML
    private void onBackClicked() {
        if (roleField.getText().equals("Parent")) {
            Utils.showPage("Parent_MainPage.fxml",btnBack);
        } else {
            Utils.showPage("Child_MainPage.fxml",btnBack);
        }
    }
}
