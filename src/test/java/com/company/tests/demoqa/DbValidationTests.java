package com.company.tests.demoqa;

import com.company.framework.clients.UserClient;
import com.company.framework.db.DbManager;
import com.company.framework.db.DbClient;
import com.company.framework.utils.IdempotencyKeyUtil;
import io.restassured.response.Response;
import org.testng.SkipException;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertTrue;

public class DbValidationTests {

    @Test(groups = {"regression"})
    public void createUserAndValidateDb() throws Exception {
        UserClient client = new UserClient();

        String username = "test_user_" + System.currentTimeMillis();
        var body = java.util.Map.of("username", username, "password", "P@ssw0rd");

        Response resp = client.createUser(body);
        int status = resp.getStatusCode();

        // If DB not enabled, skip DB assertions
        if (!com.company.framework.config.ConfigManager.getBool("db.enabled")) {
            throw new SkipException("DB not enabled - skipping DB validation");
        }

        DbManager mgr = DbManager.getInstance();
        DbClient db = mgr.client();
        if (db == null) throw new SkipException("DB client not available");

        List<Map<String, Object>> rows = db.query("SELECT * FROM users WHERE username = ?", username);
        assertTrue(rows.size() > 0, "Expected DB to contain created user");
    }
}
