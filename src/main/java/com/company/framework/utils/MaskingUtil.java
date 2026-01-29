package com.company.framework.utils;

import java.util.regex.Pattern;

public final class MaskingUtil {

    private static final Pattern PASSWORD = Pattern.compile("(\"password\"\s*:\s*\")(.*?)(\")", Pattern.CASE_INSENSITIVE);
    private static final Pattern TOKEN = Pattern.compile("(Bearer\\s+)(.+)", Pattern.CASE_INSENSITIVE);

    private MaskingUtil(){}

    public static String maskSensitive(String raw) {
        if (raw == null) return null;
        String masked = PASSWORD.matcher(raw).replaceAll("$1***$3");
        return masked;
    }

    public static String maskHeaderIfSensitive(String name, String value) {
        if (name == null || value == null) return value;
        String low = name.toLowerCase();
        if (low.contains("authorization") || low.contains("token") || low.contains("password")) {
            return "***";
        }
        return value;
    }

    public static String maskPassword(String password) {
        if (password == null) return null;
        return password.replaceAll(".", "*");
    }
}
