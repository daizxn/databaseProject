package org.example.database.common.config;

import com.huawei.gauss200.jdbc.ds.PGSimpleDataSource;
import com.huawei.gauss200.jdbc.util.PSQLException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class OpenGaussDataSourceConfig {

    @Bean
    public DataSource dataSource() throws PSQLException {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl("jdbc:gaussdb://110.41.119.192:8000/db_zjut");
        dataSource.setCurrentSchema("db_user30");
        dataSource.setUser("db_user30");
        dataSource.setPassword("db_user30@123");
        return dataSource;
    }
}
