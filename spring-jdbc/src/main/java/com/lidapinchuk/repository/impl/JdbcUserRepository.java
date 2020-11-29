package com.lidapinchuk.repository.impl;

import com.lidapinchuk.repository.JpaUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
@RequiredArgsConstructor
public class JdbcUserRepository extends JpaUserRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final String SQL_GET_TOTAL_ACTIVITIES_PRICE_BY_USER_ID =
            "SELECT SUM(price * amount) " +
            "FROM activity " +
            "WHERE building_id IN " +
                "(SELECT inst_id " +
                "FROM building " +
                "WHERE report_id IN " +
                    "(SELECT inst_id " +
                    "FROM report " +
                    "WHERE user_id = :userId))";

    public BigDecimal getTotalActivitiesPriceByUserId(Long userId) {
        return jdbcTemplate.queryForObject(SQL_GET_TOTAL_ACTIVITIES_PRICE_BY_USER_ID,
                new MapSqlParameterSource().addValue("userId", userId),
                BigDecimal.class);
    }

}
