package com.virtual_bank_g66.demo;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.List;

/**
 * The AddTaskController class is responsible for handling the task addition view.
 * It provides functionality to add new tasks with validation and authentication.
 *
 * @version 1.0 April 10th, 2024 - support parent to add new tasks with validation
 * @version 2.0 April 24th, 2024 - introduce utility classes when refactoring code
 * @author Jiabo Tong
 * @author Kexin Zhang
 */
public class AddTaskController {
    @FXML
    private Button btnBack;
    @FXML
    private TextField TaskContent;
    @FXML
    private TextField Reward;
    @FXML
    private PasswordField passwordField;

    Utils Utils = new Utils();
    FileUtil FileUtil = new FileUtil();

    /**
     * Handles the action of the confirm button click. Validates input, authenticates the user,
     * and adds a new task if validation and authentication succeed.
     */
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

    /**
     * Handles the action of the back button click. Navigates back to the Parent_TasksGoalPage.fxml page.
     */
    @FXML
    private void onBackClicked(){
        Utils.showPage("Parent_TasksGoalPage.fxml", btnBack);
    }
}
