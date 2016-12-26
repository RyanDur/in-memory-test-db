package com.testdb.testhelpers;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

public class UnicornTestFixture {

    private final NamedParameterJdbcTemplate template;

    public UnicornTestFixture(JdbcTemplate template) {
        DataSource dataSource = template.getDataSource();
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    public Unicorn insert(Unicorn unicorn) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", unicorn.getName());
        params.addValue("programmer_name", unicorn.getProgrammer().getName());

        template.update("INSERT INTO unicorn(name, programmer_name) VALUES (:name, :programmer_name)", params);

        return unicorn;
    }
}
