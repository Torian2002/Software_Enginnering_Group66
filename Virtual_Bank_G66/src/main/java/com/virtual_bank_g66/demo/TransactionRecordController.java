package com.virtual_bank_g66.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.text.Text;

/**
 * The TransactionRecordController class manages the interaction logic for the Transaction Record page.
 * It handles the initialization of the page, the loading of transaction records, and the navigation between pages.
 *
 * @version 1.0 April 10th, 2024 - trace and view transaction record including deposit and withdrawal
 * @version 2.0 April 24th, 2024 - introduce utility classes when refactoring code
 * @author Jiabo Tong
 * @author Jiameng Chen
 */
public class TransactionRecordController {

    @FXML
    private Button btnBack;

    @FXML
    private Button btnTransactionRecord;

    @FXML
    private Button btnReturn;

    @FXML
    private TableView<TransactionRecordBean> transactionTable;

    @FXML
    private Label usernameField;

    @FXML
    private Text Title;

    Utils Utils = new Utils();
    FileUtil FileUtil = new FileUtil();

    /**
     * Initializes the controller class. This method is automatically called after the FXML file has been loaded.
     * It checks the user's role and loads the appropriate transaction records.
     */
    @FXML
    public void initialize() {
        if (Title.getText().equals("Transaction Record")){
            UserInfoBean userInfoBean = UserInfoBean.getInstance();
            if (userInfoBean.getRole().equals("Child")){
                usernameField.setText("Account Name: "+ userInfoBean.getUserName());
                FileUtil.loadTransactionRecord(userInfoBean.getID(), transactionTable);
            } else {
                if (!userInfoBean.getAssociated_child().equals(" ")) {
                    usernameField.setText("Child's Name: "+ userInfoBean.getAssociated_child());
                    FileUtil.loadTransactionRecord(userInfoBean.getAssociated_ID(), transactionTable);
                } else {
                    Utils.showAlert("Error", "You should relate to your child first.", Alert.AlertType.ERROR);
                }
            }
        }
    }

    /**
     * Handles the action when the transaction record button is clicked.
     * It loads and displays the Transaction Record page.
     */
    @FXML
    public void onTransactionRecordClicked() {
        try {
            // Load the Transaction Record FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TransactionRecord.fxml"));
            Parent recordPage = loader.load();
            // Get the controller and initialize it if necessary
            TransactionRecordController controller = loader.getController();
            controller.initialize(); // Manual initialization if auto doesn't work
            Scene scene = new Scene(recordPage);
            Stage stage = (Stage) btnTransactionRecord.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Back logic for transaction page
    /**
     * Handles the action when the back button is clicked on the transaction page.
     * It navigates back to the Child Main Page.
     *
     * @param event the action event
     */
    @FXML
    private void onBackClicked(ActionEvent event) {
        Utils.showPage("Child_MainPage.fxml",btnBack);
    }

    // return logic for transaction record page(this page is provided for both parent and children)
    /**
     * Handles the action when the return button is clicked on the transaction record page.
     * It navigates back to the appropriate main page based on the user's role.
     *
     * @param event the action event
     */
    @FXML
    private void onReturnClicked(ActionEvent event) {
      UserInfoBean userInfoBean = UserInfoBean.getInstance();
      if (userInfoBean.getRole().equals("Child")){
          Utils.showPage("Transaction.fxml", btnReturn);
      } else {
          Utils.showPage("Parent_MainPage.fxml", btnReturn);
      }
    }

    /**
     * Handles the action when the transfer button is clicked.
     * Currently, it displays an informational alert indicating that this feature will be implemented in future iterations.
     *
     * @param event the action event
     */
    @FXML
    private void onTransferClicked(ActionEvent event) {
        Utils.showAlert("Inform", "To be finished in the following iterations", Alert.AlertType.INFORMATION);
    }

}