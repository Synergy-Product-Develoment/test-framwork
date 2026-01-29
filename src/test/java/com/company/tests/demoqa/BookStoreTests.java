package com.company.tests.demoqa;

import com.company.framework.assertions.ApiAssertions;
import com.company.framework.clients.BookStoreClient;
import com.company.framework.utils.RetryUtil;
import com.company.tests.base.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.time.Duration;

public class BookStoreTests extends BaseTest {

    private final BookStoreClient bookStoreClient = new BookStoreClient();

    @Test(groups = {"smoke"})
    public void shouldGetAllBooks() {
        Response response = RetryUtil.retry(() -> bookStoreClient.getAllBooks());
        ApiAssertions.assertStatus(response, 200);
        ApiAssertions.assertSchema(response, "schemas/books-schema.json");
    }
}
