package com.virtual_bank_g66.demo;

import javafx.beans.property.SimpleStringProperty;

public class TransactionRecordBean {
    private SimpleStringProperty dateRange;
    private SimpleStringProperty amount;
    private SimpleStringProperty accountType;
    private SimpleStringProperty depositWithdrawal;
    private SimpleStringProperty description;

    public TransactionRecordBean(String dateRange, String amount, String accountType, String depositWithdrawal, String description) {
        this.dateRange = new SimpleStringProperty(dateRange);
        this.amount = new SimpleStringProperty(amount);
        this.accountType = new SimpleStringProperty(accountType);
        this.depositWithdrawal = new SimpleStringProperty(depositWithdrawal);
        this.description = new SimpleStringProperty(description);
    }

    public String getDateRange() { return dateRange.get(); }

    public String getAmount() { return amount.get(); }

    public String getAccountType() { return accountType.get(); }

    public String getDepositWithdrawal() { return depositWithdrawal.get(); }

    public String getDescription() { return description.get(); }

}
