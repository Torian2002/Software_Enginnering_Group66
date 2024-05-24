package com.virtual_bank_g66.demo;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;
import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;


public class DepositAndWithdrawalController {

    @FXML
    private TextField amountField;

    @FXML
    private TextField descriptionField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField timeLimitField;

    @FXML
    private Button btnBack;

    @FXML
    private ComboBox<String> roleComboBox;

    @FXML
    private Text ServiceType;

    @FXML
    private void handleConfirm(ActionEvent event) {

        UserInfoBean userInfo = UserInfoBean.getInstance();
        String serviceType = ServiceType.getText();
        String accountType = roleComboBox.getValue();
        String description = descriptionField.getText();
        String password = passwordField.getText();

// Check all inputs are not null and not empty. Improved by handling potential null values from ComboBox and TextFields.
        if (serviceType == null || serviceType.isEmpty() ||
                accountType == null || accountType.trim().isEmpty() ||
                description == null || description.isEmpty() ||
                password == null || password.isEmpty() ||
                amountField.getText() == null || amountField.getText().isEmpty()) {
            Utils.showAlert("Error", "There are invalid inputs, please enter all required information.", Alert.AlertType.ERROR);
        } else {

            try {
                float amount = Float.parseFloat(amountField.getText()); // Parsing to check if the amount is a valid float.


                if (!password.equals(userInfo.getPassword())) {
                    Utils.showAlert("Error", "Original password is incorrect.", Alert.AlertType.ERROR);
                    return;
                }

                if (!updateAccountDetails(userInfo.getID(), serviceType, accountType, amount)) {
                    Utils.showAlert("Error", "Failed to update account details.", Alert.AlertType.ERROR);
                    return;
                }

                logTransaction(userInfo.getID(), LocalDateTime.now(), amount, accountType, serviceType, description);
                Utils.showAlert("Congratulations", "Transaction successful.", Alert.AlertType.INFORMATION);
            } catch (NumberFormatException e) {
                Utils.showAlert("Error", "Invalid amount. Please enter a valid number.", Alert.AlertType.ERROR);
            }
        }

    }

    private boolean updateAccountDetails(String userId, String serviceType, String accountType, float amount) {
        List<String> fileContent = new ArrayList<>();
        boolean accountExist = false;

        try (BufferedReader br = new BufferedReader(new FileReader(Utils.CSV_FILE_PATH_moneyInfo))) {
            String line;
            boolean firstLine = true;
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    fileContent.add(line);
                    continue;
                }
                String[] values = line.split(",");
                if (values[0].equals(userId)) {
                    accountExist = true;
                    if (!processAccountLine(values, serviceType, accountType, amount)) {
                        return false;
                    }
                    line = String.join(",", values);
                }
                fileContent.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Utils.showAlert("Error", "An error occurred while processing your request.", Alert.AlertType.ERROR);
            return false;
        }

        if (!accountExist) {
            Utils.showAlert("Error", "Account does not exist.", Alert.AlertType.ERROR);
            return false;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(Utils.CSV_FILE_PATH_moneyInfo))) {
            for (String s : fileContent) {
                bw.write(s + System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
            Utils.showAlert("Error", "An error occurred while saving your data.", Alert.AlertType.ERROR);
            return false;
        }

        return true;
    }

    private boolean processAccountLine(String[] values, String serviceType, String accountType, float amount) {

        float currentAmount = Float.parseFloat(values[1]);
        float savingAmount = Float.parseFloat(values[2]);
        float limit = Float.parseFloat(values[3]);
        String timeLimit = values[5];
        String initialDateStr = values[6];
        String expireDateStr = values[7];

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        if ("Withdrawal".equals(serviceType) && amount > limit) {
            Utils.showAlert("Sorry", "Exceed your account limitation.", Alert.AlertType.INFORMATION);
            return false;
        }

        if ("Withdrawal".equals(serviceType)) {
            if ("Current".equals(accountType) && currentAmount >= amount) {
                values[1] = String.format("%.2f", currentAmount - amount);
            } else if ("Saving".equals(accountType) && savingAmount >= amount) {
                LocalDateTime expireDate = LocalDateTime.parse(expireDateStr, formatter);
                if (now.isBefore(expireDate)) {
                    Utils.showAlert("Sorry", "Savings account funds are not yet available for withdrawal.", Alert.AlertType.INFORMATION);
                    return false;
                } else {
                    values[2] = String.format("%.2f", savingAmount - amount);
                }
            } else {
                Utils.showAlert("Sorry", "Insufficient funds.", Alert.AlertType.INFORMATION);
                return false;
            }
        } else {
            if ("Current".equals(accountType)) {
                values[1] = String.format("%.2f", currentAmount + amount);
            } else {

                values[2] = String.format("%.2f", savingAmount + amount);
                LocalDateTime initialDate = now;
                int timeLimitDays = Integer.parseInt(timeLimit);
                LocalDateTime expireDate = initialDate.plusDays(timeLimitDays);

                values[6] = initialDate.format(formatter);
                values[7] = expireDate.format(formatter);
            }
        }
        return true;
    }

    private void logTransaction(String userId, LocalDateTime dateTime, float amount, String accountType, String serviceType, String description) {
        File transactionFile = new File(Utils.CSV_FILE_PATH_transactionRecord);

        // Check if the file exists and is not a directory; create it with headers if it doesn't exist
        if (!transactionFile.exists()) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(transactionFile))) {
                // Write the headers for the CSV file
                bw.write("ID,Date,Amount,AccountType,ServiceType,Description" + System.lineSeparator());
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Error writing the header to the transaction record file.");
                return;
            }
        }

        // Append the transaction details to the file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(transactionFile, true))) {
            bw.write(userId + "," + Utils.formatDateTime(dateTime) + "," + amount + "," + accountType + "," + serviceType + "," + description + System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error writing the transaction record.");
        }
    }

    @FXML
    private void handleBack(ActionEvent event) {
        Utils.showPage("Child_MainPage.fxml", btnBack);
    }
}