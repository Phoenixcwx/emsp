package com.volvo.emspdemo.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DbManager {

    private final JdbcTemplate jdbcTemplate;

    public DbManager(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void init() {
        String createTable = "CREATE TABLE  ema_id (" +
                "country VARCHAR(50)," +
                "province VARCHAR(50)," +
                "seq_number BIGINT) ";
        jdbcTemplate.execute(createTable);
    }
}
