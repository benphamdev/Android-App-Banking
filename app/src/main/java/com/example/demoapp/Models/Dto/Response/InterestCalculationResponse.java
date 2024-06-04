package com.example.demoapp.Models.Dto.Response;

public class InterestCalculationResponse {
    private double interestAmountMonthly;
    private double interestAmount;
    private double totalAmount;

    public InterestCalculationResponse(double interestAmountMonthly, double interestAmount) {
        this.interestAmountMonthly = interestAmountMonthly;
        this.interestAmount = interestAmount;
        this.totalAmount = interestAmountMonthly + interestAmount;
    }

    public InterestCalculationResponse(
            double interestAmountMonthly, double interestAmount, double totalAmount
    ) {
        this.interestAmountMonthly = interestAmountMonthly;
        this.interestAmount = interestAmount;
        this.totalAmount = totalAmount;
    }

    public double getInterestAmountMonthly() {
        return interestAmountMonthly;
    }

    public void setInterestAmountMonthly(double interestAmountMonthly) {
        this.interestAmountMonthly = interestAmountMonthly;
    }

    public double getInterestAmount() {
        return interestAmount;
    }

    public void setInterestAmount(double interestAmount) {
        this.interestAmount = interestAmount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
