package com.example.demoapp.Models.Dto.Response;

public class AuthenticationResponse {
    private boolean authenticated;
    private String token;

    public AuthenticationResponse(boolean authenticated, String token) {
        this.authenticated = authenticated;
        this.token = token;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
