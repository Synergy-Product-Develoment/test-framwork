package com.company.framework.core;

import com.company.framework.config.ConfigManager;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.Arrays;
import java.util.Map;

public abstract class BaseApiClient {

    protected RequestSpecification given() {
        return RestAssured.given()
                .spec(RequestSpecFactory.defaultSpec())
                .filters(Arrays.asList(RestAssuredReportingFilters.defaultFilters()))
                .filter(new AutoAuthRetryFilter());
    }

    protected Response get(String path) {
        return given().when().get(buildPath(path));
    }

    //get with path params reusable key sould be first arg and value showbbe secnf
    protected Response get(String path, Map<String, Object> pathParams, Map<String, Object> headers) {
        return given()
                .headers(headers)
                .pathParams(pathParams)
                .when()
                .get(buildPath(path));
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
        String strategy = ConfigManager.get("api.version.strategy", "PATH");
        String version = ConfigManager.get("api.version", "v1");

        if ("PATH".equalsIgnoreCase(strategy)) {
            return path.replace("{version}", version);
        }
        return path;
    }

}
