package com.virtual_bank_g66.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The FileUtil class provides utility methods for reading and writing
 * user financial information from/to CSV files, as well as managing
 * user tasks and transactions.
 *
 * @version 5.0 May 25th, 2024
 * @author Jiabo Tong
 */
public class FileUtil {

    Utils Utils = new Utils();

    /**
     * Reads the money information of a user from the CSV file based on the user ID.
     *
     * @param id the user ID
     * @return a HashMap containing the user's financial information
     */
    public HashMap<String, String> readMoneyInfoCsv(String id) {
        String line;
        HashMap<String, String> data = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(Utils.CSV_FILE_PATH_moneyInfo))) {
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values[0].equals(id)) {
                    data.put("Current", values[1]);
                    data.put("Saving", values[2]);
                    data.put("Goal", values[3]);
                    data.put("LastLogin", values[8]);
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * Writes updated money information of a user to the CSV file.
     *
     * @param id the user ID
     * @param data a HashMap containing the updated financial information
     */
    public void writeMoneyInfoCsv(String id, HashMap<String, String> data) {
        String line;
        List<String> fileContent = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(Utils.CSV_FILE_PATH_moneyInfo))) {
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values[0].equals(id)) {
                    values[1] = data.get("Current");
                    values[2] = data.get("Saving");
                    values[3] = data.get("Goal");
                    values[8] = data.get("LastLogin");
                    line = String.join(",", values);
                }
                fileContent.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(Utils.CSV_FILE_PATH_moneyInfo))) {
            for (String s : fileContent) {
                bw.write(s + System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Ensures that a file exists at the specified path, creating it if necessary.
     *
     * @param filePath the path to the file
     */
    public void ensureFileExists(String filePath) {
        File file = new File(filePath);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to create the file: " + filePath);
        }
    }

    /**
     * Retrieves the spending limit for a user and sets it in the provided text field.
     *
     * @param ID the user ID
     * @param limitTextField the text field to set the limit
     */
    public void getLimit(String ID, TextField limitTextField)
    {
        try {
            List<String[]> lines = Files.lines(Paths.get(Utils.CSV_FILE_PATH_moneyInfo))
                    .map(line -> line.split(","))
                    .collect(Collectors.toList());

            for (String[] line : lines) {
                if (line[0].equals(ID)) {
                    limitTextField.setText(line[3]);
                }
            }
        } catch (IOException e) {
            Utils.showAlert("Account Error", "Account does not exist!", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    /**
     * Retrieves the savings goal for a user and sets it in the provided text field.
     *
     * @param ID the user ID
     * @param GoalTextField the text field to set the goal
     */
    public void getGoal(String ID, TextField GoalTextField)
    {
        try {
            List<String[]> lines = Files.lines(Paths.get(Utils.CSV_FILE_PATH_moneyInfo))
                    .map(line -> line.split(","))
                    .collect(Collectors.toList());

            for (String[] line : lines) {
                if (line[0].equals(ID)) {
                    GoalTextField.setText(line[4]);
                }
            }
        } catch (IOException e) {
            Utils.showAlert("Account Error", "Account does not exist!", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    /**
     * Overloaded method to retrieve the savings goal for a user and set it in the provided text element.
     *
     * @param ID the user ID
     * @param GoalTextField the text element to set the goal
     */
    public  void getGoal(String ID, Text GoalTextField)
    {
        try {
            List<String[]> lines = Files.lines(Paths.get(Utils.CSV_FILE_PATH_moneyInfo))
                    .map(line -> line.split(","))
                    .collect(Collectors.toList());

            for (String[] line : lines) {
                if (line[0].equals(ID)) {
                    GoalTextField.setText(line[4]);
                }
            }
        } catch (IOException e) {
            Utils.showAlert("Account Error", "Account does not exist!", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    /**
     * Updates the savings goal for a user in the CSV file.
     *
     * @param ID the user ID
     * @param goal the new goal to set
     */
    public  void updateGoal(String ID, String goal) {
        Path filePath = Paths.get(Utils.CSV_FILE_PATH_moneyInfo); // Ensure Utils.CSV_FILE_PATH_moneyInfo is the correct path to the CSV file.
        if (goal == null || goal.isEmpty()){
            Utils.showAlert("Error", "Invalid input please enter again: " , Alert.AlertType.ERROR);
            return;
        }
        try {
            // Read all lines and update the goal where necessary
            List<String[]> lines = Files.lines(filePath)
                    .map(line -> line.split(","))
                    .collect(Collectors.toList());

            boolean goalUpdated = false; // To check if any goal has been updated
            for (String[] line : lines) {
                if (line[0].equals(ID)) {
                    line[4] = goal; // Assuming that the goal is at index 4
                    goalUpdated = true;
                }
            }

            if (goalUpdated) {
                try (BufferedWriter bw = Files.newBufferedWriter(filePath)) {
                    for (String[] line : lines) {
                        bw.write(String.join(",", line));
                        bw.newLine();
                    }
                } catch (IOException e) {
                    Utils.showAlert("IO Error", "Failed to write to the money info file: " + e.getMessage(), Alert.AlertType.ERROR);
                    e.printStackTrace();
                    return;
                }

                Utils.showAlert("Success", "Goal updated successfully!", Alert.AlertType.INFORMATION);

            } else {
                Utils.showAlert("Update Error", "No matching account found to update the goal.", Alert.AlertType.ERROR);
            }
        } catch (IOException e) {
            Utils.showAlert("File Error", "Error accessing the money info file: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    /**
     * Loads transaction records for a user into the specified table view.
     *
     * @param ID the user ID
     * @param transactionTable the table view to set the transaction records
     */
    public  void loadTransactionRecord(String ID, TableView<TransactionRecordBean> transactionTable) {
        ObservableList<TransactionRecordBean> transactionRecordBeans = FXCollections.observableArrayList();

        try (BufferedReader br = new BufferedReader(new FileReader(Utils.CSV_FILE_PATH_transactionRecord))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] details = line.split(",");
                if (details[0].equals(ID)) {
                    transactionRecordBeans.add(new TransactionRecordBean(details[1], details[2], details[3], details[4], details[5]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        transactionTable.setItems(transactionRecordBeans);
    }

    /**
     * Loads tasks for a user from the CSV file.
     *
     * @param ID the user ID
     * @return a list of TaskBean objects representing the user's tasks
     */
    public  List<TaskBean> loadTasks(String ID) {
        String filePath = Utils.CSV_FILE_PATH_tasks;
        ensureFileExists(filePath);
        List<TaskBean> tasks = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine(); // Skip the header
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(ID)) {
                    TaskBean task = new TaskBean(data[0], data[1], data[2], data[3], data[4]);
                    tasks.add(task);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    /**
     * Updates tasks in the CSV file based on the specified service type.
     *
     * @param updatedTasks a list of TaskBean objects representing the updated tasks
     * @param service the type of service to perform ("stateChange", "Delete", or "Add")
     */
    public  void updateTasks(List<TaskBean> updatedTasks, String service) {
        String filePath = Utils.CSV_FILE_PATH_tasks;
        File file = new File(filePath);
        if (!file.exists()) {
            Utils.showAlert("Error", "File does not exist and cannot be written to.", Alert.AlertType.ERROR);
            return;
        }
        List<TaskBean> existingTasks = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    TaskBean task = new TaskBean(parts[0], parts[1], parts[2], parts[3], parts[4]);
                    existingTasks.add(task);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        if (service.equals("stateChange")){
            for (TaskBean updatedTask : updatedTasks) {
                for (TaskBean existingTask : existingTasks) {
                    if (existingTask.getID().equals(updatedTask.getID()) &&
                            existingTask.getTaskNumber().equals(updatedTask.getTaskNumber())) {
                        existingTask.setState(updatedTask.getState());
                    }
                }
            }
        } else if (service.equals("Delete")){
            UserInfoBean userInfo = UserInfoBean.getInstance();
            List<TaskBean> filteredTasks = existingTasks.stream()
                    .filter(task -> !task.getID().equals(userInfo.getAssociated_ID()))
                    .collect(Collectors.toList());
            filteredTasks.addAll(updatedTasks);
            existingTasks = filteredTasks;

        } else if(service.equals("Add")){
            TaskBean newTask = updatedTasks.get(updatedTasks.size() - 1);
            existingTasks.add(newTask);
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write("ID,TaskNum,TaskContent,Reward,State\n");
            for (TaskBean task : existingTasks) {
                bw.write(String.format("%s,%s,%s,%s,%s\n", task.getID(), task.getTaskNumber(), task.getTaskContent(),task.getReward(), task.getState()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * Sends a reward to a child's current account in the CSV file.
     *
     * @param ID the user ID of the child
     * @param Reward the reward amount to send
     */
    public  void sendRewardToChild(String ID, String Reward) {
        String filePath = Utils.CSV_FILE_PATH_moneyInfo;
        File file = new File(filePath);
        if (!file.exists()) {
            Utils.showAlert("Error", "Money information file does not exist.", Alert.AlertType.ERROR);
            return;
        }

        List<String[]> lines = new ArrayList<>();
        boolean updatePerformed = false;

        // Read existing records
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(ID)) {
                    float current = Float.parseFloat(data[1]);
                    float reward = Float.parseFloat(Reward);
                    data[1] = String.format("%.2f", (current + reward));  // Update current account
                    updatePerformed = true;
                }
                lines.add(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Utils.showAlert("IO Error", "Failed to read the money info file.", Alert.AlertType.ERROR);
            return;
        }

        // Write updated records back to file
        if (updatePerformed) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                for (String[] data : lines) {
                    bw.write(String.join(",", data) + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
                Utils.showAlert("IO Error", "Failed to write to the moneyInfo.csv.", Alert.AlertType.ERROR);
                return;
            }
            Utils.showAlert("Success", "Reward successfully sent to child's current account.", Alert.AlertType.INFORMATION);
        }
    }

}
