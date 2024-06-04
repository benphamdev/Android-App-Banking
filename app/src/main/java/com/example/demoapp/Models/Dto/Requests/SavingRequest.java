package com.example.demoapp.Models.Dto.Requests;

import java.io.Serializable;
import java.math.BigDecimal;

public class SavingRequest implements Serializable {
    private int userId;
    private Long amount;
    private int duration;

    public SavingRequest(int userId, Long amount, int duration) {
        this.userId = userId;
        this.amount = amount;
        this.duration = duration;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
