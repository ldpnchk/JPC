package com.lidapinchuk.dao;

import com.lidapinchuk.model.Material;

import java.util.Collection;

public interface MaterialDao {

    Material createMaterial(Material material);

    Material updateMaterial(Material newMaterial);

    void deleteMaterialById(Long materialId);

    Material getMaterialById(Long materialId);

    Collection<Material> getAllMaterials();

}
