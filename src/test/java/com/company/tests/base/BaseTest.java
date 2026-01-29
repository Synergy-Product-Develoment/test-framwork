package com.company.tests.base;

import com.company.framework.auth.AuthBootstrap;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterMethod;

import com.company.framework.utils.IdempotencyKeyUtil;

public abstract class BaseTest {

    @BeforeSuite
    public void beforeSuite() {
        //health check or any global setup can be done here
        //write health check if fails skip all tests
        //Db connections can be setup here
        
    }

    @BeforeMethod
    public void beforeMethod() {
        AuthBootstrap.init();
    }
    
    @AfterMethod
    public void afterMethod() {
        // clear thread-local idempotency key between tests
        IdempotencyKeyUtil.clear();
        // clear thread-local user context
        com.company.framework.auth.UserContext.clear();
    }

    @AfterSuite
    public void afterSuite() {
        AuthBootstrap.clear();
    }
}
