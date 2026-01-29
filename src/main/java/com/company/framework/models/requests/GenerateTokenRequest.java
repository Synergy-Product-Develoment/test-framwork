package com.company.framework.models.requests;


public class GenerateTokenRequest {
    public String userName;
    public String password;

    public GenerateTokenRequest() {}

    public GenerateTokenRequest(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
