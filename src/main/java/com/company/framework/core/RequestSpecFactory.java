package com.company.framework.core;

import com.company.framework.auth.TokenManager;
import com.company.framework.auth.UserContext;
import com.company.framework.config.ConfigManager;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.lang3.StringUtils;

import static io.restassured.http.ContentType.JSON;

public final class RequestSpecFactory {

    private RequestSpecFactory(){}

    public static RequestSpecification defaultSpec() {
        int timeoutMs = ConfigManager.getInt("timeout.ms");

        RequestSpecBuilder builder = new RequestSpecBuilder()
                .setBaseUri(ConfigManager.get("base.url"))
                .setContentType(JSON)
                .setAccept(JSON);

        builder.addHeader("X-Device-ID", UserContext.get().deviceId());
        if(StringUtils.isNotBlank(TokenManager.get(UserContext.getUsername()))){
            builder.addHeader("Authorization", "Bearer " + TokenManager.get(UserContext.getUsername()));
        }
        // Header-based API versioning
        String versionStrategy = ConfigManager.get("api.version.strategy", "PATH");
        String apiVersion = ConfigManager.get("api.version", "v1");
        if ("HEADER".equalsIgnoreCase(versionStrategy)) {
            builder.addHeader("X-API-Version", apiVersion);
        }

        // Idempotency header support
        if (ConfigManager.getBool("idempotency.enabled")) {
            String idempotencyHeader = ConfigManager.get("idempotency.header", "Idempotency-Key");
            String key = com.company.framework.utils.IdempotencyKeyUtil.getCurrent();
            if (key == null) key = com.company.framework.utils.IdempotencyKeyUtil.newKey();
            builder.addHeader(idempotencyHeader, key);
        }

        return builder.build();
    }
}
