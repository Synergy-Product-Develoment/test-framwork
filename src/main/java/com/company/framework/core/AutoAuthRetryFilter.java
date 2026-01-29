package com.company.framework.core;

import com.company.framework.auth.AuthContext;
import com.company.framework.auth.TokenManager;
import com.company.framework.clients.AccountClient;
import com.company.framework.config.ConfigManager;
import com.company.framework.models.requests.CreateUserRequest;
import com.company.framework.models.requests.GenerateTokenRequest;
import io.qameta.allure.Allure;
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

import java.util.Objects;
import java.util.UUID;

public class AutoAuthRetryFilter implements Filter {

    private static final int MAX_RETRY = 1;

    AccountClient authClient = new AccountClient();
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
        AuthContext authContextUser = TokenManager.current();

        Allure.step("Auth failed (" + response.statusCode() + "). Autheticating token and retrying request...");
        authClient.generateToken(new GenerateTokenRequest(authContextUser.username(), authContextUser.password()));
        AuthContext authContext = TokenManager.get(getDefaultUser());
        if (authContext != null && !authContext.token().isBlank()) {
            requestSpec.removeHeader("Authorization");
            requestSpec.header("Authorization", "Bearer " + authContext.token());
        }

        return  ctx.next(requestSpec, responseSpec);
    }

    private boolean isAuthFailure(Response response) {
        return response != null && (response.statusCode() == 401 || response.statusCode() == 403);
    }
}
