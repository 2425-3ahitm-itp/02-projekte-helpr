package at.htl.helpr.controller;

import static org.junit.jupiter.api.Assertions.*;

import io.quarkus.test.junit.QuarkusTest;
import java.sql.Connection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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