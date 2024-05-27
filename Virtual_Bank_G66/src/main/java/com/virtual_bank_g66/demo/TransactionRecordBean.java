package com.virtual_bank_g66.demo;

import javafx.beans.property.SimpleStringProperty;

/**
 * The TransactionRecordBean class represents a single transaction record. It includes details
 * such as the date range, amount, account type, deposit/withdrawal status, and a description
 * of the transaction. Each field is represented as a SimpleStringProperty to facilitate binding
 * in JavaFX applications.
 *
 * @version 1.0 April 10th, 2024 - create getter and setters for properties related to transaction
 * @author Jiabo Tong
 * @author Wantong Zhao
 */
public class TransactionRecordBean {
    private SimpleStringProperty dateRange;
    private SimpleStringProperty amount;
    private SimpleStringProperty accountType;
    private SimpleStringProperty depositWithdrawal;
    private SimpleStringProperty description;

    /**
     * Constructs a TransactionRecordBean with the specified details.
     *
     * @param dateRange the date range of the transaction
     * @param amount the amount involved in the transaction
     * @param accountType the type of account for the transaction
     * @param depositWithdrawal indicates whether it was a deposit or withdrawal
     * @param description a brief description of the transaction
     */
    public TransactionRecordBean(String dateRange, String amount, String accountType, String depositWithdrawal, String description) {
        this.dateRange = new SimpleStringProperty(dateRange);
        this.amount = new SimpleStringProperty(amount);
        this.accountType = new SimpleStringProperty(accountType);
        this.depositWithdrawal = new SimpleStringProperty(depositWithdrawal);
        this.description = new SimpleStringProperty(description);
    }

    /**
     * Gets the date range of the transaction.
     *
     * @return the date range of the transaction
     */
    public String getDateRange() { return dateRange.get(); }

    /**
     * Gets the amount involved in the transaction.
     *
     * @return the amount involved in the transaction
     */
    public String getAmount() { return amount.get(); }

    /**
     * Gets the type of account for the transaction.
     *
     * @return the type of account for the transaction
     */
    public String getAccountType() { return accountType.get(); }

    /**
     * Gets the deposit/withdrawal status of the transaction.
     *
     * @return the deposit/withdrawal status of the transaction
     */
    public String getDepositWithdrawal() { return depositWithdrawal.get(); }

    /**
     * Gets a brief description of the transaction.
     *
     * @return a brief description of the transaction
     */
    public String getDescription() { return description.get(); }

}
