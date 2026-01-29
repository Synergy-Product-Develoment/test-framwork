package com.company.framework.config;

public final class Endpoints {
    private Endpoints(){}
    // DemoQA - keep endpoints version-agnostic; versioning is applied centrally
    public static final String GENERATE_TOKEN = "/Account/GenerateToken";
    public static final String CREATE_USER    = "/Account/User";
    public static final String GET_USER       = "/Account/User/{userId}";
    public static final String BOOKS          = "/BookStore/Books";
}
