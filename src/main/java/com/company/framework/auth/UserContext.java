package com.company.framework.auth;

public final class UserContext {

    private static final ThreadLocal<AuthContext> ctx = new ThreadLocal<>();

    private UserContext() {}

    public static void set(String username, String password) {
        ctx.set(new AuthContext(username, password, null));
    }

    public static void set(AuthContext auth) {
        ctx.set(auth);
    }

    public static void set(String username, String password, String userId) {
        ctx.set(new AuthContext(username, password, userId));
    }

    public static AuthContext get() {
        return ctx.get();
    }

    public static String getUsername() {
        AuthContext a = ctx.get();
        return a == null ? null : a.username();
    }

    public static String getPassword() {
        AuthContext a = ctx.get();
        return a == null ? null : a.password();
    }

    public static String getUserId() {
        AuthContext a = ctx.get();
        return a == null ? null : a.userId();
    }

    public static void clear() {
        ctx.remove();
    }


public static class AuthContext {
    private final String username;
    private final String password;
    private final String userId;

    public AuthContext(String username, String password,  String userId) {
        this.username = username;
        this.password = password;
        this.userId = userId;
    }

    public String username() { return username; }
    public String password() { return password; }
    public String userId() { return userId; }
}
}
