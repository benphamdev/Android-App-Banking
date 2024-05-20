package com.example.demoapp.Models.Dto.Requests;

public class ProvinceRequest {
    private String name;

    public ProvinceRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
