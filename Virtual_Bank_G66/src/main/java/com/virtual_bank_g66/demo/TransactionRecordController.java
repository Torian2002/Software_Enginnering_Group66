package com.virtual_bank_g66.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.text.Text;

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
    @FXML
    private void onBackClicked(ActionEvent event) {
        Utils.showPage("Child_MainPage.fxml",btnBack);
    }

    // return logic for transaction record page(this page is provided for both parent and children)
    @FXML
    private void onReturnClicked(ActionEvent event) {
      UserInfoBean userInfoBean = UserInfoBean.getInstance();
      if (userInfoBean.getRole().equals("Child")){
          Utils.showPage("Transaction.fxml", btnReturn);
      } else {
          Utils.showPage("Parent_MainPage.fxml", btnReturn);
      }
    }

    @FXML
    private void onTransferClicked(ActionEvent event) {
        Utils.showAlert("Inform", "To be finished in the following iterations", Alert.AlertType.INFORMATION);
    }

}