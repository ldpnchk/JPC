package com.lidapinchuk.repository;

import com.lidapinchuk.config.ApplicationConfig;
import com.lidapinchuk.repository.impl.JdbcBuildingRepository;
import com.lidapinchuk.repository.impl.JdbcReportRepository;
import com.lidapinchuk.model.Building;
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
public class BuildingRepositoryTest {

    @Autowired
    private JdbcReportRepository jdbcReportRepository;
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
        Building building = Building.builder()
                .buildingName("Building")
                .isActive(true)
                .report(jdbcReportRepository.getReportById(1L))
                .build();

        int initialBuildingsSize = jdbcBuildingRepository.getAllBuildings().size();

        jdbcBuildingRepository.createBuilding(building);
        Collection<Building> buildings = jdbcBuildingRepository.getAllBuildings();

        assertNotNull(building.getInstId());
        assertTrue(buildings.contains(building));
        assertEquals(initialBuildingsSize + 1, buildings.size());

        jdbcBuildingRepository.deleteBuildingById(building.getInstId());
    }

    @Test
    void testUpdate() {
        Building building = Building.builder()
                .buildingName("Building")
                .isActive(true)
                .report(jdbcReportRepository.getReportById(1L))
                .build();
        jdbcBuildingRepository.createBuilding(building);
        int initialBuildingsSize = jdbcBuildingRepository.getAllBuildings().size();

        building.setBuildingName("building")
                .setIsActive(false)
                .setReport(jdbcReportRepository.getReportById(2L));

        jdbcBuildingRepository.updateBuilding(building);

        Collection<Building> buildings = jdbcBuildingRepository.getAllBuildings();
        Building updatedBuilding = jdbcBuildingRepository.getBuildingById(building.getInstId());

        assertEquals(building, updatedBuilding);
        assertEquals(initialBuildingsSize, buildings.size());
        assertEquals(building.getReport().getInstId(), updatedBuilding.getReport().getInstId());

        jdbcBuildingRepository.deleteBuildingById(building.getInstId());
    }

    @Test
    void testDeleteById() {
        Building building = Building.builder()
                .buildingName("Building")
                .isActive(true)
                .report(jdbcReportRepository.getReportById(1L))
                .build();

        jdbcBuildingRepository.createBuilding(building);
        int initialBuildingsSize = jdbcBuildingRepository.getAllBuildings().size();

        jdbcBuildingRepository.deleteBuildingById(building.getInstId());
        Collection<Building> buildings = jdbcBuildingRepository.getAllBuildings();

        assertFalse(buildings.contains(building));
        assertEquals(initialBuildingsSize - 1, buildings.size());
    }

    @Test
    void testGetById() {
        Building building = Building.builder()
                .buildingName("Building")
                .isActive(true)
                .report(jdbcReportRepository.getReportById(1L))
                .build();

        jdbcBuildingRepository.createBuilding(building);

        Building anotherBuilding = jdbcBuildingRepository.getBuildingById(building.getInstId());

        assertEquals(building, anotherBuilding);

        jdbcBuildingRepository.deleteBuildingById(building.getInstId());
    }

    @Test
    void testGetAll() {

        Collection<Building> oldBuildings = jdbcBuildingRepository.getAllBuildings();

        Building building = Building.builder()
                .buildingName("Building")
                .isActive(true)
                .report(jdbcReportRepository.getReportById(1L))
                .build();

        jdbcBuildingRepository.createBuilding(building);

        Collection<Building> buildings = jdbcBuildingRepository.getAllBuildings();

        assertEquals(oldBuildings.size() + 1, buildings.size());
        assertTrue(buildings.containsAll(oldBuildings));
        assertTrue(buildings.contains(building));

        jdbcBuildingRepository.deleteBuildingById(building.getInstId());
    }

    @Test
    void testSetNotActiveIfTotalPriceBiggerThan() {

        jdbcBuildingRepository.setBuildingNotActiveIfTotalPriceBiggerThan(new BigDecimal(1000));

        assertFalse(jdbcBuildingRepository.getBuildingById(1L).getIsActive());
        assertFalse(jdbcBuildingRepository.getBuildingById(2L).getIsActive());
        assertFalse(jdbcBuildingRepository.getBuildingById(3L).getIsActive());
        assertFalse(jdbcBuildingRepository.getBuildingById(4L).getIsActive());
        assertTrue(jdbcBuildingRepository.getBuildingById(5L).getIsActive());
        assertFalse(jdbcBuildingRepository.getBuildingById(6L).getIsActive());
        assertTrue(jdbcBuildingRepository.getBuildingById(7L).getIsActive());
    }

    @Test
    void testGetTotalActivitiesPriceById() {

        assertEquals(new BigDecimal(9150).setScale(2), jdbcBuildingRepository.getTotalActivitiesPriceByBuildingId(1L));
        assertEquals(new BigDecimal(1200).setScale(2), jdbcBuildingRepository.getTotalActivitiesPriceByBuildingId(2L));
        assertEquals(new BigDecimal(480).setScale(2), jdbcBuildingRepository.getTotalActivitiesPriceByBuildingId(3L));
        assertEquals(new BigDecimal(1280).setScale(2), jdbcBuildingRepository.getTotalActivitiesPriceByBuildingId(4L));
        assertEquals(new BigDecimal(576).setScale(2), jdbcBuildingRepository.getTotalActivitiesPriceByBuildingId(5L));
        assertEquals(new BigDecimal(160).setScale(2), jdbcBuildingRepository.getTotalActivitiesPriceByBuildingId(6L));
        assertEquals(new BigDecimal(500).setScale(2), jdbcBuildingRepository.getTotalActivitiesPriceByBuildingId(7L));
    }

}
