package com.lidapinchuk.repository.impl;

import com.lidapinchuk.model.Activity;
import com.lidapinchuk.repository.JpaActivityRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.Collection;

@Repository
@RequiredArgsConstructor
public class JdbcActivityRepository extends JpaActivityRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final String SQL_GET_ALL_ACTIVITIES_BY_BUILDING_ID =
            "SELECT * FROM activity WHERE building_id = :buildingId";

    public Collection<Activity> getAllActivitiesByBuildingId(Long buildingId) {

        return jdbcTemplate.query(SQL_GET_ALL_ACTIVITIES_BY_BUILDING_ID,
                new MapSqlParameterSource().addValue("buildingId", buildingId),
                new ActivityMapper());
    }

    class ActivityMapper implements RowMapper<Activity> {

        @SneakyThrows
        public Activity mapRow(ResultSet resultSet, int rowNum) {
            return Activity.builder()
                    .instId(resultSet.getLong("inst_id"))
                    .workName(resultSet.getString("work_name"))
                    .measurement(resultSet.getString("measurement"))
                    .price(resultSet.getBigDecimal("price"))
                    .amount(resultSet.getBigDecimal("amount"))
                    .build();
        }
    }

}
