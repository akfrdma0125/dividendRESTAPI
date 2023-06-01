package com.example.kuit_server.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Slf4j
@Repository
public class DividendDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public DividendDao(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }
}
