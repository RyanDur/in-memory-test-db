package com.testdb;

import com.testdb.testhelpers.ProgrammerTestFixture;
import com.testdb.testhelpers.UnicornTestFixture;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@SpringBootConfiguration
public class TestContext {


    @TestConfiguration
    static class Config {

        @Primary
        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                    .url("jdbc:hsqldb:mem:PUBLIC;sql.syntax_mysql=true;")
                    .username("sa")
                    .password("").build();
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }

        @Bean
        public ProgrammerTestFixture getProgrammerTestFixture(JdbcTemplate template) {
            return new ProgrammerTestFixture(template);
        }

        @Bean
        public UnicornTestFixture getUnicornTestFixture(JdbcTemplate template) {
            return new UnicornTestFixture(template);
        }
    }
}
