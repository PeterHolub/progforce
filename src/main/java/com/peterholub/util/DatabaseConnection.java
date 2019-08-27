package com.peterholub.util;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import com.mysql.cj.jdbc.MysqlDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

/**
 * Class util for database connection
 */
public final class DatabaseConnection {
    private static final String URL = "url";
    private static final String FILENAME = "database.properties";
    private static MysqlDataSource mysqlDataSource;

    static {
        Properties properties = new Properties();
        try {
            properties.load(Objects.requireNonNull(DatabaseConnection.class.getClassLoader().getResourceAsStream(FILENAME)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        mysqlDataSource = new MysqlConnectionPoolDataSource();
        mysqlDataSource.setURL(properties.getProperty(URL));
    }

    private DatabaseConnection() {
    }

    public static DataSource getDataSource() {
        return mysqlDataSource;
    }
}
