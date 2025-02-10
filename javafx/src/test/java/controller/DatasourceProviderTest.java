package controller;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
class DatasourceProviderTest {

    @Inject
    DataSource dataSource;

    @BeforeEach
    void setup() {
        assertThat(dataSource).isNotNull();
    }

    @Test
    void testDataSourceIsNotNull() {
        assertThat(dataSource).isNotNull();
    }

    @Test
    void testDataSourceIsValid() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            assertThat(connection).isNotNull();
            assertThat(connection.isValid(2)).isTrue();
        }
    }

    @Test
    void testDataSourceCanExecuteQuery() throws SQLException {
        try (Connection connection = dataSource.getConnection();
             var stmt = connection.createStatement();
             var rs = stmt.executeQuery("SELECT 1")) {

            assertThat(rs).isNotNull();
            assertThat(rs.next()).isTrue();
            assertThat(rs.getInt(1)).isEqualTo(1);
        }
    }
}
