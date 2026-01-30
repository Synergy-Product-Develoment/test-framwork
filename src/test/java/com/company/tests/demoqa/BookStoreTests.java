package com.company.tests.demoqa;

import com.company.framework.assertions.ApiAssertions;
import com.company.framework.clients.BookStoreClient;
import com.company.framework.utils.RetryUtil;
import com.company.tests.base.BaseTest;
import com.company.tests.dataproviders.DataProviders;

import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Map;

public class BookStoreTests extends BaseTest {

    private final BookStoreClient bookStoreClient = new BookStoreClient();

   // @Test(groups = {"smoke"})
    @Test(dataProvider = "json", dataProviderClass = DataProviders.class, groups = {"smoke"})
    public void shouldGetAllBooks(Map<String, Object> data) {
        int expected = ((Number) data.getOrDefault("expectedStatus", 200)).intValue();
        Response response = RetryUtil.retry(() -> bookStoreClient.getAllBooks());
        ApiAssertions.assertStatus(response, expected);
        ApiAssertions.assertSchema(response, "schemas/books-schema.json");
    }
}
