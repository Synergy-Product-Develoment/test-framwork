package com.company.framework.auth;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class TokenManager {
    private static final Map<String, String> contextCache = new ConcurrentHashMap<>();

    private TokenManager(){}

    public static void put(String username, String token) {
        contextCache.put(username, token);
    }

    public static String get(String username) {
        return contextCache.get(username);
    }

    public static void clear() {
        contextCache.clear();
    }
}
