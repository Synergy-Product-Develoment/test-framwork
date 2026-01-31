package com.company.framework.listeners;

import com.company.framework.auth.TokenManager;
import com.company.framework.clients.AccountClient;
import com.company.framework.config.ConfigManager;
import com.company.framework.models.requests.AuthRequest;
import io.qameta.allure.Step;
import org.testng.ISuite;
import org.testng.ISuiteListener;

/*
* Executes per suit
*
* */
public class SuiteAuthSetupListener implements ISuiteListener {

    @Override
    public void onStart(ISuite suite) {
        authenticate();
    }

    @Step("Authenticate before suite execution")
    private void authenticate() {
        String email = ConfigManager.get("auth.email");
        String password = ConfigManager.get("auth.password");

        AuthRequest req = AuthRequest.builder()
                .email(email)
                .password(password)
                .build();

        String token = new AccountClient()
                .generateToken(req)
                .then()
                .statusCode(200)
                .extract()
                .path("token");

        TokenManager.put(email, token);
    }
}
