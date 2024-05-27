package com.virtual_bank_g66.demo;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;

/**
 * The AccountInfoController class is responsible for handling the account information view.
 * It initializes user data, updates interest calculations, and manages navigation between different views.
 *
 * @version 5.0 May 25th, 2024
 * @author Jiabo Tong
 * @author Ruoqi Liu
 */

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

    Utils Utils = new Utils();
    FileUtil FileUtil = new FileUtil();

    /**
     * Initializes the controller class and loads user data.
     */
    public void initialize() {

        UserInfoBean userInfo = UserInfoBean.getInstance();
        loadUserData(userInfo);
    }

    /**
     * Loads the user data and populates the fields accordingly.
     * @param userInfo The user information bean containing user data.
     */
    private void loadUserData(UserInfoBean userInfo) {
        nameField.setText(userInfo.getUserName());
        roleField.setText(userInfo.getRole());

        if ("Child".equals(userInfo.getRole())) {
            HashMap<String, String> data = FileUtil.readMoneyInfoCsv(userInfo.getID());
            updateInterest(data);
            FileUtil.writeMoneyInfoCsv(userInfo.getID(), data);

        } else if ("Parent".equals(userInfo.getRole())) {

            if ( !userInfo.getAssociated_child().equals(" ")  &&  !userInfo.getAssociated_ID().equals(" ")) {

                HashMap<String, String> data = FileUtil.readMoneyInfoCsv(userInfo.getAssociated_ID());
                updateInterest(data);
                FileUtil.writeMoneyInfoCsv(userInfo.getAssociated_ID(), data);
                currentChild.setText(userInfo.getAssociated_child());
            } else {
                Utils.showAlert("Alert", "You should relate to your child first.", Alert.AlertType.ERROR);
                currentChild.setText("You don't have any associated goal");
            }
        }
    }

    /**
     * Updates the fields with the data from the given HashMap.
     * @param data A HashMap containing the account information.
     */
    private void updateFields(HashMap<String, String> data) {
        if (data != null) {
            currentAccountField.setText(data.getOrDefault("Current", "0"));
            savingAccountField.setText(data.getOrDefault("Saving", "0"));
            limitField.setText(data.getOrDefault("Goal", "0"));
        }
    }

    /**
     * Updates the interest on the current and saving amounts based on the time elapsed since the last login.
     * @param data A HashMap containing the account information.
     */
    private void updateInterest(HashMap<String, String> data) {
        if (data != null) {

            LocalDateTime currentDate= LocalDateTime.now();
            if (data.get("LastLogin").equals(" "))
            {
                data.put("LastLogin", Utils.formatDateTime(currentDate));
                return;
            }
            LocalDateTime lastLoginDate = LocalDateTime.parse(data.get("LastLogin"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

            long daysBetween = ChronoUnit.DAYS.between(lastLoginDate, currentDate);

            float currentAmount = Float.parseFloat(data.get("Current"));
            float savingAmount = Float.parseFloat(data.get("Saving"));

            currentAmount *= (float) Math.pow(1 + Utils.Current_Interest_Rate/365, daysBetween);
            savingAmount *=  (float) Math.pow(1 + Utils.Saving_Interest_Rate/365, daysBetween);

            data.put("Current", String.format("%.2f", currentAmount));
            data.put("Saving", String.format("%.2f", savingAmount));
            data.put("LastLogin", Utils.formatDateTime(currentDate));

            updateFields(data);
        }
    }

    /**
     * Handles the action of the edit button click. Navigates to the EditInfo.fxml page.
     */
    @FXML
    private void onEditClicked() {
        Utils.showPage("EditInfo.fxml",btnEdit);
    }

    /**
     * Handles the action of the back button click. Navigates to the main page based on the user role.
     */
    @FXML
    private void onBackClicked() {
        if (roleField.getText().equals("Parent")) {
            Utils.showPage("Parent_MainPage.fxml",btnBack);
        } else {
            Utils.showPage("Child_MainPage.fxml",btnBack);
        }
    }

    /**
     * Handles the action of the check button click. Shows an information alert indicating future implementation.
     */
    @FXML
    private void onCheckClicked() {
        Utils.showAlert("Inform", "To be finished in the following iterations", Alert.AlertType.INFORMATION);
    }
}
