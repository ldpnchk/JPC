package com.lidapinchuk.repository;

import com.lidapinchuk.model.Building;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Collection;

public class JpaBuildingRepository {

    @PersistenceContext
    protected EntityManager entityManager;

    @Transactional
    public Building createBuilding(Building building) {

        entityManager.persist(building);
        return building;
    }

    @Transactional
    public Building updateBuilding(Building building) {

        entityManager.merge(building);
        return building;
    }

    @Transactional
    public void deleteBuildingById(Long buildingId) {

        Building building = entityManager.find(Building.class, buildingId);
        entityManager.remove(building);
    }

    @Transactional
    public Building getBuildingById(Long buildingId) {

        return entityManager.find(Building.class, buildingId);
    }

    @Transactional
    public Collection<Building> getAllBuildings() {

        return entityManager
                    .createQuery("FROM Building").getResultList();
    }

}
