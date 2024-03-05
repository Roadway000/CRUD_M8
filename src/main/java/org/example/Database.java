package org.example;

import org.example.utils.MyFormat;
import org.flywaydb.core.Flyway;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Database {
    private static Database instance;
    public Connection conn;

    private Database() {
        String url = ConnectReader.getConnectionPostgres();
        String user = ConnectReader.getUserPostgres();
        String psw = ConnectReader.getPswPostgres();
        try {
            this.conn = DriverManager.getConnection(url, user, psw);
            flywayMigration(url,user,psw);
        } catch (SQLException e) {
            MyFormat.logger.info("SQL exception. Can not create connect");
            throw new RuntimeException("Database(),getConnection Exception: "+e.getMessage());
        }
    }

    public static synchronized Database getInstance() throws SQLException {
        if ((instance == null) || (instance.getConnection().isClosed())) {
            instance = new Database();
        }
        return instance;
    }

    public Connection getConnection() {
        return conn;
    }

    private void flywayMigration(String connUrl, String connUser, String connPsw) {
        Flyway flyway = Flyway.configure().dataSource(connUrl,connUser,connPsw).load();
        flyway.migrate();
    }
}
