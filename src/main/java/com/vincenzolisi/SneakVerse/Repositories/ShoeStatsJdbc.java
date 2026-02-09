package com.vincenzolisi.SneakVerse.Repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@Repository
public class ShoeStatsJdbc {

    @Autowired
    private JdbcTemplate jdbc;

    public BigDecimal getAverageShoePrice() {
        String sql = "SELECT COALESCE(AVG(shoePrice), 0) FROM shoes";
        return jdbc.queryForObject(sql, BigDecimal.class);
    }
}
