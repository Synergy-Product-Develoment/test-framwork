package com.company.demoqa;

import com.company.framework.auth.TokenManager;
import com.company.framework.auth.UserContext;
import com.company.framework.clients.AuthenticationClient;
import com.company.base.BaseTest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.Test;

@Slf4j
public class AccountTests extends BaseTest {

    private final AuthenticationClient authenticationClient = new AuthenticationClient();

    @Test
    public void shouldCreateUser() {

        if(StringUtils.isNotBlank(TokenManager.get(UserContext.getUsername()))){

        } else  {
            log.info("User is already authenticated/existing, skipping user creation.");
        }

    }
}

