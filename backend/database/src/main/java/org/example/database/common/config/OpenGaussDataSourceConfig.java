package org.example.database.common.config;

import org.opengauss.util.PSQLException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class OpenGaussDataSourceConfig {

    @Bean
    public DataSource dataSource() throws PSQLException {
        org.opengauss.ds.PGSimpleDataSource dataSource = new org.opengauss.ds.PGSimpleDataSource();
        dataSource.setUrl("jdbc:opengauss://localhost:5432/databaseproject");
        dataSource.setUser("gaussdb");
        dataSource.setPassword("openGauss@123");
        return dataSource;
    }
}
