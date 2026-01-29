package com.company.framework.auth;

import java.util.UUID;

public final class AuthBootstrap {

    public static AuthContext init() {
        String randomUser = "user_" + UUID.randomUUID().toString().substring(0, 8);
        String randomPwd  = "pwd_A_@" + UUID.randomUUID().toString().substring(0, 12);

        // initially no token
        AuthContext ctx = new AuthContext(randomUser, randomPwd, null);

        // cache it globally
        TokenManager.put(ctx);

        return ctx;
    }

    public static void clear() {
        TokenManager.clear();
    }
}
