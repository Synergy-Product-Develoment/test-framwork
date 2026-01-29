package com.company.framework.clients;

import com.company.framework.core.BaseApiClient;
import io.restassured.response.Response;

public class UserClient extends BaseApiClient {

    public Response createUser(Object body) {
        return post("/api/users", body);
    }

    public Response getUser(String userId) {
        return get("/api/users/" + userId);
    }

    public Response deleteUser(String userId) {
        return delete("/api/users/" + userId);
    }
}
