package com.company.framework.auth;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class TokenManager {
    private static final Map<String, AuthContext> contextCache = new ConcurrentHashMap<>();

    private TokenManager(){}

    public static void put(AuthContext context) {
        contextCache.put(context.username(), context);
    }

    public static AuthContext get(String username) {
        return contextCache.get(username);
    }

    public static AuthContext current() {
        // if only one user per run, return the first
        return contextCache.values().stream().findFirst().orElse(null);
    }

    public static void clear() {
        contextCache.clear();
    }
}
