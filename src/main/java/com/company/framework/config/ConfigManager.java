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

    public static String get(String key, String defaultValue) {
        return props.getProperty(key, defaultValue);
    }

    public static int getInt(String key) {
        return Integer.parseInt(get(key));
    }

    public static int getInt(String key, int defaultValue) {
        String v = get(key);
        return v == null ? defaultValue : Integer.parseInt(v);
    }

    public static boolean getBool(String key) {
        return Boolean.parseBoolean(get(key));
    }

    public static boolean getBool(String key, boolean defaultValue) {
        String v = get(key);
        return v == null ? defaultValue : Boolean.parseBoolean(v);
    }

    public static long getLong(String key, long defaultValue) {
        String v = get(key);
        return v == null ? defaultValue : Long.parseLong(v);
    }
}
