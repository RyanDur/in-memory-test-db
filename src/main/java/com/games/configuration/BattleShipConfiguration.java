package com.games.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class BattleShipConfiguration {

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
