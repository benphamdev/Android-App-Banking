package com.example.demoapp.Activities.admin.accounts;

import java.math.BigDecimal;

public class AccountInfoResponse {
    private String id;
    private String accountName;
    private BigDecimal accountBalance;
    private String accountNumber;
    private String branchName;

    public AccountInfoResponse(
            String id, String accountName, BigDecimal accountBalance, String accountNumber,
            String branchName
    ) {
        this.id = id;
        this.accountName = accountName;
        this.accountBalance = accountBalance;
        this.accountNumber = accountNumber;
        this.branchName = branchName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(BigDecimal accountBalance) {
        this.accountBalance = accountBalance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }
}
