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
import java.util.List;
import java.util.stream.Collectors;

public class FileUtil {

    public static void ensureFileExists(String filePath) {
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
    public static void getLimit(String ID, TextField limitTextField)
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

    public static void getGoal(String ID, TextField GoalTextField)
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

    public static void getGoal(String ID, Text GoalTextField)
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


    public static void updateGoal(String ID, String goal) {
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


    public static void loadTransactionRecord(String ID, TableView<TransactionRecordBean> transactionTable) {
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

    public static List<TaskBean> loadTasks(String ID) {
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

    public static void updateTasks(List<TaskBean> updatedTasks, String service) {
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
            UserSessionBean userSession = UserSessionBean.getInstance();
            List<TaskBean> filteredTasks = existingTasks.stream()
                    .filter(task -> !task.getID().equals(userSession.getAssociated_ID()))
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


    public static void sendRewardToChild(String ID, String Reward) {
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
