package com.company.framework.auth;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class TokenManager {

    private static final Map<String, String> tokenCache = new ConcurrentHashMap<>();

    private TokenManager(){}

    public static void put(String username, String token) {
        tokenCache.put(username, token);
    }

    public static String get(String username) {
        return tokenCache.get(username);
    }

    public static void clear() {
        tokenCache.clear();
    }
}
