package com.example.demoapp.Models.Dto.Requests;


public class BranchRequest {
    private String branchName;
    private String address;
    private String provinceName;

    public BranchRequest(String branchName, String address, String provinceName) {
        this.branchName = branchName;
        this.address = address;
        this.provinceName = provinceName;
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

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }
}
