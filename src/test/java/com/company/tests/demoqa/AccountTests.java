package com.company.tests.demoqa;

import com.company.framework.assertions.ApiAssertions;
import com.company.framework.clients.AccountClient;
import com.company.framework.models.requests.CreateUserRequest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.UUID;

public class AccountTests {

    private final AccountClient accountClient = new AccountClient();

    @Test
    public void shouldCreateUser() {
        String username = "user_" + UUID.randomUUID();
        String password = "Test@12345";

        Response response = accountClient.createUser(new CreateUserRequest(username, password));

        ApiAssertions.assertStatus(response, 201);

        String userId = response.jsonPath().getString("userID");
        ApiAssertions.assertNotNull(userId, "userID should not be null");
    }
}
