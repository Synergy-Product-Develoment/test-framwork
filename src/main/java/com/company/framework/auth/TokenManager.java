package com.company.framework.auth;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class TokenManager {

    private static final Map<String, AuthContext> tokenCache = new ConcurrentHashMap<>();

    private TokenManager(){}

    public static void put(String username, String token) {
        if(tokenCache.containsKey(username)) {
            tokenCache.computeIfPresent(username, (k, authContext) -> new AuthContext(authContext.username(), authContext.password(), token));;
        } else  {
            throw new IllegalStateException("No AuthContext found for username: " + username);
        }
    }

    public static void put(String username, String password, String token) {
        tokenCache.put(username, new AuthContext(username, password, token));
    }
    public static AuthContext get(String username) {
        return tokenCache.get(username);
    }

    public static void clear() {
        tokenCache.clear();
    }
}
