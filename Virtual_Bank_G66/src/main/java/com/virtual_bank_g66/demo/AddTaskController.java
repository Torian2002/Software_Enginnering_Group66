package com.virtual_bank_g66.demo;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.List;

public class AddTaskController {
    @FXML
    private Button btnBack;
    @FXML
    private TextField TaskContent;
    @FXML
    private TextField Reward;
    @FXML
    private PasswordField passwordField;

    @FXML
    private void onConfirmClicked() {
        String content = TaskContent.getText().trim();
        float reward;
        String rewardStr = Reward.getText().trim();
        String password = passwordField.getText();

        if (!UserInfoBean.getInstance().getAssociated_child().equals(" ")){
            if (content.isEmpty()) {
                Utils.showAlert("Validation Error", "Task content cannot be empty.", Alert.AlertType.ERROR);
                TaskContent.clear();
                passwordField.clear();
                return;
            }

            try {
                reward = Float.parseFloat(rewardStr);
            } catch (NumberFormatException e) {
                Utils.showAlert("Validation Error", "Reward must be a valid number.", Alert.AlertType.ERROR);
                Reward.clear();
                passwordField.clear();
                return;
            }

            if (!password.equals(UserInfoBean.getInstance().getPassword())) {
                Utils.showAlert("Authentication Error", "Incorrect password.", Alert.AlertType.ERROR);
                passwordField.clear();
                return;
            }

            // Load existing tasks to determine the new task number
            List<TaskBean> existingTasks = FileUtil.loadTasks(UserInfoBean.getInstance().getAssociated_ID());
            int newTaskNum = existingTasks.size() + 1;

            // Create new task
            TaskBean newTask = new TaskBean(UserInfoBean.getInstance().getAssociated_ID(), Integer.toString(newTaskNum), content, rewardStr, "To be finished");
            existingTasks.add(newTask);
            FileUtil.updateTasks(existingTasks,  "Add");
            Utils.showAlert("Success", "New task added successfully.", Alert.AlertType.INFORMATION);
            TaskContent.clear();
            Reward.clear();
            passwordField.clear();
        } else {
            TaskContent.clear();
            Reward.clear();
            passwordField.clear();
            Utils.showAlert("Error", "Your should relate to your child first.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void onBackClicked(){
        Utils.showPage("Parent_TasksGoalPage.fxml", btnBack);
    }
}
