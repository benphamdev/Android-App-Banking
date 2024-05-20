package com.example.demoapp.Models.Dto.Response;

import android.accounts.Account;

import java.util.List;

public class BranchResponse {
    private Integer id;
    private String branchName;
    private String address;
    private ProvinceResponse province;
    private List<Account> accounts;

    public BranchResponse(
            Integer id, String branchName, String address, ProvinceResponse province,
            List<Account> accounts
    ) {
        this.id = id;
        this.branchName = branchName;
        this.address = address;
        this.province = province;
        this.accounts = accounts;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ProvinceResponse getProvince() {
        return province;
    }

    public void setProvince(ProvinceResponse province) {
        this.province = province;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
}
