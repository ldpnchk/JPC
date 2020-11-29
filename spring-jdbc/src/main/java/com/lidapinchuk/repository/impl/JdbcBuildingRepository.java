package com.lidapinchuk.repository.impl;

import com.lidapinchuk.model.Building;
import com.lidapinchuk.repository.JpaBuildingRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Collection;

@Repository
public class JdbcBuildingRepository extends JpaBuildingRepository {

    @Transactional
    public void setBuildingNotActiveIfTotalPriceBiggerThan(BigDecimal totalPrice) {
        Collection<Building> buildings = entityManager
                .createQuery(
                        "FROM Building b " +
                                "WHERE b.isActive = true AND b.instId IN " +
                                    "(SELECT a.building.instId " +
                                    "FROM Activity a " +
                                    "GROUP BY a.building " +
                                    "HAVING SUM(a.price * a.amount) > :totalPrice)")
                .setParameter("totalPrice", totalPrice)
                .getResultList();

        buildings.forEach(building -> building.setIsActive(false));
    }

    public BigDecimal getTotalActivitiesPriceByBuildingId(Long buildingId) {

        return (BigDecimal) entityManager
                .createQuery(
                        "SELECT SUM(a.price * a.amount)" +
                                "FROM Activity a " +
                                "WHERE a.building.instId IN " +
                                    "(SELECT b.instId " +
                                    "FROM Building b " +
                                    "WHERE b.instId = :buildingId)")
                .setParameter("buildingId", buildingId)
                .getSingleResult();
    }

}
