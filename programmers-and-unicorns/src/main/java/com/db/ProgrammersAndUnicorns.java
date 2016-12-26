package com.db;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.Arrays.asList;

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

    public void add(String unicorn, String programmer) {
        add(programmer);

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", unicorn);
        params.addValue("programmer_name", programmer);

        template.update("INSERT INTO unicorn(name, programmer_name) VALUES (:name, :programmer_name)", params);
    }

    public List<String> getAllProgrammers() {
        return template.query("SELECT * FROM programmer", (rs, rowNum) -> rs.getString("name"));
    }

    public List<String> getAllUnicorns() {
        return template.query("SELECT * FROM unicorn", (rs, rowNum) -> rs.getString("name"));
    }

    public List<List<String>> getAllPairs() {
        return template.query("SELECT * FROM unicorn", (rs, rowNum) ->
                asList(rs.getString("programmer_name"),
                        rs.getString("name")));

    }
}
