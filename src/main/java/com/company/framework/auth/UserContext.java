package com.company.framework.auth;

public final class UserContext {

    private static AuthContext ctx;

    private UserContext() {}

    public static void set(String username, String password, String deviceId) {
        ctx = new AuthContext(username, password, null, deviceId);
    }

    public static void set(AuthContext auth) {
        ctx= auth;
    }

    public static void set(String username, String password, String userId, String deviceId) {
        ctx= new AuthContext(username, password, userId, deviceId);
    }

    public static AuthContext get() {
        return ctx;
    }

    public static String getUsername() {
        return ctx == null ? null : ctx.email();
    }

    public static String getPassword() {
        AuthContext a = ctx;
        return a == null ? null : a.password();
    }

    public static String getUserId() {
        AuthContext a = ctx;
        return a == null ? null : a.userId();
    }

    public static void clear() {
        ctx = null;
    }



    public record AuthContext(String email, String password, String userId, String deviceId) { }
}
