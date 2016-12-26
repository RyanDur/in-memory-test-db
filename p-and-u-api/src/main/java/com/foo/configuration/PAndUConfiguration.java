package com.foo.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@ComponentScan({"com.db"})
public class PAndUConfiguration {

    @Bean
    public DataSource getDataSource(@Value("${spring.datasource.url}") String url,
                                    @Value("${spring.datasource.username}") String username,
                                    @Value("${spring.datasource.driver-class-name}") String driver) {
        return DataSourceBuilder.create()
                .url(url)
                .username(username)
                .driverClassName(driver)
                .build();
    }
}
