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



//
//    @Override
//    public Response filter(FilterableRequestSpecification req,
//                           FilterableResponseSpecification resSpec,
//                           FilterContext ctx) {
//
//        Response response = ctx.next(req, resSpec);
//
//        if (!isAuthFailure(response)) return response;
//
//        // Retry once only
//        Integer retryCount = (Integer) req.getAttribute("authRetryCount");
//        if (retryCount == null) retryCount = 0;
//        if (retryCount >= MAX_RETRY) return response;
//
//        req.setAttribute("authRetryCount", retryCount + 1);
//
//        // refresh token
//        refreshToken();
//
//        // attach new token header (or whatever your auth header is)
//        String token = TokenManager.get(getDefaultUser());
//        if (token != null && !token.isBlank()) {
//            req.removeHeaders("Authorization");
//            req.header("Authorization", "Bearer " + token);
//        }
//
//        Allure.step("Auth failed (" + response.statusCode() + "). Refreshed token and retrying request...");
//
//        return ctx.next(req, resSpec);
//    }
//
//    private boolean isAuthFailure(Response response) {
//        return response != null && (response.statusCode() == 401 || response.statusCode() == 403);
//    }
//
//    private void refreshToken() {
//        // call your existing auth client
//        // example:
//        // new AccountClient().generateToken(...) then TokenManager.put(user, token)
//
//        AuthBootstrap.ensureTokenAvailable();
//    }

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
        AuthContext authContextUser = TokenManager.get(getDefaultUser());
        if(!Objects.isNull(authContextUser)) {
            Allure.step("Auth failed (" + response.statusCode() + "). Autheticating token and retrying request...");
            authClient.generateToken(new GenerateTokenRequest(authContextUser.username(), authContextUser.password()));
        AuthContext authContext = TokenManager.get(getDefaultUser());
        if (authContext != null && !authContext.token().isBlank()) {
            requestSpec.removeHeader("Authorization");
            requestSpec.header("Authorization", "Bearer " + authContext.token());
        }
        }
        return  ctx.next(requestSpec, responseSpec);
    }

    private boolean isAuthFailure(Response response) {
        return response != null && (response.statusCode() == 401 || response.statusCode() == 403);
    }
}
