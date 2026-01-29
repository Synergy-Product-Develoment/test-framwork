package com.company.framework.core;

import com.company.framework.config.ConfigManager;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

import static io.restassured.http.ContentType.JSON;

public final class RequestSpecFactory {

    private RequestSpecFactory(){}

    public static RequestSpecification defaultSpec() {
        int timeoutMs = ConfigManager.getInt("timeout.ms");

        return new RequestSpecBuilder()
                .setBaseUri(ConfigManager.get("base.url"))
                .setContentType(JSON)
                .setAccept(JSON)
                // timeouts are set via underlying HTTP client in advanced setup;
                // for demo simplicity, keep this clean
                .build();
    }
}
