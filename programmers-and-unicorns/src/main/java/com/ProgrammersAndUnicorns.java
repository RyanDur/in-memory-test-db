package com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProgrammersAndUnicorns {

    private final NamedParameterJdbcTemplate template;

    @Autowired
    public ProgrammersAndUnicorns(JdbcTemplate template) {
        this.template = new NamedParameterJdbcTemplate(template);
    }

    public void add(String programmer) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", programmer);

        template.update("INSERT INTO programmer(name) VALUES (:name)", params);
    }

    public void add(String unicorn, String programmer) throws InterruptedException {
        add(programmer);

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", unicorn);
        params.addValue("programmer", programmer);

        template.update("INSERT INTO unicorn(name, programmer) VALUES (:name, :programmer)", params);
    }

    public List<String> getAllProgrammers() {
        return template.query("SELECT * FROM programmer", (rs, rowNum) -> rs.getString("name"));
    }
}
