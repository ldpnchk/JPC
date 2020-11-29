package com.lidapinchuk.repository;

import com.lidapinchuk.repository.impl.BuildingDaoImpl;
import com.lidapinchuk.repository.impl.ReportDaoImpl;
import com.lidapinchuk.model.Building;
import com.lidapinchuk.util.DatabaseUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class BuildingDaoTest {

    private static ReportDao reportDao;
    private static BuildingDao buildingDao;

    @BeforeAll
    public static void initDatabase() {

        DatabaseUtil.initializeDatabase();

        reportDao = ReportDaoImpl.getInstance();
        buildingDao = BuildingDaoImpl.getInstance();
    }

    @BeforeEach
    public void initDatabaseContent() {

        DatabaseUtil.fillDatabase();
    }

    @Test
    void testCreate() {
        Building building = Building.builder()
                .buildingName("Building")
                .isActive(true)
                .report(reportDao.getReportById(1L))
                .build();

        int initialBuildingsSize = buildingDao.getAllBuildings().size();

        buildingDao.createBuilding(building);
        Collection<Building> buildings = buildingDao.getAllBuildings();

        assertNotNull(building.getInstId());
        assertTrue(buildings.contains(building));
        assertEquals(initialBuildingsSize + 1, buildings.size());

        buildingDao.deleteBuildingById(building.getInstId());
    }

    @Test
    void testUpdate() {
        Building building = Building.builder()
                .buildingName("Building")
                .isActive(true)
                .report(reportDao.getReportById(1L))
                .build();
        buildingDao.createBuilding(building);
        int initialBuildingsSize = buildingDao.getAllBuildings().size();

        building.setBuildingName("building")
                .setIsActive(false)
                .setReport(reportDao.getReportById(2L));

        buildingDao.updateBuilding(building);

        Collection<Building> buildings = buildingDao.getAllBuildings();
        Building updatedBuilding = buildingDao.getBuildingById(building.getInstId());

        assertEquals(building, updatedBuilding);
        assertEquals(initialBuildingsSize, buildings.size());
        assertEquals(building.getReport().getInstId(), updatedBuilding.getReport().getInstId());

        buildingDao.deleteBuildingById(building.getInstId());
    }

    @Test
    void testDeleteById() {
        Building building = Building.builder()
                .buildingName("Building")
                .isActive(true)
                .report(reportDao.getReportById(1L))
                .build();

        buildingDao.createBuilding(building);
        int initialBuildingsSize = buildingDao.getAllBuildings().size();

        buildingDao.deleteBuildingById(building.getInstId());
        Collection<Building> buildings = buildingDao.getAllBuildings();

        assertFalse(buildings.contains(building));
        assertEquals(initialBuildingsSize - 1, buildings.size());
    }

    @Test
    void testGetById() {
        Building building = Building.builder()
                .buildingName("Building")
                .isActive(true)
                .report(reportDao.getReportById(1L))
                .build();

        buildingDao.createBuilding(building);

        Building anotherBuilding = buildingDao.getBuildingById(building.getInstId());

        assertEquals(building, anotherBuilding);

        buildingDao.deleteBuildingById(building.getInstId());
    }

    @Test
    void testGetAll() {

        Collection<Building> oldBuildings = buildingDao.getAllBuildings();

        Building building = Building.builder()
                .buildingName("Building")
                .isActive(true)
                .report(reportDao.getReportById(1L))
                .build();

        buildingDao.createBuilding(building);

        Collection<Building> buildings = buildingDao.getAllBuildings();

        assertEquals(oldBuildings.size() + 1, buildings.size());
        assertTrue(buildings.containsAll(oldBuildings));
        assertTrue(buildings.contains(building));

        buildingDao.deleteBuildingById(building.getInstId());
    }

    @Test
    void testSetNotActiveIfTotalPriceBiggerThan() {

        buildingDao.setBuildingNotActiveIfTotalPriceBiggerThan(new BigDecimal(1000));

        assertFalse(buildingDao.getBuildingById(1L).getIsActive());
        assertFalse(buildingDao.getBuildingById(2L).getIsActive());
        assertFalse(buildingDao.getBuildingById(3L).getIsActive());
        assertFalse(buildingDao.getBuildingById(4L).getIsActive());
        assertTrue(buildingDao.getBuildingById(5L).getIsActive());
        assertFalse(buildingDao.getBuildingById(6L).getIsActive());
        assertTrue(buildingDao.getBuildingById(7L).getIsActive());
    }

    @Test
    void testGetTotalActivitiesPriceById() {

        assertEquals(new BigDecimal(9150).setScale(2), buildingDao.getTotalActivitiesPriceByBuildingId(1L));
        assertEquals(new BigDecimal(1200).setScale(2), buildingDao.getTotalActivitiesPriceByBuildingId(2L));
        assertEquals(new BigDecimal(480).setScale(2), buildingDao.getTotalActivitiesPriceByBuildingId(3L));
        assertEquals(new BigDecimal(1280).setScale(2), buildingDao.getTotalActivitiesPriceByBuildingId(4L));
        assertEquals(new BigDecimal(576).setScale(2), buildingDao.getTotalActivitiesPriceByBuildingId(5L));
        assertEquals(new BigDecimal(160).setScale(2), buildingDao.getTotalActivitiesPriceByBuildingId(6L));
        assertEquals(new BigDecimal(500).setScale(2), buildingDao.getTotalActivitiesPriceByBuildingId(7L));
    }

}
