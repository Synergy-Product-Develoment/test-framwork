package com.company.framework.models.requests;

public class RegisterUserRequest {
    public String userName;
    public String password;

    public RegisterUserRequest() {}

    public RegisterUserRequest(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
}
