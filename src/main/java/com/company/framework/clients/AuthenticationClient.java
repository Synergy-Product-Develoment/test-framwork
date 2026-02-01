package com.company.framework.clients;

import com.company.framework.auth.TokenManager;
import com.company.framework.config.ConfigManager;
import com.company.framework.config.Endpoints;
import com.company.framework.core.BaseApiClient;
import com.company.framework.models.requests.AuthRequest;
import com.company.framework.models.requests.RegisterUserRequest;
import io.restassured.response.Response;
import com.company.framework.auth.UserContext;

import java.util.Objects;

import static com.company.framework.config.Endpoints.Authentication.CREATE_USER;
import static com.company.framework.config.Endpoints.Authentication.USER_DETAILS;

public class AuthenticationClient extends BaseApiClient {

    public Response createUser(RegisterUserRequest request) {
        RegisterUserRequest req = request;

        Response resp = post(CREATE_USER, req);
        // try to capture userId from response and set thread-local UserContext
        try {
            String userId = resp.jsonPath().getString("userId");
            UserContext.set(req.userName, req.password, userId, ConfigManager.get("auth.deviceKey"));
        } catch (Exception ignore) {
            UserContext.set(req.userName, req.password, null);
        }

        return resp;
    }

    public Response authenticate(AuthRequest request) {
        UserContext.AuthContext authContext = UserContext.get();
        Response response = post(Endpoints.Authentication.LOGIN, Objects.requireNonNullElse(request, authContext));
        String token = response.jsonPath().getString("accessToken");
        TokenManager.put(authContext.email(), token);
        return response;
    }

    public Response getUser(String userId) {

        return get(USER_DETAILS);

//                Map.of("userId", userId), Map.of("Authorization", "Bearer " + TokenManager.get(UserContext.getUsername())));

    }

    public synchronized void refresh() {
       this.authenticate(AuthRequest.builder().email(UserContext.getUsername()).password(UserContext.getPassword()).build());
    }

}
