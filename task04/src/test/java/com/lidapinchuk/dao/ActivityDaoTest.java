package com.lidapinchuk.dao;

import com.lidapinchuk.dao.impl.ActivityDaoImpl;
import com.lidapinchuk.dao.impl.BuildingDaoImpl;
import com.lidapinchuk.model.Activity;
import com.lidapinchuk.util.DatabaseUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class ActivityDaoTest {

    private static BuildingDao buildingDao;
    private static ActivityDao activityDao;

    @BeforeAll
    public static void initDatabase() {

        DatabaseUtil.initializeDatabase();

        buildingDao = BuildingDaoImpl.getInstance();
        activityDao = ActivityDaoImpl.getInstance();
    }

    @BeforeEach
    public void initDatabaseContent() {

        DatabaseUtil.fillDatabase();
    }

    @Test
    void testCreate() {
        Activity activity = Activity.builder()
                .workName("Activity")
                .measurement("m")
                .price(10D)
                .amount(1D)
                .building(buildingDao.getBuildingById(1L))
                .build();

        int initialActivitiesSize = activityDao.getAllActivities().size();

        activityDao.createActivity(activity);
        Collection<Activity> activities = activityDao.getAllActivities();

        assertNotNull(activity.getInstId());
        assertTrue(activities.contains(activity));
        assertEquals(initialActivitiesSize + 1, activities.size());

        activityDao.deleteActivityById(activity.getInstId());
    }

    @Test
    void testUpdate() {
        Activity activity = Activity.builder()
                .workName("Activity")
                .measurement("m")
                .price(10D)
                .amount(1D)
                .building(buildingDao.getBuildingById(1L))
                .build();
        activityDao.createActivity(activity);
        int initialActivitiesSize = activityDao.getAllActivities().size();

        activity.setWorkName("activity")
                .setMeasurement("m2")
                .setPrice(20D)
                .setAmount(2D)
                .setBuilding(buildingDao.getBuildingById(2L));

        activityDao.updateActivity(activity);

        Collection<Activity> activities = activityDao.getAllActivities();
        Activity updatedActivity = activityDao.getActivityById(activity.getInstId());

        assertEquals(activity, updatedActivity);
        assertEquals(initialActivitiesSize, activities.size());
        assertEquals(activity.getBuilding().getInstId(), updatedActivity.getBuilding().getInstId());

        activityDao.deleteActivityById(activity.getInstId());
    }

    @Test
    void testDeleteById() {
        Activity activity = Activity.builder()
                .workName("Activity")
                .measurement("m")
                .price(10D)
                .amount(1D)
                .building(buildingDao.getBuildingById(1L))
                .build();

        activityDao.createActivity(activity);
        int initialActivitiesSize = activityDao.getAllActivities().size();

        activityDao.deleteActivityById(activity.getInstId());
        Collection<Activity> activities = activityDao.getAllActivities();

        assertFalse(activities.contains(activity));
        assertEquals(initialActivitiesSize - 1, activities.size());
    }

    @Test
    void testGetById() {
        Activity activity = Activity.builder()
                .workName("Activity")
                .measurement("m")
                .price(10D)
                .amount(1D)
                .building(buildingDao.getBuildingById(1L))
                .build();

        activityDao.createActivity(activity);

        Activity anotherActivity = activityDao.getActivityById(activity.getInstId());

        assertEquals(activity, anotherActivity);
    }

    @Test
    void testGetAll() {

        Collection<Activity> oldActivities = activityDao.getAllActivities();

        Activity activity = Activity.builder()
                .workName("Activity")
                .measurement("m")
                .price(10D)
                .amount(1D)
                .building(buildingDao.getBuildingById(1L))
                .build();

        activityDao.createActivity(activity);

        Collection<Activity> activitys = activityDao.getAllActivities();

        assertEquals(oldActivities.size() + 1, activitys.size());
        assertTrue(activitys.containsAll(oldActivities));
        assertTrue(activitys.contains(activity));
    }

    @Test
    void testGetAllByBuildingId() {

        Collection<Activity> activities = activityDao.getAllActivitiesByBuildingId(1L);

        assertEquals(3, activities.size());

        Object[] activitiesArr = activities.toArray();
        assertEquals(1, ((Activity) activitiesArr[0]).getInstId());
        assertEquals(2, ((Activity) activitiesArr[1]).getInstId());
        assertEquals(3, ((Activity) activitiesArr[2]).getInstId());
    }

}
