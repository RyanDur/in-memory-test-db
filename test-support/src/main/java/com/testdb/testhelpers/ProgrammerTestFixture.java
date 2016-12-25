package com.testdb.testhelpers;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class ProgrammerTestFixture {

    private final  NamedParameterJdbcTemplate template;

    public ProgrammerTestFixture(JdbcTemplate template) {
        this.template = new NamedParameterJdbcTemplate(template.getDataSource());
    }

    public Programmer insert(Programmer programmer) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", programmer.getName());

        template.update("INSERT INTO programmer(name) VALUES (:name)", params);

        return programmer;
    }
}
