package com.example.demoapp.Models.Dto.Requests;

import com.example.demoapp.Models.Dto.entity.LoanInfo;

public class LoanDetailRequest {
    private Long referenceNumber;
    private int userId;
    private LoanInfo loanInfo;

    public LoanDetailRequest(Long referenceNumber, int userId, LoanInfo loanInfo) {
        this.referenceNumber = referenceNumber;
        this.userId = userId;
        this.loanInfo = loanInfo;
    }

    public Long getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(Long referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LoanInfo getLoanInfo() {
        return loanInfo;
    }

    public void setLoanInfo(LoanInfo loanInfo) {
        this.loanInfo = loanInfo;
    }
}
