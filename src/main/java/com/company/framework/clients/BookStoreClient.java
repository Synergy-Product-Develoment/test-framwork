package com.company.framework.clients;

import com.company.framework.config.Endpoints;
import com.company.framework.core.BaseApiClient;
import io.restassured.response.Response;

public class BookStoreClient extends BaseApiClient {

    public Response getAllBooks() {
        return get(Endpoints.BOOKS);
    }
}
