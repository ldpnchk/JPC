package com.lidapinchuk.dao;

import com.lidapinchuk.dao.impl.MaterialDaoImpl;
import com.lidapinchuk.model.Material;
import com.lidapinchuk.util.DatabaseUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class MaterialDaoTest {

    private static MaterialDao materialDao;

    @BeforeAll
    public static void initDatabase() {

        DatabaseUtil.initializeDatabase();

        materialDao = MaterialDaoImpl.getInstance();
    }

    @BeforeEach
    public void initDatabaseContent() {

        DatabaseUtil.fillDatabase();
    }

    @Test
    void testCreate() {
        Material material = Material.builder()
                .material("Glass door")
                .price(200D)
                .supplier("Doors")
                .measurement("piece")
                .balance("10")
                .build();

        int initialMaterialsSize = materialDao.getAllMaterials().size();

        materialDao.createMaterial(material);
        Collection<Material> materials = materialDao.getAllMaterials();

        assertNotNull(material.getInstId());
        assertTrue(materials.contains(material));
        assertEquals(initialMaterialsSize + 1, materials.size());

        materialDao.deleteMaterialById(material.getInstId());
    }

    @Test
    void testUpdate() {
        Material material = Material.builder()
                .material("Iron door")
                .price(400D)
                .supplier("Doors")
                .measurement("piece")
                .balance("5")
                .build();

        materialDao.createMaterial(material);
        int initialMaterialsSize = materialDao.getAllMaterials().size();

        material.setMaterial("copper")
                .setPrice(350D)
                .setSupplier("Strange doors")
                .setBalance("1");

        materialDao.updateMaterial(material);

        Collection<Material> materials = materialDao.getAllMaterials();
        Material updatedMaterial = materialDao.getMaterialById(material.getInstId());

        assertEquals(material, updatedMaterial);
        assertEquals(initialMaterialsSize, materials.size());

        materialDao.deleteMaterialById(material.getInstId());
    }

    @Test
    void testDeleteById() {
        Material material = Material.builder()
                .material("Iron door")
                .price(400D)
                .supplier("Doors")
                .measurement("piece")
                .balance("5")
                .build();

        materialDao.createMaterial(material);
        int initialMaterialsSize = materialDao.getAllMaterials().size();

        materialDao.deleteMaterialById(material.getInstId());
        Collection<Material> materials = materialDao.getAllMaterials();

        assertFalse(materials.contains(material));
        assertEquals(initialMaterialsSize - 1, materials.size());
    }

    @Test
    void testGetById() {
        Material material = Material.builder()
                .material("Iron door")
                .price(400D)
                .supplier("Doors")
                .measurement("piece")
                .balance("5")
                .build();

        materialDao.createMaterial(material);

        Material anotherMaterial = materialDao.getMaterialById(material.getInstId());

        assertEquals(material, anotherMaterial);
    }

    @Test
    void testGetAll() {

        Collection<Material> oldMaterials = materialDao.getAllMaterials();

        Material material = Material.builder()
                .material("Iron door")
                .price(400D)
                .supplier("Doors")
                .measurement("piece")
                .balance("5")
                .build();

        materialDao.createMaterial(material);

        Collection<Material> materials = materialDao.getAllMaterials();

        assertEquals(oldMaterials.size() + 1, materials.size());
        assertTrue(materials.containsAll(oldMaterials));
        assertTrue(materials.contains(material));
    }

}
