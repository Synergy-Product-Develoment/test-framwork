package com.company.tests.base;

import com.company.framework.auth.AuthBootstrap;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterMethod;

import com.company.framework.utils.IdempotencyKeyUtil;

public abstract class BaseTest {

    @BeforeSuite
    public void beforeSuite() {
        AuthBootstrap.init();
    }

    @AfterMethod
    public void afterMethod() {
        // clear thread-local idempotency key between tests
        IdempotencyKeyUtil.clear();
    }

    @AfterSuite
    public void afterSuite() {
        AuthBootstrap.clear();
    }
}
