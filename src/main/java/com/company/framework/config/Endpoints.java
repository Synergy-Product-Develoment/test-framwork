package com.company.framework.config;

public interface Endpoints {
    // DemoQA - keep endpoints version-agnostic; versioning is applied centrally

    interface Authentication {
        String CREATE_USER = "/auth/register";
        String LOGIN = "/auth/login";
        String REGISTER = " /auth/register";
        String USER_DETAILS = "/auth/user";
    }

    interface Projects {
        String CREATE_PROJECT = "/{version}/projects";
        String GET_PROJECT = "/{version}/projects/{projectId}";
        String DELETE_PROJECT = "/{version}/projects/{projectId}";
    }
}
