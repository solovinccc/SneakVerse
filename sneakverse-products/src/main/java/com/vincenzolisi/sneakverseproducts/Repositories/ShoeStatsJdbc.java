package com.vincenzolisi.sneakverseproducts.Repositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public class ShoeStatsJdbc {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public BigDecimal getAverageShoePrice() {
        String sql = "SELECT AVG(shoePrice) FROM shoes";
        return jdbcTemplate.queryForObject(sql, BigDecimal.class);
    }
}