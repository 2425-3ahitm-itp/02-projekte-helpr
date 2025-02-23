package at.htl.helpr.controller;


import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

public class Database {

    static final String appConfigPath = Objects.requireNonNull(
            Database.class.getResource("/database.properties")
    ).getPath();


    static final String USERNAME = "app";
    static final String PASSWORD = "app";
    public static final String URL = "jdbc:postgresql://localhost:5432/helpr";

    /**
     * Get a DataSource object to connect to the database.
     * The properties are read from a file 'database.properties'.
     * If the file is not found, the default properties are used.
     * The default port of the h2 db is 9092
     *
     * @return DataSource object
     */
    public static Connection getConnection() {
        Properties dbProperties = new Properties();

        try (FileInputStream in = new FileInputStream(appConfigPath)) {
            dbProperties.load(in);

            dbProperties.setProperty("user", "app");
            dbProperties.setProperty("password", "app");

            return DriverManager.getConnection(dbProperties.getProperty("url"), dbProperties);
        } catch (IOException e) {
            throw new RuntimeException("Mhm");
        } catch (SQLException e) {
            throw new RuntimeException("Mhm2");
        }

    }

}
