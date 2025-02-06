package controller;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class DatasourceProviderTest {

    @Inject
    DataSource dataSource;

    @Test
    void testDataSourceIsNotNull() {
        assertThat(dataSource).isNotNull();
    }
}