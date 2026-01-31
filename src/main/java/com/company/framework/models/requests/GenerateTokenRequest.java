package com.company.framework.models.requests;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GenerateTokenRequest {
    public String userName;
    public String password;

    public GenerateTokenRequest() {}

    public GenerateTokenRequest(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
