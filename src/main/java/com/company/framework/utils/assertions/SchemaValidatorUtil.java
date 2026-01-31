package com.company.framework.utils.assertions;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

import java.io.InputStream;

public final class SchemaValidatorUtil {

    private SchemaValidatorUtil(){}

    public static void validateSchema(Response response, String schemaPathInResources) {
        InputStream is = SchemaValidatorUtil.class.getClassLoader().getResourceAsStream(schemaPathInResources);
        if (is == null) throw new RuntimeException("Schema not found in resources: " + schemaPathInResources);
        response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(is));

        //matchesJsonSchemaInClasspath("schemas/books-schema.json");
    }
}
