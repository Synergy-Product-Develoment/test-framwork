package com.company.tests.base;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterMethod;

import com.company.framework.utils.IdempotencyKeyUtil;

public abstract class BaseTest {

    @BeforeSuite
    public void beforeSuite() {

        // Apply RestAssured filters globally (includes AllureRestAssured and masking util)

    }

    @AfterMethod
    public void afterMethod() {
        // clear thread-local idempotency key between tests
        IdempotencyKeyUtil.clear();
    }
}
