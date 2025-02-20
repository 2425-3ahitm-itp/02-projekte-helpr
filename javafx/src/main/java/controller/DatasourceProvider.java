package controller;

import io.agroal.api.AgroalDataSource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import javax.sql.DataSource;

@ApplicationScoped
public class DatasourceProvider {

    @Inject
    AgroalDataSource dataSource;

    public DataSource getDataSource() {
        return dataSource;
    }
}
