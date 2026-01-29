package com.company.framework.utils;

import com.company.framework.config.ConfigManager;
import io.qameta.allure.Allure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.function.Supplier;

public final class RetryUtil {

    private static final Logger log = LoggerFactory.getLogger(RetryUtil.class);

    private RetryUtil(){}

    public static <T> T retry(Supplier<T> supplier) {
        boolean enabled = ConfigManager.getBool("retry.enabled");
        int maxAttempts = ConfigManager.getInt("retry.maxAttempts", 3);
        long backoffMs = ConfigManager.getLong("retry.backoffMs", 1000L);

        if (!enabled) {
            return supplier.get();
        }

        RuntimeException last = null;
        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            try {
                Allure.step("Retry attempt " + attempt + " of " + maxAttempts);
                log.info("Retry attempt {}/{}", attempt, maxAttempts);
                return supplier.get();
            } catch (RuntimeException e) {
                last = e;
                log.warn("Attempt {}/{} failed: {}", attempt, maxAttempts, e.toString());
                try {
                    Allure.addAttachment("retry-exception-" + attempt, e.toString());
                } catch (Exception ignore) {}
                if (attempt < maxAttempts) {
                    try {
                        Thread.sleep(backoffMs);
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                        throw e;
                    }
                }
            }
        }
        throw last;
    }
}
