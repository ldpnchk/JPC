package com.lidapinchuk.repository.impl;

import com.lidapinchuk.repository.BuildingDao;
import com.lidapinchuk.model.Activity;
import com.lidapinchuk.model.Building;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.BigDecimalType;
import org.hibernate.type.Type;

import java.math.BigDecimal;
import java.util.Collection;

@Slf4j
public class BuildingDaoImpl implements BuildingDao {

    private static class BuildingDaoImplHolder {
        public static final BuildingDaoImpl instance = new BuildingDaoImpl();
    }

    public static BuildingDaoImpl getInstance()  {
        return BuildingDaoImplHolder.instance;
    }

    private BuildingDaoImpl() {

    }

    @Override
    public Building createBuilding(Building building) {
        log.info("'createActivity' invoked with building: {}", building);

        Building createdBuilding = GeneralDao.getInstance().performOperation(session -> {
            session.persist(building);
            return building;
        });

        log.info("'createActivity' returned with createdBuilding: {}", createdBuilding);
        return createdBuilding;
    }

    @Override
    public Building updateBuilding(Building building) {
        log.info("'updateActivity' invoked with building: {}", building);

        Building updatedBuilding = GeneralDao.getInstance().performOperation(session -> {
            session.update(building);
            return building;
        });

        log.info("'updateActivity' returned with updatedBuilding: {}", updatedBuilding);
        return updatedBuilding;
    }

    @Override
    public void setBuildingNotActiveIfTotalPriceBiggerThan(BigDecimal totalPrice) {
        log.info("'setBuildingNotActiveIfTotalPriceBiggerThan' invoked with totalPrice: {}", totalPrice);

        GeneralDao.getInstance().performOperation(session -> {
            Collection<Building> buildings = session
                    .createNativeQuery(
                            "SELECT * FROM building " +
                            "WHERE is_active = true AND inst_id IN " +
                                "(SELECT building_id " +
                                "FROM activity " +
                                "GROUP BY building_id " +
                                "HAVING SUM(price * amount) > :totalPrice)")
                    .setParameter("totalPrice", totalPrice)
                    .addEntity(Building.class)
                    .list();

            buildings.forEach(building -> building.setIsActive(false));
        });
    }

    @Override
    public void deleteBuildingById(Long buildingId) {
        log.info("'deleteActivityById' invoked with buildingId: {}", buildingId);

        GeneralDao.getInstance().performOperation(session -> {
            Building building = session.get(Building.class, buildingId);
            session.remove(building);
        });
    }

    @Override
    public Building getBuildingById(Long buildingId) {
        log.info("'getActivityById' invoked with buildingId: {}", buildingId);

        Building building = GeneralDao.getInstance().performOperation(session -> {
            return session
                    .createQuery("FROM Building b WHERE b.instId = :buildingId", Building.class)
                    .setParameter("buildingId", buildingId)
                    .getSingleResult();
        });

        log.info("'getActivityById' returned with building: {}", building);
        return building;
    }

    @Override
    public Collection<Building> getAllBuildings() {
        log.info("'getAllActivities' invoked");

        Collection<Building> buildings = GeneralDao.getInstance().performOperation(session -> {
            return session
                    .createQuery("FROM Building")
                    .list();
        });

        log.info("'getAllActivities' returned with buildings: {}", buildings);
        return buildings;
    }

    @Override
    public BigDecimal getTotalActivitiesPriceByBuildingId(Long buildingId) {
        log.info("'getTotalActivitiesPriceByBuildingId' invoked with buildingId: {}", buildingId);

        BigDecimal totalActivitiesPrice = GeneralDao.getInstance().performOperation(session -> {

            Criteria criteria = session.createCriteria(Activity.class)
                    .setProjection(Projections.sqlProjection(
                            "sum(price * amount)", new String[] {"sum"}, ( new Type[] {new BigDecimalType()})))
                    .add(Restrictions.eq("building.instId", buildingId));

            return (BigDecimal) criteria.uniqueResult();
        });

        log.info("'getTotalActivitiesPriceByBuildingId' returned with totalActivitiesPrice: {}", totalActivitiesPrice);
        return totalActivitiesPrice;
    }

}
