package com.company.framework.core;

import com.company.framework.config.ConfigManager;
import com.company.framework.utils.MaskingUtil;

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
                // include masked body in the log so it's not an unused assignment
                log.info("REQUEST {} {} body={}", reqSpec.getMethod(), reqSpec.getURI(), body);
            }
            return ctx.next(reqSpec, resSpec);
        };
    }

    public static Filter responseLogging() {
        return (reqSpec, resSpec, ctx) -> {
            var response = ctx.next(reqSpec, resSpec);
            if (ConfigManager.getBool("log.response")) {
                log.info("RESPONSE status={} body={}", response.getStatusCode(), MaskingUtil.maskSensitive(response.asString()));
            }
            return response;
        };
    }

    public static Filter[] defaultFilters() {
        return new Filter[] { allureFilter(), requestLogging(), responseLogging() };
    }
}
