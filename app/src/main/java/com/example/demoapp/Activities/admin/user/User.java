package com.example.demoapp.Activities.admin.user;

import com.example.demoapp.Models.Dto.Response.UserResponse;

public class User {
    private int id;
    private String firstname;
    private String lastName;
    private String otherName;
    private String dob;
    private String gender;
    private String address;
    private String email;
    private String phoneNumber;

    public User(int id, String firstname, String lastName, String otherName, String dob, String gender, String address, String email, String phoneNumber) {
        this.id = id;
        this.firstname = firstname;
        this.lastName = lastName;
        this.otherName = otherName;
        this.dob = dob;
        this.gender = gender;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getOtherName() {
        return otherName;
    }

    public void setOtherName(String otherName) {
        this.otherName = otherName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
