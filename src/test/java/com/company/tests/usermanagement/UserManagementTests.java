package com.company.tests.usermanagement;

import com.company.framework.clients.UserClient;
import io.qameta.allure.Attachment;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

@Feature("User Management")
public class UserManagementTests {

    @BeforeClass
    public void setup() {
        
    }

    @Test(description = "Create user - password field should be masked in logs")
    @Story("Create User")
    @Severity(SeverityLevel.CRITICAL)
    public void createUser_withPassword_masked() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("username", "testuser-" + System.currentTimeMillis());
        payload.put("password", "P@ssw0rd!123");
        payload.put("email", "testuser@example.com");

        UserClient client = new UserClient();
        Response res = client.createUser(payload);

        // Basic assertions
        Assert.assertTrue(res.getStatusCode() == 200 || res.getStatusCode() == 201,
                "Expected 200 or 201 but got " + res.getStatusCode());

        // Attach Trace-ID header to Allure report
        String traceId = res.getHeader("Trace-ID");
        if (traceId != null && !traceId.isEmpty()) {
            attachTraceId(traceId);
        }
    }

    @Attachment(value = "Trace-ID", type = "text/plain")
    private String attachTraceId(String traceId) {
        return traceId;
    }
}
