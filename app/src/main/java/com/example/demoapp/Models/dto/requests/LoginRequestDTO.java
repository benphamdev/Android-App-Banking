package com.example.demoapp.Models.dto.requests;

public class LoginRequestDTO {

    private final String email;

    private final String password;

    public LoginRequestDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
