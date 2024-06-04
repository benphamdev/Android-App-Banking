package com.example.demoapp.Models.dto.requests;

public class UserCreationRequest {
    private final String firstName;
    private final String lastName;
    private final String otherName;
    private final String dob;

    private final String gender;

    private final String address;

    private final String email;

    private final String phoneNumber;

    private final String password;

    public UserCreationRequest(
            String firstName, String lastName, String otherName, String dob, String gender,
            String address, String email, String phoneNumber, String password
    ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.otherName = otherName;
        this.dob = dob;
        this.gender = gender;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getOtherName() {
        return otherName;
    }

    public String getDob() {
        return dob;
    }

    public String getGender() {
        return gender;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }
}
