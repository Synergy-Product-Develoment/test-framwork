package com.company.framework.utils;

import java.util.regex.Pattern;

public final class MaskingUtil {

    private static final Pattern PASSWORD = Pattern.compile("(\"password\"\s*:\s*\")(.*?)(\")", Pattern.CASE_INSENSITIVE);

    private MaskingUtil(){}

    public static String maskSensitive(String raw) {
        if (raw == null) return null;
        String masked = PASSWORD.matcher(raw).replaceAll("$1***$3");
        return masked;
    }
}
