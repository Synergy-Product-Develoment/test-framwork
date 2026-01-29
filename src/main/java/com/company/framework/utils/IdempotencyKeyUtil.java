package com.company.framework.utils;

import java.util.UUID;

public final class IdempotencyKeyUtil {

    private static final ThreadLocal<String> current = new ThreadLocal<>();

    private IdempotencyKeyUtil(){}

    public static String newKey() {
        String k = UUID.randomUUID().toString();
        current.set(k);
        return k;
    }

    public static String getCurrent() {
        return current.get();
    }

    public static void clear() {
        current.remove();
    }
}
