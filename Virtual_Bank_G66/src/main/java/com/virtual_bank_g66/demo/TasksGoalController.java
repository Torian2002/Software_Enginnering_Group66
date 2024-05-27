package com.virtual_bank_g66.demo;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * The TasksGoalController class manages the tasks and savings goals
 * for both parents and children. It handles the initialization of the
 * task table, task-related actions (add, delete, finish, confirm),
 * and setting savings goals.
 *
 * @version 1.0 April 10th, 2024 - fulfill basic requirement to manage tasks and saving goals
 * @version 2.0 April 24th, 2024 - introduce utility classes when refactoring code
 * @author Jiabo Tong
 * @author Kexin Zhang
 */
public class TasksGoalController {
    @FXML
    private TableView<TaskBean> tasksTable;
    @FXML
    private TableColumn<TaskBean, String> taskNumberColumn;
    @FXML
    private TableColumn<TaskBean, String> taskContentColumn;
    @FXML
    private TableColumn<TaskBean, String> rewardColumn;
    @FXML
    private TableColumn<TaskBean, String> stateColumn;
    @FXML
    private TableColumn<TaskBean, String> actionColumn;
    @FXML
    private Button btnBack;
    @FXML
    private Button btnAdd;
    @FXML
    private Text SavingGoal;
    @FXML
    private Text preText;

    @FXML
    private TextField currentGoalField;
    @FXML
    private TextField savingGoalField;
    @FXML
    private TextField taskNum;

    Utils Utils = new Utils();
    FileUtil FileUtil = new FileUtil();

    /**
     * Initializes the task table columns and loads the tasks.
     */
    public void initialize() {
        taskNumberColumn.setCellValueFactory(new PropertyValueFactory<>("taskNumber"));
        taskContentColumn.setCellValueFactory(new PropertyValueFactory<>("taskContent"));
        rewardColumn.setCellValueFactory(new PropertyValueFactory<>("reward"));
        stateColumn.setCellValueFactory(new PropertyValueFactory<>("state"));
        loadTasks();
    }

    /**
     * Loads the tasks for the user and sets the appropriate cell factory
     * for the action column based on the user's role.
     */
    private void loadTasks() {
        UserInfoBean userInfo = UserInfoBean.getInstance();
        if (userInfo.getRole().equals("Child")){
            actionColumn.setCellFactory(param -> new ButtonCell("Finish", "To be confirmed"));
            List<TaskBean> tasks = FileUtil.loadTasks(userInfo.getID());
            tasksTable.getItems().setAll(tasks);
            FileUtil.getGoal(userInfo.getID(), currentGoalField);
        } else {
            if (userInfo.getAssociated_ID().equals(" ")){
                preText.setText("You don't have any associated goal");
            } else {
                actionColumn.setCellFactory(param -> new ButtonCell("Confirm", "Already done"));
                List<TaskBean> tasks = FileUtil.loadTasks(userInfo.getAssociated_ID());
                tasksTable.getItems().setAll(tasks);
                FileUtil.getGoal(userInfo.getAssociated_ID(), SavingGoal);
            }
        }
    }

    /**
     * A TableCell implementation that adds a button to each cell.
     * The button's action depends on the task state and user role.
     */
    private class ButtonCell extends TableCell<TaskBean, String> {
        private final Button btn;
        private final String State;

        /**
         * A TableCell implementation that adds a button to each cell.
         * The button's action depends on the task state and user role.
         */
        public ButtonCell(String btnName, String State)
        {
            this.btn = new Button(btnName);
            this.State = State;
            this.btn.setOnAction(event -> {
                TaskBean task = getTableView().getItems().get(getIndex());
                if (task != null) {
                    if (btnName.equals("Confirm") && task.getState().equals("To be confirmed")) {
                        task.setState(this.State);
                        getTableView().refresh();
                        FileUtil.updateTasks(getTableView().getItems(), "stateChange");
                        FileUtil.sendRewardToChild(task.getID(), task.getReward());
                    } else if (btnName.equals("Confirm") && task.getState().equals("Already done")) {
                        Utils.showAlert("INFO", "Reward has been given to Child!", Alert.AlertType.INFORMATION);
                    } else if (btnName.equals("Confirm") && task.getState().equals("To be finished")) {
                        Utils.showAlert("INFO", "Task has not been finished by your child!", Alert.AlertType.INFORMATION);
                    } else if (btnName.equals("Finish") && (task.getState().equals("Already done") || task.getState().equals("To be confirmed"))) {
                        Utils.showAlert("INFO", "Task has already been finished or waiting to be confirmed!", Alert.AlertType.INFORMATION);
                    } else {
                        task.setState(this.State);
                        getTableView().refresh();
                        FileUtil.updateTasks(getTableView().getItems(), "stateChange");
                    }
                }
            });
        }

        /**
         * Updates the item within the TableCell.
         *
         * @param item the item to update
         * @param empty whether the cell is empty
         */
        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || getIndex() >= getTableView().getItems().size()) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(this.btn);
            }
        }
    }

    /**
     * Deletes the selected task and updates the task numbers
     * for the remaining tasks.
     */
    @FXML
    private void onDeletClicked() {
        String taskNumberToDelete = taskNum.getText().trim();
        List<TaskBean> tasks = FileUtil.loadTasks(UserInfoBean.getInstance().getAssociated_ID());
        List<TaskBean> updatedTasks = new ArrayList<>();
        boolean decrementFollowingTasks = false;

        for (TaskBean task : tasks) {
            if (task.getTaskNumber().equals(taskNumberToDelete) && task.getID().equals(UserInfoBean.getInstance().getAssociated_ID())) {
                decrementFollowingTasks = true; // Mark to start decrementing task numbers
                continue; // Skip adding this task to updatedTasks
            }
            if (decrementFollowingTasks && task.getID().equals(UserInfoBean.getInstance().getAssociated_ID())) {
                // Decrement the task number by 1 for all subsequent tasks of the same child ID
                int newTaskNumber = Integer.parseInt(task.getTaskNumber()) - 1;
                task.setTaskNumber(String.valueOf(newTaskNumber));
            }
            updatedTasks.add(task);
        }

        if (decrementFollowingTasks) {
            FileUtil.updateTasks(updatedTasks, "Delete"); // Write the updated list back to the file
            tasksTable.getItems().setAll(updatedTasks); // Refresh the TableView with updated tasks
            Utils.showAlert("Success", "Task deleted and remaining tasks updated.", Alert.AlertType.INFORMATION);
        } else {
            Utils.showAlert("Error", "Task number not found for the given child ID.", Alert.AlertType.ERROR);
        }
    }

    /**
     * Navigates to the Add Task page.
     */
    @FXML
    private void onADDClicked(){
        Utils.showPage("AddTaskPage.fxml", btnAdd);
    }

    /**
     * Sets the saving goal for the user and updates the displayed goal.
     */
    @FXML
    private void onSetSavingGoalClicked(){
        FileUtil.updateGoal(UserInfoBean.getInstance().getID(), savingGoalField.getText());
        if (!savingGoalField.getText().isEmpty()) {
            currentGoalField.setText(savingGoalField.getText());
        }
        savingGoalField.clear();
    }

    /**
     * Navigates back to the main page based on the user's role.
     */
    @FXML
    private void onBackClicked() {
        UserInfoBean userInfo = UserInfoBean.getInstance();
        if (userInfo.getRole().equals("Child")){
            Utils.showPage("Child_MainPage.fxml", btnBack);
        } else {
            Utils.showPage("Parent_MainPage.fxml", btnBack);
        }
    }

}
