package com.company.framework.models.requests;

public class CreateUserRequest {
    public String userName;
    public String password;

    public CreateUserRequest() {}

    public CreateUserRequest(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
}
