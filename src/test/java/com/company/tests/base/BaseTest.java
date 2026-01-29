package com.company.tests.base;

import org.testng.annotations.BeforeSuite;

public abstract class BaseTest {

    @BeforeSuite
    public void beforeSuite() {

        // Apply RestAssured filters globally (includes AllureRestAssured and masking util)

    }
}
