package com.lidapinchuk.repository;

import com.lidapinchuk.model.Activity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Collection;

public class JpaActivityRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Activity createActivity(Activity activity) {

        entityManager.persist(activity);
        return activity;
    }

    @Transactional
    public Activity updateActivity(Activity activity) {

        entityManager.merge(activity);
        return activity;
    }

    @Transactional
    public void deleteActivityById(Long activityId) {

        Activity activity = entityManager.find(Activity.class, activityId);
        entityManager.remove(activity);
    }

    @Transactional
    public Activity getActivityById(Long activityId) {

        return entityManager.find(Activity.class, activityId);
    }

    @Transactional
    public Collection<Activity> getAllActivities() {

        return entityManager
                    .createQuery("FROM Activity").getResultList();
    }

}
