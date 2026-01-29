package com.company.framework.clients;

import com.company.framework.config.Endpoints;
import com.company.framework.core.BaseApiClient;
import com.company.framework.models.requests.CreateUserRequest;
import com.company.framework.models.requests.GenerateTokenRequest;
import io.restassured.response.Response;

import java.util.Map;

public class AccountClient extends BaseApiClient {

    public Response createUser(CreateUserRequest request) {
        return post(Endpoints.CREATE_USER, request);
    }

    public Response generateToken(GenerateTokenRequest request) {
        return post(Endpoints.GENERATE_TOKEN, request);
    }

    public Response getUser(String userId, String token) {

        return get(Endpoints.GET_USER, Map.of("userId", userId), Map.of("Authorization", "Bearer " + token));

    }

}
