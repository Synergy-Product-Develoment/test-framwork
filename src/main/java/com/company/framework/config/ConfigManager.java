package com.company.framework.config;

import java.io.InputStream;
import java.util.Properties;

public final class ConfigManager {

    private static final Properties props = new Properties();

    static {
        String env = System.getProperty("env", "qa");
        String fileName = "config/application-" + env + ".properties";

        try (InputStream is = ConfigManager.class.getClassLoader().getResourceAsStream(fileName)) {
            if (is == null) throw new RuntimeException("Config file not found: " + fileName);
            props.load(is);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load config: " + fileName, e);
        }
    }

    private ConfigManager() {}

    public static String get(String key) {
        return props.getProperty(key);
    }

    public static int getInt(String key) {
        return Integer.parseInt(get(key));
    }

    public static boolean getBool(String key) {
        return Boolean.parseBoolean(get(key));
    }
}
