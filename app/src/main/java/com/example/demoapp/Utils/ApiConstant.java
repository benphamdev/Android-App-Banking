package com.example.demoapp.Utils;

public interface ApiConstant {
    String SERVER_PORT = "8081";
    String SERVER_IP = "192.168.1.26";
    String BASE_URL = "http://" + SERVER_IP + ":" + SERVER_PORT + "/api/v1/";
    String SIGNUP = BASE_URL + "user/signup";
}
