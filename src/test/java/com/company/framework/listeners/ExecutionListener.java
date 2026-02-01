package com.company.framework.listeners;

import com.company.framework.auth.AuthBootstrap;
import com.company.framework.clients.AuthenticationClient;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.testng.IExecutionListener;

/*
* Executes before and after the entire test suite.
* ExecutionListener listens to the start and finish of the test execution.
*/
@Slf4j
public class ExecutionListener implements IExecutionListener {

    @Override
    public void onExecutionStart() {
        log.info("Test execution started.");
        authenticate();

    }

    @Override
    public void onExecutionFinish() {
        System.out.println("Test execution finished.");
    }


    @Step("Authenticate before suite execution")
    private void authenticate() {
        AuthBootstrap.init();
        Response res = new AuthenticationClient()
                .authenticate(null);
    }
}
