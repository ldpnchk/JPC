package com.lidapinchuk.repository.impl;

import com.lidapinchuk.repository.ActivityDao;
import com.lidapinchuk.model.Activity;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

@Slf4j
public class ActivityDaoImpl implements ActivityDao {

    private static class ActivityDaoImplHolder {
        public static final ActivityDaoImpl instance = new ActivityDaoImpl();
    }

    public static ActivityDaoImpl getInstance()  {
        return ActivityDaoImplHolder.instance;
    }

    private ActivityDaoImpl() {

    }

    @Override
    public Activity createActivity(Activity activity) {
        log.info("'createActivity' invoked with activity: {}", activity);

        Activity createdActivity = GeneralDao.getInstance().performOperation(session -> {
            session.persist(activity);
            return activity;
        });

        log.info("'createActivity' returned with createdActivity: {}", createdActivity);
        return createdActivity;
    }

    @Override
    public Activity updateActivity(Activity activity) {
        log.info("'updateActivity' invoked with activity: {}", activity);

        Activity updatedActivity = GeneralDao.getInstance().performOperation(session -> {
            session.merge(activity);
            return activity;
        });


        log.info("'updateActivity' returned with updatedActivity: {}", updatedActivity);
        return updatedActivity;
    }

    @Override
    public void deleteActivityById(Long activityId) {
        log.info("'deleteActivityById' invoked with activityId: {}", activityId);

        GeneralDao.getInstance().performOperation(session -> {
            Activity activity = session.get(Activity.class, activityId);
            session.remove(activity);
        });
    }

    @Override
    public Activity getActivityById(Long activityId) {
        log.info("'getActivityById' invoked with activityId: {}", activityId);

        Activity activity = GeneralDao.getInstance().performOperation(session -> {
            return session
                    .createQuery("FROM Activity a WHERE a.instId = :activityId", Activity.class)
                    .setParameter("activityId", activityId)
                    .getSingleResult();
        });

        log.info("'getActivityById' returned with activity: {}", activity);
        return activity;
    }

    @Override
    public Collection<Activity> getAllActivities() {
        log.info("'getAllActivities' invoked");

        Collection<Activity> activities = GeneralDao.getInstance().performOperation(session -> {
            return session
                    .createQuery("FROM Activity")
                    .list();
        });

        log.info("'getAllActivities' returned with activities: {}", activities);
        return activities;
    }

    @Override
    public Collection<Activity> getAllActivitiesByBuildingId(Long buildingId) {
        log.info("'getAllActivitiesByBuildingId' invoked with buildingId: {}", buildingId);

        Collection<Activity> activities = GeneralDao.getInstance().performOperation(session -> {
            return session
                    .createQuery("FROM Activity a WHERE a.building.id = :buildingId")
                    .setParameter("buildingId", buildingId)
                    .list();
        });

        log.info("'getAllActivitiesByBuildingId' returned with activities: {}", activities);
        return activities;
    }

}
