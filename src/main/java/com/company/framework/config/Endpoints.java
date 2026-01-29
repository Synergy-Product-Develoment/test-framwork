package com.company.framework.config;

public final class Endpoints {
    private Endpoints(){}

    // DemoQA
    public static final String GENERATE_TOKEN = "/Account/v1/GenerateToken";
    public static final String CREATE_USER    = "/Account/v1/User";
    public static final String GET_USER       = "/Account/v1/User/{userId}";
    public static final String BOOKS          = "/BookStore/v1/Books";
}
