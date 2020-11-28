package com.lidapinchuk.dao;

import com.lidapinchuk.model.Building;

import java.math.BigDecimal;
import java.util.Collection;

public interface BuildingDao {

    Building createBuilding(Building building);

    Building updateBuilding(Building newBuilding);

    void setBuildingNotActiveIfTotalPriceBiggerThan(BigDecimal totalPrice);

    void deleteBuildingById(Long buildingId);

    Building getBuildingById(Long buildingId);

    Collection<Building> getAllBuildings();

    BigDecimal getTotalActivitiesPriceByBuildingId(Long buildingId);

}
