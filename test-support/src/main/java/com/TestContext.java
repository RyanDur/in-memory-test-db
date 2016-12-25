package com;

import com.testhelpers.ProgrammerTestFixture;
import com.testhelpers.UnicornTestFixture;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@SpringBootConfiguration
public class TestContext {


    @TestConfiguration
    static class Config {

        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                    .url("jdbc:hsqldb:mem:PUBLIC;sql.syntax_db2=true;")
                    .username("sa")
                    .password("").build();
        }

        @Bean
        public JdbcTemplate jdbcTemplate() {
            return new JdbcTemplate(dataSource());
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
