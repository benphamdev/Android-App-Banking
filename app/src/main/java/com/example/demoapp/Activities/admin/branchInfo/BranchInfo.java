package com.example.demoapp.Activities.admin.branchInfo;

import com.example.demoapp.Models.Dto.Response.AccountInfoResponse;
import com.example.demoapp.Models.Dto.Response.ProvinceResponse;

import java.util.List;

public class BranchInfo {
    private String id;
    private String branchName;
    private String address;
    private ProvinceResponse provinceName;
    private List<AccountInfoResponse> accountInfoResponse;

    public BranchInfo(
            String id, String branchName, String address, ProvinceResponse provinceName,
            List<AccountInfoResponse> accountInfoResponse
    ) {
        this.id = id;
        this.branchName = branchName;
        this.address = address;
        this.provinceName = provinceName;
        this.accountInfoResponse = accountInfoResponse;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public ProvinceResponse getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(ProvinceResponse provinceName) {
        this.provinceName = provinceName;
    }

    public List<AccountInfoResponse> getAccountInfoResponse() {
        return accountInfoResponse;
    }

    public void setAccountInfoResponse(List<AccountInfoResponse> accountInfoResponse) {
        this.accountInfoResponse = accountInfoResponse;
    }
}
