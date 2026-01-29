package com.company.framework.core;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.Arrays;

public abstract class BaseApiClient {

    protected RequestSpecification given() {
        return RestAssured.given()
                .spec(RequestSpecFactory.defaultSpec())
                .filters(Arrays.asList(RestAssuredReportingFilters.defaultFilters()));
    }

    protected Response get(String path) {
        return given().when().get(path);
    }

    protected Response post(String path, Object body) {
        return given().body(body).when().post(path);
    }

    protected Response delete(String path) {
        return given().when().delete(path);
    }

    protected Response put(String path, Object body) {
        return given().body(body).when().put(path);
    }
}
