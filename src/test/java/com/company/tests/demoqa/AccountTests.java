package com.company.tests.demoqa;

import com.company.framework.assertions.ApiAssertions;
import com.company.framework.auth.AuthContext;
import com.company.framework.auth.TokenManager;
import com.company.framework.clients.AccountClient;
import com.company.framework.models.requests.CreateUserRequest;
import com.company.tests.base.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.UUID;

public class AccountTests extends BaseTest {

    private final AccountClient accountClient = new AccountClient();

    @Test
    public void shouldCreateUser() {
        AuthContext ctx = TokenManager.current();

        String username = ctx.username();
        String password = ctx.password();

        Response response = accountClient.createUser(new CreateUserRequest(username, password));

        ApiAssertions.assertStatus(response, 201);

        String userId = response.jsonPath().getString("userID");
        ApiAssertions.assertNotNull(userId, "userID should not be null");
    }
}
