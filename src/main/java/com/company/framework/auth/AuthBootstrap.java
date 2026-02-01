package com.company.framework.auth;

import java.util.UUID;

import com.company.framework.auth.UserContext.AuthContext;
import com.company.framework.config.ConfigManager;

public final class AuthBootstrap {

    public static AuthContext init() {
        String email = ConfigManager.get("auth.email");
        String password = ConfigManager.get("auth.password");
        String deviceId = ConfigManager.get("auth.deviceKey");
        // initially no token
        AuthContext ctx = new AuthContext(email, password, null, deviceId);
        // cache it globally per thread
        UserContext.set(ctx);
        return ctx;
    }

    public static void clear() {
        UserContext.clear();
    }
}
