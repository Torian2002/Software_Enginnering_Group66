package com.virtual_bank_g66.demo;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
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

    Utils Utils = new Utils();
    FileUtil FileUtil = new FileUtil();

    public void initialize() {

        UserInfoBean userInfo = UserInfoBean.getInstance();
        loadUserData(userInfo);
    }

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

    private void updateFields(HashMap<String, String> data) {
        if (data != null) {
            currentAccountField.setText(data.getOrDefault("Current", "0"));
            savingAccountField.setText(data.getOrDefault("Saving", "0"));
            limitField.setText(data.getOrDefault("Goal", "0"));
        }
    }

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

    @FXML
    private void onCheckClicked() {
        Utils.showAlert("Inform", "To be finished in the following iterations", Alert.AlertType.INFORMATION);
    }
}
