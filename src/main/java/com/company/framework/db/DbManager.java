package com.company.framework.db;

import com.company.framework.utils.MaskingUtil;
import com.company.framework.config.ConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DbManager {

    private static final Logger log = LoggerFactory.getLogger(DbManager.class);
    private static DbManager instance;
    private Connection conn;

    private DbManager() {}

    public static synchronized DbManager getInstance() {
        if (instance == null) {
            instance = new DbManager();
            instance.init();
        }
        return instance;
    }

    private void init() {
        if (!ConfigManager.getBool("db.enabled")) return;
        String url = ConfigManager.get("db.url");
        String user = ConfigManager.get("db.username");
        String pass = ConfigManager.get("db.password");
        String driver = ConfigManager.get("db.driver");

        try {
            if (driver != null && !driver.isBlank()) Class.forName(driver);
            log.info("Connecting to DB {} with user {}", url, user);
            log.info("DB credentials: {}", MaskingUtil.maskPassword(pass));
            conn = DriverManager.getConnection(url, user, pass);
        } catch (ClassNotFoundException | SQLException e) {
            log.warn("Failed to init DB connection: {}", e.getMessage());
            conn = null;
        }
    }

    public DbClient client() {
        if (conn == null) return null;
        return new DbClient(conn);
    }

    public void close() {
        try {
            if (conn != null && !conn.isClosed()) conn.close();
        } catch (SQLException e) {
            log.warn("Error closing DB connection: {}", e.getMessage());
        }
    }
}
