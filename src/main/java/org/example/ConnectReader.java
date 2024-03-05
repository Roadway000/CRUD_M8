package org.example;

import org.example.utils.MyFormat;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConnectReader {
    private static final String LIST_PROPERTY = "dbconnect.properties";
    private static final String SOMETHING_WENT_WRONG = "Something went wrong! The file dbconnect.properties cannot be found";
    private ConnectReader() {
        throw new UnsupportedOperationException("notImplemented() cannot be performed because ...");
    }
    public static String getConnectionPostgres() {
        try (InputStream input = ConnectReader.class.getClassLoader()
                .getResourceAsStream(LIST_PROPERTY)) {
            Properties prop = new Properties();
            if (input == null) {
                MyFormat.logger.info(SOMETHING_WENT_WRONG);
                return null;
            }
            prop.load(input);
            return new StringBuilder("jdbc:postgresql://")
                    .append(prop.getProperty("postgres.db.host"))
                    .append(":")
                    .append(prop.getProperty("postgres.db.port"))
                    .append("/")
                    .append(prop.getProperty("postgres.db.database"))
                    .append("?currentSchema=public")
                    .toString(); // jdbc:postgresql://localhost:5432/postgrestest?currentSchema=public
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static String getUserPostgres() {
        try (InputStream input = ConnectReader.class.getClassLoader()
                .getResourceAsStream(LIST_PROPERTY)) {
            Properties prop = new Properties();
            if (input == null) {
                MyFormat.logger.info(SOMETHING_WENT_WRONG);
                return null;
            }
            prop.load(input);
            return prop.getProperty("postgres.db.username");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static String getPswPostgres() {
        try (InputStream input = ConnectReader.class.getClassLoader()
                .getResourceAsStream(LIST_PROPERTY)) {
            Properties prop = new Properties();
            if (input == null) {
                MyFormat.logger.info(SOMETHING_WENT_WRONG);
                return null;
            }
            prop.load(input);
            return prop.getProperty("postgres.db.password");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getConnectionMySQL() {
        try (InputStream input = ConnectReader.class.getClassLoader()
                .getResourceAsStream(LIST_PROPERTY)) {
            Properties prop = new Properties();
            if (input == null) {
                MyFormat.logger.info(SOMETHING_WENT_WRONG);
                return null;
            }
            prop.load(input);
            return new StringBuilder("jdbc:mysql://")
                    .append(prop.getProperty("mysql.db.host"))
                    .append(":")
                    .append(prop.getProperty("mysql.db.port"))
                    .append("/")
                    .append(prop.getProperty("mysql.db.database"))
                    .append("?allowPublicKeyRetrieval=true&useSSL=false")
                    .toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getConnectionH2() {
        try (InputStream input = ConnectReader.class.getClassLoader()
                .getResourceAsStream(LIST_PROPERTY)) {
            Properties prop = new Properties();
            if (input == null) {
                MyFormat.logger.info(SOMETHING_WENT_WRONG);
                return null;
            }
            prop.load(input);
            return new StringBuilder("jdbc:")
                    .append(prop.getProperty("h2.db.host"))
                    .append(":./")
                    .append(prop.getProperty("h2.db.database"))
                    .toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
