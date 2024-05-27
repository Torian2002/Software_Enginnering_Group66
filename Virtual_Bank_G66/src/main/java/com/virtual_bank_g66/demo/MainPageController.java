package com.virtual_bank_g66.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import jdk.jshell.execution.Util;

/**
 * The MainPageController class manages the functionality of the main page
 * displayed to both parents and children after logging in.
 *
 * @version 5.0 May 25th, 2024
 * @author Jiabo Tong
 * @author Jiameng Chen
 *
 * This class controls the main page, providing different functionalities
 * based on whether the user is a parent or a child.
 *
 * <ul>
 * <li>Common Functions</li>
 * <li>Child Functions</li>
 * <li>Parent Functions</li>
 * </ul>
 *
 * The class uses the Utils and FileUtil classes to handle various operations
 * such as page transitions and file operations.
 *
 * @see Utils
 * @see FileUtil
 * @see UserInfoBean
 *
 */
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

    /**
     * Initializes the main page with a welcome message.
     * Sets the welcome message with the username of the logged-in user.
     */
    public void initialize() {
        String username = UserInfoBean.getInstance().getUserName();
        labelWelcome.setText("Welcome, " + username + ", Please Select Service!");
    }

    /**
     * Logs out the current user and navigates back to the login page.
     * Clears the current user session and shows the login page.
     */
    @FXML
    private void onLogoutClicked() {
        UserInfoBean.cleanUserSession();
        Utils.showPage("Login.fxml", btnLogout);
    }

    /**
     * Navigates to the account information page based on the user's role.
     * If the user is a parent, navigates to Parent_InfoPage.fxml; if the user is a child, navigates to Child_InfoPage.fxml.
     */
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

    /**
     * Navigates to the transaction page for the child.
     */
    @FXML
    private void onTransactionClicked() {
        Utils.showPage("Transaction.fxml", btnTransaction);
    }

    /**
     * Navigates to the deposit page for the child.
     * @param event the event that triggers this method
     */
    @FXML
    private void onDepositClicked(ActionEvent event) {
        Utils.showPage("Deposit.fxml", btnDeposit);
    }

    /**
     * Navigates to the withdrawal page for the child.
     * @param event the event that triggers this method
     */
    @FXML
    private void onWithdrawalClicked(ActionEvent event) {
        Utils.showPage("Withdrawal.fxml", btnWithdrawal);
    }

    /**
     * Displays an information alert indicating that the investment feature is not yet implemented.
     * @param event the event that triggers this method
     */
    @FXML
    private void onInvestmentClicked(ActionEvent event) {
        Utils.showAlert("Inform", "To be finished in the following iterations", Alert.AlertType.INFORMATION);
    }

    /**
     * Navigates to the task management page for the child.
     */
    @FXML
    private void onManageTasksClicked() {
        Utils.showPage("Child_TasksGoalPage.fxml", btnManageTasks);
    }


    /*
    The following are the functions of parent's main page
     */

    /**
     * Navigates to the page for setting the relationship with a child.
     */
    @FXML
    private void onSetRelationshipWithChildClicked() {
        Utils.showPage("RelateChild.fxml", btnBindChild);
    }

    /**
     * Navigates to the page for checking a child's transactions.
     */
    @FXML
    private void onCheckChildTransactionsClicked() {
        Utils.showPage("TransactionRecord.fxml", btnCheckChildTransactions);
    }

    /**
     * Navigates to the page for setting a transaction limit for the child.
     */
    @FXML
    private void onSetTransactionLimitClicked() {
        Utils.showPage("SetLimit.fxml", btnSetTransactionLimit);
    }

    /**
     * Navigates to the page for setting tasks for the child.
     */
    @FXML
    private void onSetTasksForChildClicked() {
        Utils.showPage("Parent_TasksGoalPage.fxml", btnSetTasksForChild);
    }

}
