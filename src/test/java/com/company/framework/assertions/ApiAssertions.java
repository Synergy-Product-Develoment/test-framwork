package com.company.framework.assertions;

import com.company.framework.utils.assertions.SchemaValidatorUtil;
import io.restassured.response.Response;
import org.testng.Assert;

public final class ApiAssertions {

    private ApiAssertions(){}

    public static void assertStatus(Response response, int expected) {
        Assert.assertEquals(response.getStatusCode(), expected,
                "Unexpected status. Body: " + response.asString());
    }

    public static void assertNotNull(Object value, String message) {
        Assert.assertNotNull(value, message);
    }

    //assert schema validation
    public static void assertSchema(Response response, String schemaPathInResources) {
        SchemaValidatorUtil.validateSchema(response, schemaPathInResources);
    }

}