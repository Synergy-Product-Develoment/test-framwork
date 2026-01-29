package com.company.tests.demoqa;

import com.company.framework.assertions.ApiAssertions;
import com.company.framework.clients.BookStoreClient;
import com.company.framework.utils.RetryUtil;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.time.Duration;

public class BookStoreTests {

    private final BookStoreClient bookStoreClient = new BookStoreClient();
    @Test
    public void shouldGetAllBooks() {
        Response response = RetryUtil.retry(2, Duration.ofSeconds(1), () -> bookStoreClient.getAllBooks());
        ApiAssertions.assertStatus(response, 200);
        ApiAssertions.assertSchema(response, "schemas/books-schema.json");
    }
}
