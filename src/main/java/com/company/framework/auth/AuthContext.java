package com.company.framework.auth;

public class AuthContext {
    private final String username;
    private final String password;
    private final String token;

    public AuthContext(String username, String password, String token) {
        this.username = username;
        this.password = password;
        this.token = token;
    }

    public String username() { return username; }
    public String password() { return password; }
    public String token() { return token; }
}
