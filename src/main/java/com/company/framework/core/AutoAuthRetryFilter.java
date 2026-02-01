package com.company.framework.core;

import com.company.framework.auth.TokenManager;
import com.company.framework.auth.UserContext;
import com.company.framework.clients.AuthenticationClient;
import com.company.framework.config.ConfigManager;
import io.qameta.allure.Allure;
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

public class AutoAuthRetryFilter implements Filter {

    private static final int MAX_RETRY = 1;

    AuthenticationClient authClient = new AuthenticationClient();
    private String getDefaultUser() {
        return ConfigManager.get("auth.default.user", "default");
    }

    @Override
    public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec, FilterContext ctx) {
        Response response = ctx.next(requestSpec, responseSpec);
        if (!isAuthFailure(response)) {
            return response;
        }
        // Retry once only
        Allure.step("Auth failed (" + response.statusCode() + "). Autheticating token and retrying request...");
        requestSpec.removeHeader("Authorization");
        authClient.refresh();
        requestSpec.header("Authorization", "Bearer " + TokenManager.get(UserContext.getUsername()));
        return  ctx.next(requestSpec, responseSpec);
    }

    private boolean isAuthFailure(Response response) {
        return response != null && (response.statusCode() == 401 || response.statusCode() == 403);
    }
}
