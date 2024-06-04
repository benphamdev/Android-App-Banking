package com.example.demoapp.Models.dto.requests;

import java.io.Serializable;

public class AccountCreationRequest implements Serializable {
    private final int userId;
    private final int branchInfoId;
//    Enums.AccountType accountType;

    public AccountCreationRequest(int userId, int branchInfoId) {
        this.userId = userId;
        this.branchInfoId = branchInfoId;
    }

    public int getUserId() {
        return userId;
    }

    public int getBranchInfoId() {
        return branchInfoId;
    }
}
