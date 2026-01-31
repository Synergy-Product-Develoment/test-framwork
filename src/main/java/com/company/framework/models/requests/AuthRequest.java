package com.company.framework.models.requests;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthRequest {
    public String email;
    public String password;
}
