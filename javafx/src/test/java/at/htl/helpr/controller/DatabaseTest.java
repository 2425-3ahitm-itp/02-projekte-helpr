package at.htl.helpr.controller;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class DatabaseTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void getConnectionNotNull() {
        assertDoesNotThrow(() -> {
            Connection connection = Database.getConnection();
            assertNotNull(connection);
            connection.close();
        });
    }

    @Test
    void getConnectionIsValid() {
        assertDoesNotThrow(() -> {
            Connection connection = Database.getConnection();
            assertNotNull(connection);
            assertTrue(connection.isValid(2)); // 2 seconds timeout
            connection.close();
        });
    }
}