package com.example.demoapp.Models.Dto.entity;

import com.example.demoapp.Activities.admin.user.User;
import com.example.demoapp.Models.Dto.Requests.UserCreationRequest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LoanDetail implements Serializable {

    private Integer id;
    Enums.LoanStatus loanStatus = Enums.LoanStatus.PENDING;
    Enums.LoanPaymentStatus loanPaymentStatus = Enums.LoanPaymentStatus.UNPAID;
    String referenceNumber;
    private User user;
    private LoanInfo loanInfo;
//    private List<LoanDisbursement> loanDisbursements;
//    private List<LoanPayment> loanPayments;


    public LoanDetail(Integer id, Enums.LoanStatus loanStatus, Enums.LoanPaymentStatus loanPaymentStatus, String referenceNumber, User user, LoanInfo loanInfo) {
        this.id = id;
        this.loanStatus = loanStatus;
        this.loanPaymentStatus = loanPaymentStatus;
        this.referenceNumber = referenceNumber;
        this.user = user;
        this.loanInfo = loanInfo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Enums.LoanStatus getLoanStatus() {
        return loanStatus;
    }

    public void setLoanStatus(Enums.LoanStatus loanStatus) {
        this.loanStatus = loanStatus;
    }

    public Enums.LoanPaymentStatus getLoanPaymentStatus() {
        return loanPaymentStatus;
    }

    public void setLoanPaymentStatus(Enums.LoanPaymentStatus loanPaymentStatus) {
        this.loanPaymentStatus = loanPaymentStatus;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LoanInfo getLoanInfo() {
        return loanInfo;
    }

    public void setLoanInfo(LoanInfo loanInfo) {
        this.loanInfo = loanInfo;
    }
}
