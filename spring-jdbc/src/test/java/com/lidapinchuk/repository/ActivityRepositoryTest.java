package com.lidapinchuk.repository;

import com.lidapinchuk.config.ApplicationConfig;
import com.lidapinchuk.model.Activity;
import com.lidapinchuk.repository.impl.JdbcActivityRepository;
import com.lidapinchuk.repository.impl.JdbcBuildingRepository;
import com.lidapinchuk.util.DatabaseUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { ApplicationConfig.class })
public class ActivityRepositoryTest {

    @Autowired
    private JdbcActivityRepository jdbcActivityRepository;
    @Autowired
    private JdbcBuildingRepository jdbcBuildingRepository;

    @BeforeAll
    public static void initDatabase(@Autowired DataSource dataSource) {

        DatabaseUtil.initializeDatabase(dataSource);
    }

    @BeforeEach
    public void initDatabaseContent(@Autowired DataSource dataSource) {

        DatabaseUtil.fillDatabase(dataSource);
    }

    @Test
    void testCreate() {
        Activity activity = Activity.builder()
                .workName("Activity")
                .measurement("m")
                .price(new BigDecimal(10).setScale(2))
                .amount(new BigDecimal(1).setScale(2))
                .building(jdbcBuildingRepository.getBuildingById(1L))
                .build();

        int initialActivitiesSize = jdbcActivityRepository.getAllActivities().size();

        jdbcActivityRepository.createActivity(activity);
        Collection<Activity> activities = jdbcActivityRepository.getAllActivities();

        assertNotNull(activity.getInstId());
        assertTrue(activities.contains(activity));
        assertEquals(initialActivitiesSize + 1, activities.size());

        jdbcActivityRepository.deleteActivityById(activity.getInstId());
    }

    @Test
    void testUpdate() {
        Activity activity = Activity.builder()
                .workName("Activity")
                .measurement("m")
                .price(new BigDecimal(10).setScale(2))
                .amount(new BigDecimal(1).setScale(2))
                .building(jdbcBuildingRepository.getBuildingById(1L))
                .build();
        jdbcActivityRepository.createActivity(activity);
        int initialActivitiesSize = jdbcActivityRepository.getAllActivities().size();

        activity.setWorkName("activity")
                .setMeasurement("m2")
                .setPrice(new BigDecimal(20).setScale(2))
                .setAmount(new BigDecimal(2).setScale(2))
                .setBuilding(jdbcBuildingRepository.getBuildingById(2L));

        jdbcActivityRepository.updateActivity(activity);

        Collection<Activity> activities = jdbcActivityRepository.getAllActivities();
        Activity updatedActivity = jdbcActivityRepository.getActivityById(activity.getInstId());

        assertEquals(activity, updatedActivity);
        assertEquals(initialActivitiesSize, activities.size());
        assertEquals(activity.getBuilding().getInstId(), updatedActivity.getBuilding().getInstId());

        jdbcActivityRepository.deleteActivityById(activity.getInstId());
    }

    @Test
    void testDeleteById() {
        Activity activity = Activity.builder()
                .workName("Activity")
                .measurement("m")
                .price(new BigDecimal(10))
                .amount(new BigDecimal(1))
                .building(jdbcBuildingRepository.getBuildingById(1L))
                .build();

        jdbcActivityRepository.createActivity(activity);
        int initialActivitiesSize = jdbcActivityRepository.getAllActivities().size();

        jdbcActivityRepository.deleteActivityById(activity.getInstId());
        Collection<Activity> activities = jdbcActivityRepository.getAllActivities();

        assertFalse(activities.contains(activity));
        assertEquals(initialActivitiesSize - 1, activities.size());
    }

    @Test
    void testGetById() {
        Activity activity = Activity.builder()
                .workName("Activity")
                .measurement("m")
                .price(new BigDecimal(10).setScale(2))
                .amount(new BigDecimal(1).setScale(2))
                .building(jdbcBuildingRepository.getBuildingById(1L))
                .build();

        jdbcActivityRepository.createActivity(activity);

        Activity anotherActivity = jdbcActivityRepository.getActivityById(activity.getInstId());

        assertEquals(activity, anotherActivity);

        jdbcActivityRepository.deleteActivityById(activity.getInstId());
    }

    @Test
    void testGetAll() {

        Collection<Activity> oldActivities = jdbcActivityRepository.getAllActivities();

        Activity activity = Activity.builder()
                .workName("Activity")
                .measurement("m")
                .price(new BigDecimal(10).setScale(2))
                .amount(new BigDecimal(1).setScale(2))
                .building(jdbcBuildingRepository.getBuildingById(1L))
                .build();

        jdbcActivityRepository.createActivity(activity);

        Collection<Activity> activities = jdbcActivityRepository.getAllActivities();

        assertEquals(oldActivities.size() + 1, activities.size());
        assertTrue(activities.containsAll(oldActivities));
        assertTrue(activities.contains(activity));

        jdbcActivityRepository.deleteActivityById(activity.getInstId());
    }

    @Test
    void testGetAllByBuildingId() {

        Collection<Activity> activities = jdbcActivityRepository.getAllActivitiesByBuildingId(1L);

        assertEquals(3, activities.size());

        Object[] activitiesArr = activities.toArray();
        assertEquals(1, ((Activity) activitiesArr[0]).getInstId());
        assertEquals(2, ((Activity) activitiesArr[1]).getInstId());
        assertEquals(3, ((Activity) activitiesArr[2]).getInstId());
    }

}
