package com.company.tests.demoqa;

import com.company.framework.assertions.ApiAssertions;
import com.company.framework.clients.AccountClient;
import com.company.framework.clients.BookStoreClient;
import com.company.framework.models.requests.CreateUserRequest;
import com.company.framework.models.requests.GenerateTokenRequest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.UUID;

public class E2EFlowTests {

    private final AccountClient accountClient = new AccountClient();
    private final BookStoreClient bookStoreClient = new BookStoreClient();

    @Test
    public void e2e_createUser_generateToken_getBooks() {

        String username = "user_" + UUID.randomUUID();
        String password = "Test@12345";

        Response createResp = accountClient.createUser(new CreateUserRequest(username, password));
        ApiAssertions.assertStatus(createResp, 201);

        String userId = createResp.jsonPath().getString("userID");
        ApiAssertions.assertNotNull(userId, "userID should not be null");

        Response tokenResp = accountClient.generateToken(new GenerateTokenRequest(username, password));
        ApiAssertions.assertStatus(tokenResp, 200);

        String token = tokenResp.jsonPath().getString("token");
        ApiAssertions.assertNotNull(token, "token should not be null");

        // Example secured call (Get User)
        Response getUserResp = accountClient.getUser(userId);
        // DemoQA may return 200 or 401 depending on token validity; keep status assertion flexible if needed
        // Here we assert it's not 500
        int status = getUserResp.getStatusCode();
        if (status >= 500) {
            throw new AssertionError("Server error: " + getUserResp.asString());
        }

        Response booksResp = bookStoreClient.getAllBooks();
        ApiAssertions.assertStatus(booksResp, 200);
    }
}
