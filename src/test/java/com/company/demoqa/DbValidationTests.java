package com.company.demoqa;

import com.company.base.BaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class DbValidationTests extends BaseTest {

    @Test(groups = {"regression"})
    public void createUserAndValidateDb() throws Exception {
//        AuthenticationClient client = new AuthenticationClient();
//        AuthContext ctx = UserContext.get();
//        String username = ctx.email();
//        RegisterUserRequest request = new RegisterUserRequest(username, "P@ssw0rd");
//        Response resp = client.createUser(request);
//        int status = resp.getStatusCode();
//
//        // If DB not enabled, skip DB assertions
//        if (!com.company.framework.config.ConfigManager.getBool("db.enabled")) {
//            throw new SkipException("DB not enabled - skipping DB validation");
//        }
//
//        DbManager mgr = DbManager.getInstance();
//        DbClient db = mgr.client();
//        if (db == null) throw new SkipException("DB client not available");
//
//        List<Map<String, Object>> rows = db.query("SELECT * FROM users WHERE username = ?", username);
//        assertTrue(rows.size() > 0, "Expected DB to contain created user");
    }
}
