package com.virtual_bank_g66.demo;

import javafx.scene.control.Alert;

import java.io.*;

/**
 * The AccountService class provides services related to account management,
 * including account creation and password validation.
 *
 * @version 5.0 May 25th, 2024
 * @author Jiabo Tong
 * @author Ruoqi Liu
 */
public class AccountService {

    private  String userInfoFilePath;
    private  String moneyInfoFilePath;
    private  Utils Utils;

    /**
     * Constructs an AccountService with the specified file paths and utility instance.
     *
     * @param userInfoFilePath The file path for user information storage.
     * @param moneyInfoFilePath The file path for money information storage.
     * @param utils The Utils instance for utility functions.
     */
    public AccountService(String userInfoFilePath, String moneyInfoFilePath, Utils utils) {
        this.userInfoFilePath = userInfoFilePath;
        this.moneyInfoFilePath = moneyInfoFilePath;
        this.Utils = utils;
    }

    /**
     * Validates the given password based on specific criteria.
     *
     * @param password The password to be validated.
     * @return true if the password is valid, false otherwise.
     */
    public static boolean isValid(String password) {
        if (password == null || password.length() < 5 || password.length() > 9) {
            return false;
        }

        boolean hasLetter = false;
        boolean hasDigit = false;

        for (char c : password.toCharArray()) {
            if (Character.isLetter(c)) {
                hasLetter = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else {
                return false;
            }
        }

        return hasLetter && hasDigit;
    }

    /**
     * Creates a new account with the specified role, name, password, and email.
     *
     * @param role The role of the user.
     * @param name The name of the user.
     * @param password The password for the account.
     * @param email The email of the user.
     * @return true if the account is successfully created, false otherwise.
     */
    public boolean createAccount(String role, String name, String password, String email) {
        if (role.equals("")||name.equals("")||password.equals("")||email.equals(""))
            return false;

        if (!isValid(password)) {
            return false;
        }

        File file_userInfo = new File(userInfoFilePath);

        File userinfoDir = file_userInfo.getParentFile();
        if (!userinfoDir.exists() && !userinfoDir.mkdirs()) {
            Utils.showAlert("Directory Error", "Failed to create userinfo directories.", Alert.AlertType.ERROR);
            return false;
        }

        int accountNumber = 1;
        boolean accountExists = false;

        if (file_userInfo.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file_userInfo))) {
                String line;
                while ((line = br.readLine()) != null) {
                    accountNumber++;
                    String[] data = line.split(",");
                    if (data[1].equals(role) && data[2].equals(name)) {
                        accountExists = true;
                        break;
                    }
                }
            } catch (IOException e) {
                Utils.showAlert("IO Error", "An error occurred while reading the userInfo.csv: " + e.getMessage(), Alert.AlertType.ERROR);
                e.printStackTrace();
                return false;
            }
        } else {
            try {
                if (!file_userInfo.createNewFile()) {
                    Utils.showAlert("File Error", "Failed to create the userInfo.csv.", Alert.AlertType.ERROR);
                    return false;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if (accountExists) {
            Utils.showAlert("Account Error", "An account with this name and role already exists.", Alert.AlertType.ERROR);
            return false;
        }

        String csvData_userInfo = String.format("%d,%s,%s,%s,%s,%s,%s,%n",
                accountNumber,
                role,
                name,
                password,
                email,
                " ",
                " ");

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file_userInfo, true))) {
            bw.write(csvData_userInfo);
            bw.flush();
        } catch (IOException e) {
            Utils.showAlert("IO Error", "An error occurred while writing to the userInfo.csv: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
            return false;
        }

        if (role.equals("Child")) {
            writeMoneyInfo(accountNumber);
            return true;
        }

        return true;
    }

    /**
     * Writes initial money information for the account with the specified account number.
     *
     * @param accountNumber The account number for which the money information is written.
     * @return true if the money information is successfully written, false otherwise.
     */
    private boolean writeMoneyInfo(int accountNumber) {
        File file_moneyInfo = new File(moneyInfoFilePath);

        File moneyInfoDir = file_moneyInfo.getParentFile();
        if (!moneyInfoDir.exists() && !moneyInfoDir.mkdirs()) {
            Utils.showAlert("Directory Error", "Failed to create moneyinfo directories.", Alert.AlertType.ERROR);
            return false;
        }

        if (!file_moneyInfo.exists()) {
            try {
                if (!file_moneyInfo.createNewFile()) {
                    Utils.showAlert("File Error", "Failed to create the moneyInfo.csv.", Alert.AlertType.ERROR);
                    return false;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        String csvData_moneyInfo = String.format("%d,%f,%f,%f,%f,%d,%s,%s,%s,%n",
                accountNumber,
                Utils.Initial_Account_Value,
                Utils.Initial_Account_Value,
                Utils.Initial_Account_Value,
                Utils.Initial_Account_Value,
                Utils.Initial_Saving_Period,
                " ",
                " ",
                " ");

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file_moneyInfo, true))) {
            bw.write(csvData_moneyInfo);
            bw.flush();
        } catch (IOException e) {
            Utils.showAlert("IO Error", "An error occurred while writing to the moneyInfo.csv: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
            return false;
        }

        Utils.showAlert("Registration Successful", "Your account has been created successfully!", Alert.AlertType.INFORMATION);
        return true;
    }
}
