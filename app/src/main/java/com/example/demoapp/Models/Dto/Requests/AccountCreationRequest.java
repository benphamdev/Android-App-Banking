package com.example.demoapp.Models.Dto.Requests;

import java.io.Serializable;

public class AccountCreationRequest implements Serializable {
    private int userId;
    private int branchInfoId;
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
