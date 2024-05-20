package com.example.demoapp.Models.Dto.entity;

import java.io.Serializable;


public class LoanInfo implements Serializable {
    double loanAmount;
    int loanTerm; // input in months
    double interestRate = 12; // Default value: 12% / year

    public LoanInfo(double loanAmount, int loanTerm, double interestRate) {
        this.loanAmount = loanAmount;
        this.loanTerm = loanTerm;
        this.interestRate = interestRate;
    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public int getLoanTerm() {
        return loanTerm;
    }

    public void setLoanTerm(int loanTerm) {
        this.loanTerm = loanTerm;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }
}
