package com.company.framework.utils;

import java.time.Duration;
import java.util.function.Supplier;

public final class RetryUtil {

    private RetryUtil(){}

    public static <T> T retry(int maxAttempts, Duration backoff, Supplier<T> supplier) {
        RuntimeException last = null;
        for (int i = 1; i <= maxAttempts; i++) {
            try {
                return supplier.get();
            } catch (RuntimeException e) {
                last = e;
                try {
                    Thread.sleep(backoff.toMillis());
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    throw e;
                }
            }
        }
        throw last;
    }
}
