package com.company.framework.core;

import com.company.framework.config.ConfigManager;
import com.company.framework.utils.MaskingUtil;

import io.qameta.allure.Allure;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.filter.Filter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class RestAssuredReportingFilters {

    private static final Logger log = LoggerFactory.getLogger(RestAssuredReportingFilters.class);

    private RestAssuredReportingFilters(){}

    public static Filter allureFilter() {
        // Directly return AllureRestAssured; dependency is declared in pom.xml
        return new AllureRestAssured();
    }

    public static Filter requestLogging() {
        return (reqSpec, resSpec, ctx) -> {
            if (ConfigManager.getBool("log.request")) {
                String body = reqSpec.getBody() == null ? null : reqSpec.getBody().toString();
                if (body != null) body = MaskingUtil.maskSensitive(body);
                log.info("REQUEST {} {} body={}", reqSpec.getMethod(), reqSpec.getURI(), body);
                try {
                    StringBuilder curl = new StringBuilder();
                    curl.append(reqSpec.getMethod()).append(" ").append(reqSpec.getURI()).append("\n");
                    // headers
                    reqSpec.getHeaders().asList().forEach(h -> {
                        String name = h.getName();
                        String value = MaskingUtil.maskHeaderIfSensitive(h.getName(), h.getValue());
                        curl.append(name).append(": ").append(value).append("\n");
                    });
                    if (body != null && !body.isEmpty()) {
                        curl.append("\n").append(MaskingUtil.maskSensitive(body));
                    }
                    Allure.addAttachment("curl.txt", curl.toString());
                } catch (Exception ignore) {
                    // do not fail logging
                }
            }
            return ctx.next(reqSpec, resSpec);
        };
    }

    public static Filter responseLogging() {
        return (reqSpec, resSpec, ctx) -> {
            var response = ctx.next(reqSpec, resSpec);
            if (ConfigManager.getBool("log.response")) {
                String respBody = response.asString();
                log.info("RESPONSE status={} body={}", response.getStatusCode(), MaskingUtil.maskSensitive(respBody));
                try {
                    Allure.addAttachment("response-body.txt", MaskingUtil.maskSensitive(respBody));
                    Allure.addAttachment("response-headers.txt", response.getHeaders().toString());
                    Allure.addAttachment("status.txt", String.valueOf(response.getStatusCode()));
                } catch (Exception ignore) {}
            }
            return response;
        };
    }

    public static Filter[] defaultFilters() {
        return new Filter[] { allureFilter(), requestLogging(), responseLogging() };
    }
}
