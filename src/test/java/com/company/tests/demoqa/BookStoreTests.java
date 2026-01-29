package com.company.tests.demoqa;

import com.company.framework.assertions.ApiAssertions;
import com.company.framework.clients.BookStoreClient;
import com.company.framework.utils.RetryUtil;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.time.Duration;

public class BookStoreTests {

    private final BookStoreClient bookStoreClient = new BookStoreClient();

    @Test(groups = {"smoke"})
    public void shouldGetAllBooks() {
        Response response = RetryUtil.retry(() -> bookStoreClient.getAllBooks());
        ApiAssertions.assertStatus(response, 200);
        ApiAssertions.assertSchema(response, "schemas/books-schema.json");
    }
}
