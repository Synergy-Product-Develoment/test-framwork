package com.company.framework.clients;

import com.company.framework.auth.TokenManager;
import com.company.framework.config.Endpoints;
import com.company.framework.core.BaseApiClient;
import com.company.framework.models.requests.AuthRequest;
import com.company.framework.models.requests.RegisterUserRequest;
import io.restassured.response.Response;
import com.company.framework.auth.UserContext;

import java.util.Map;

public class AccountClient extends BaseApiClient {

    public Response createUser(RegisterUserRequest request) {
        RegisterUserRequest req = request;
        

        Response resp = post(Endpoints.CREATE_USER, req);
        // try to capture userId from response and set thread-local UserContext
        try {
            String userId = resp.jsonPath().getString("userId");
            UserContext.set(req.userName, req.password, userId);
        } catch (Exception ignore) {
            UserContext.set(req.userName, req.password);
        }

        return resp;
    }

    public Response generateToken(AuthRequest request) {
        Response response = post(Endpoints.GENERATE_TOKEN, request);
        String token = response.jsonPath().getString("token");
        TokenManager.put(UserContext.getUsername(), token);
        return response;
    }

    public Response getUser(String userId) {

        return get(Endpoints.GET_USER, Map.of("userId", userId), Map.of("Authorization", "Bearer " + TokenManager.get(UserContext.getUsername())));

    }

    public synchronized void refresh() {
       this.generateToken(AuthRequest.builder().email(UserContext.getUsername()).password(UserContext.getPassword()).build());
    }

}
