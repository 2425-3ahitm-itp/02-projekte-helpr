package at.htl.helpr.controller;

import io.agroal.api.AgroalDataSource;
import io.quarkus.arc.Arc;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

public class Database {

    private static final String USERNAME = "app";
    private static final String PASSWORD = "app";
    private static final String URL = "jdbc:postgresql://localhost:5432/helpr?currentSchema=helpr";

    public static Connection getConnection() {
        DataSource ds = Arc.container().instance(AgroalDataSource.class).get();
        try {
            return ds.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
