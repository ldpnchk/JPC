package com.lidapinchuk.repository;

import com.lidapinchuk.model.Activity;

import java.util.Collection;

public interface ActivityDao {

    Activity createActivity(Activity activity);

    Activity updateActivity(Activity newActivity);

    void deleteActivityById(Long activityId);

    Activity getActivityById(Long activityId);

    Collection<Activity> getAllActivities();

    Collection<Activity> getAllActivitiesByBuildingId(Long buildingId);

}
