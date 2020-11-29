package com.lidapinchuk.repository;

import com.lidapinchuk.config.ApplicationConfig;
import com.lidapinchuk.repository.impl.JdbcMaterialRepository;
import com.lidapinchuk.model.Material;
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
public class MaterialDaoTest {

    @Autowired
    private JdbcMaterialRepository jdbcMaterialRepository;

    @BeforeAll
    public static void initDatabase(@Autowired DataSource dataSource) {

        DatabaseUtil.initializeDatabase(dataSource);
    }

    @BeforeEach
    public void initDatabaseContent(@Autowired DataSource dataSource) {

        DatabaseUtil.fillDatabase(dataSource);
    }

    @Test
    public void testCreate() {

        Material material = Material.builder()
                .material("Glass door")
                .price(new BigDecimal(200).setScale(2))
                .supplier("Doors")
                .measurement("piece")
                .balance("10")
                .build();

        int initialMaterialsSize = jdbcMaterialRepository.getAllMaterials().size();

        jdbcMaterialRepository.createMaterial(material);
        Collection<Material> materials = jdbcMaterialRepository.getAllMaterials();

        assertNotNull(material.getInstId());
        assertTrue(materials.contains(material));
        assertEquals(initialMaterialsSize + 1, materials.size());

        jdbcMaterialRepository.deleteMaterialById(material.getInstId());
    }

    @Test
    void testUpdate() {
        Material material = Material.builder()
                .material("Iron door")
                .price(new BigDecimal(400).setScale(2))
                .supplier("Doors")
                .measurement("piece")
                .balance("5")
                .build();

        jdbcMaterialRepository.createMaterial(material);
        int initialMaterialsSize = jdbcMaterialRepository.getAllMaterials().size();

        material.setMaterial("copper")
                .setPrice(new BigDecimal(350).setScale(2))
                .setSupplier("Strange doors")
                .setBalance("1");

        jdbcMaterialRepository.updateMaterial(material);

        Collection<Material> materials = jdbcMaterialRepository.getAllMaterials();
        Material updatedMaterial = jdbcMaterialRepository.getMaterialById(material.getInstId());

        assertEquals(material, updatedMaterial);
        assertEquals(initialMaterialsSize, materials.size());

        jdbcMaterialRepository.deleteMaterialById(material.getInstId());
    }

    @Test
    void testDeleteById() {
        Material material = Material.builder()
                .material("Iron door")
                .price(new BigDecimal(400).setScale(2))
                .supplier("Doors")
                .measurement("piece")
                .balance("5")
                .build();

        jdbcMaterialRepository.createMaterial(material);
        int initialMaterialsSize = jdbcMaterialRepository.getAllMaterials().size();

        jdbcMaterialRepository.deleteMaterialById(material.getInstId());
        Collection<Material> materials = jdbcMaterialRepository.getAllMaterials();

        assertFalse(materials.contains(material));
        assertEquals(initialMaterialsSize - 1, materials.size());
    }

    @Test
    void testGetById() {
        Material material = Material.builder()
                .material("Iron door")
                .price(new BigDecimal(400).setScale(2))
                .supplier("Doors")
                .measurement("piece")
                .balance("5")
                .build();

        jdbcMaterialRepository.createMaterial(material);

        Material anotherMaterial = jdbcMaterialRepository.getMaterialById(material.getInstId());

        assertEquals(material, anotherMaterial);

        jdbcMaterialRepository.deleteMaterialById(material.getInstId());
    }

    @Test
    void testGetAll() {

        Collection<Material> oldMaterials = jdbcMaterialRepository.getAllMaterials();

        Material material = Material.builder()
                .material("Iron door")
                .price(new BigDecimal(400).setScale(2))
                .supplier("Doors")
                .measurement("piece")
                .balance("5")
                .build();

        jdbcMaterialRepository.createMaterial(material);

        Collection<Material> materials = jdbcMaterialRepository.getAllMaterials();

        assertEquals(oldMaterials.size() + 1, materials.size());
        assertTrue(materials.containsAll(oldMaterials));
        assertTrue(materials.contains(material));

        jdbcMaterialRepository.deleteMaterialById(material.getInstId());
    }

}
