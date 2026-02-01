package com.company.framework.listeners;

import com.company.framework.auth.AuthBootstrap;
import com.company.framework.auth.TokenManager;
import com.company.framework.clients.AuthenticationClient;
import com.company.framework.config.ConfigManager;
import com.company.framework.models.requests.AuthRequest;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.testng.ISuite;
import org.testng.ISuiteListener;

/*
* Executes per suit
*
* */
@Slf4j
public class SuiteAuthSetupListener implements ISuiteListener {

    @Override
    public void onStart(ISuite suite) {

    }

}
