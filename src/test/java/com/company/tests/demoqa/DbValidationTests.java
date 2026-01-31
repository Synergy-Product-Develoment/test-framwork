package com.company.tests.demoqa;

import com.company.framework.auth.UserContext;
import com.company.framework.auth.UserContext.AuthContext;
import com.company.framework.clients.AccountClient;
import com.company.framework.db.DbManager;
import com.company.framework.db.DbClient;
import com.company.framework.models.requests.RegisterUserRequest;
import com.company.tests.base.BaseTest;
import io.restassured.response.Response;
import org.testng.SkipException;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertTrue;

public class DbValidationTests extends BaseTest {

    @Test(groups = {"regression"})
    public void createUserAndValidateDb() throws Exception {
        AccountClient client = new AccountClient();
        AuthContext ctx = UserContext.get();
        String username = ctx.username();
        RegisterUserRequest request = new RegisterUserRequest(username, "P@ssw0rd");
        Response resp = client.createUser(request);
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
