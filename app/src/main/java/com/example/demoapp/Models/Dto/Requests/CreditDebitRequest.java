package com.example.demoapp.Models.Dto.Requests;

import java.math.BigDecimal;

public class CreditDebitRequest {
    String accountNumber;
    BigDecimal amount;

    public CreditDebitRequest(String accountNumber, BigDecimal amount) {
        this.accountNumber = accountNumber;
        this.amount = amount;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
