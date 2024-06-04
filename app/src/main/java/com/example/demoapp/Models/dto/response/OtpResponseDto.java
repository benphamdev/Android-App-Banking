package com.example.demoapp.Models.dto.response;

public class OtpResponseDto {
    //    private Enums.OtpStatus status;
    private String message;

    public OtpResponseDto(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
