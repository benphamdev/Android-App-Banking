package com.example.demoapp.Models.Dto.entity;

import com.example.demoapp.Activities.admin.user.User;
import com.example.demoapp.Models.Dto.Response.UserResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Saving {
    private int id;
    String maturationDate;
    BigDecimal baseAmount;
    BigDecimal refundAmount;
    int duration;
    boolean statusRefund = false;
    User user;

    public Saving(int id, String maturationDate, BigDecimal baseAmount, BigDecimal refundAmount, int duration, boolean statusRefund, User user) {
        this.id = id;
        this.maturationDate = maturationDate;
        this.baseAmount = baseAmount;
        this.refundAmount = refundAmount;
        this.duration = duration;
        this.statusRefund = statusRefund;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaturationDate() {
        return maturationDate;
    }

    public void setMaturationDate(String maturationDate) {
        this.maturationDate = maturationDate;
    }

    public BigDecimal getBaseAmount() {
        return baseAmount;
    }

    public void setBaseAmount(BigDecimal baseAmount) {
        this.baseAmount = baseAmount;
    }

    public BigDecimal getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(BigDecimal refundAmount) {
        this.refundAmount = refundAmount;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean isStatusRefund() {
        return statusRefund;
    }

    public void setStatusRefund(boolean statusRefund) {
        this.statusRefund = statusRefund;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
