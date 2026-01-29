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
        return given().when().get(buildPath(path));
    }

    protected Response post(String path, Object body) {
        return given().body(body).when().post(buildPath(path));
    }

    protected Response delete(String path) {
        return given().when().delete(buildPath(path));
    }

    protected Response put(String path, Object body) {
        return given().body(body).when().put(buildPath(path));
    }

    private String buildPath(String path) {
        String strategy = com.company.framework.config.ConfigManager.get("api.version.strategy", "PATH");
        String version = com.company.framework.config.ConfigManager.get("api.version", "v1");
        if ("PATH".equalsIgnoreCase(strategy)) {
            // ensure single leading slash
            String p = path.startsWith("/") ? path : "/" + path;
            return "/" + version + p;
        }
        return path;
    }
}
