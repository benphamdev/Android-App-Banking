package com.example.demoapp.Models.Dto.entity;

import java.io.Serializable;

public class LoanPayment implements Serializable {
    double payAmount;
    String paymentReference;
    String paymentType;
    LoanDetail loanDetail;

    public LoanPayment(double payAmount, String paymentReference, String paymentType, LoanDetail loanDetail) {
        this.payAmount = payAmount;
        this.paymentReference = paymentReference;
        this.paymentType = paymentType;
        this.loanDetail = loanDetail;
    }

    public double getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(double payAmount) {
        this.payAmount = payAmount;
    }

    public String getPaymentReference() {
        return paymentReference;
    }

    public void setPaymentReference(String paymentReference) {
        this.paymentReference = paymentReference;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public LoanDetail getLoanDetail() {
        return loanDetail;
    }

    public void setLoanDetail(LoanDetail loanDetail) {
        this.loanDetail = loanDetail;
    }
}
