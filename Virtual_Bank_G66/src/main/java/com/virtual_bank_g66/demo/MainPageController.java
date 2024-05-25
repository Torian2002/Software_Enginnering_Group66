package com.virtual_bank_g66.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import jdk.jshell.execution.Util;

public class MainPageController {

    // Elements for common usage
    @FXML
    private Label labelWelcome;
    @FXML
    private Button btnLogout;
    @FXML
    private Button btnAccountInfo;

    // Elements for child
    @FXML
    private Button btnTransaction;
    @FXML
    private Button btnWithdrawal;
    @FXML
    private Button btnDeposit;
    @FXML
    private Button btnManageTasks;

    // Elements for parent
    @FXML
    private Button btnCheckChildTransactions;
    @FXML
    private Button btnBindChild;
    @FXML
    private Button btnSetTransactionLimit;
    @FXML
    private Button btnSetTasksForChild;

    Utils Utils = new Utils();
    FileUtil FileUtil = new FileUtil();


    /*
    The following are the common functions
     */

    public void initialize() {
        String username = UserInfoBean.getInstance().getUserName();
        labelWelcome.setText("Welcome, " + username + ", Please Select Service!");
    }

    @FXML
    private void onLogoutClicked() {
        UserInfoBean.cleanUserSession();
        Utils.showPage("Login.fxml", btnLogout);
    }

    @FXML
    private void onCheckAccountInfoClicked() {
        if (UserInfoBean.getInstance().getRole().equals("Parent")){
            Utils.showPage("Parent_InfoPage.fxml", btnAccountInfo);
        } else {
            Utils.showPage("Child_InfoPage.fxml", btnAccountInfo);
        }

    }


    /*
    The following are the functions of child's main page
     */


    @FXML
    private void onTransactionClicked() {
        Utils.showPage("Transaction.fxml", btnTransaction);
    }

    @FXML
    private void onDepositClicked(ActionEvent event) {
        Utils.showPage("Deposit.fxml", btnDeposit);
    }

    @FXML
    private void onWithdrawalClicked(ActionEvent event) {
        Utils.showPage("Withdrawal.fxml", btnWithdrawal);
    }

    @FXML
    private void onInvestmentClicked(ActionEvent event) {
        Utils.showAlert("Inform", "To be finished in the following iterations", Alert.AlertType.INFORMATION);
    }

    @FXML
    private void onManageTasksClicked() {
        Utils.showPage("Child_TasksGoalPage.fxml", btnManageTasks);
    }


    /*
    The following are the functions of parent's main page
     */


    @FXML
    private void onSetRelationshipWithChildClicked() {
        Utils.showPage("RelateChild.fxml", btnBindChild);
    }

    @FXML
    private void onCheckChildTransactionsClicked() {
        Utils.showPage("TransactionRecord.fxml", btnCheckChildTransactions);
    }

    @FXML
    private void onSetTransactionLimitClicked() {
        Utils.showPage("SetLimit.fxml", btnSetTransactionLimit);
    }

    @FXML
    private void onSetTasksForChildClicked() {
        Utils.showPage("Parent_TasksGoalPage.fxml", btnSetTasksForChild);
    }

}
