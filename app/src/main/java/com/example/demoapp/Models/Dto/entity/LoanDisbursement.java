package com.example.demoapp.Models.Dto.entity;

import com.example.demoapp.Models.Dto.entity.LoanDetail;
import java.io.Serializable;

public class LoanDisbursement implements Serializable {

    int monthlyPaymentDay = 10;
    LoanDetail loanDetail;

    public LoanDisbursement(int monthlyPaymentDay, LoanDetail loanDetail) {
        this.monthlyPaymentDay = monthlyPaymentDay;
        this.loanDetail = loanDetail;
    }

    public int getMonthlyPaymentDay() {
        return monthlyPaymentDay;
    }

    public void setMonthlyPaymentDay(int monthlyPaymentDay) {
        this.monthlyPaymentDay = monthlyPaymentDay;
    }

    public LoanDetail getLoanDetail() {
        return loanDetail;
    }

    public void setLoanDetail(LoanDetail loanDetail) {
        this.loanDetail = loanDetail;
    }
}
