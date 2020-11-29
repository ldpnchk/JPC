package com.lidapinchuk.repository.impl;

import com.lidapinchuk.repository.MaterialDao;
import com.lidapinchuk.model.Material;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

@Slf4j
public class MaterialDaoImpl implements MaterialDao {

    private static class MaterialDaoImplHolder {
        public static final MaterialDaoImpl instance = new MaterialDaoImpl();
    }

    public static MaterialDaoImpl getInstance()  {
        return MaterialDaoImplHolder.instance;
    }

    private MaterialDaoImpl() {

    }

    @Override
    public Material createMaterial(Material material) {
        log.info("'createActivity' invoked with material: {}", material);

        Material createdMaterial = GeneralDao.getInstance().performOperation(session -> {
            session.persist(material);
            return material;
        });

        log.info("'createActivity' returned with createdMaterial: {}", createdMaterial);
        return createdMaterial;
    }

    @Override
    public Material updateMaterial(Material material) {
        log.info("'updateActivity' invoked with material: {}", material);

        Material updatedMaterial = GeneralDao.getInstance().performOperation(session -> {
            session.merge(material);
            return material;
        });

        log.info("'updateActivity' returned with updatedMaterial: {}", updatedMaterial);
        return updatedMaterial;
    }

    @Override
    public void deleteMaterialById(Long materialId) {
        log.info("'deleteActivityById' invoked with materialId: {}", materialId);

        GeneralDao.getInstance().performOperation(session -> {
            Material material = session.get(Material.class, materialId);
            session.remove(material);
        });
    }

    @Override
    public Material getMaterialById(Long materialId) {
        log.info("'getActivityById' invoked with materialId: {}", materialId);

        Material material = GeneralDao.getInstance().performOperation(session -> {
            return session
                    .createQuery("FROM Material m WHERE m.instId = :materialId", Material.class)
                    .setParameter("materialId", materialId)
                    .getSingleResult();
        });

        log.info("'getActivityById' returned with material: {}", material);
        return material;
    }

    @Override
    public Collection<Material> getAllMaterials() {
        log.info("'getAllActivities' invoked");

        Collection<Material> materials = GeneralDao.getInstance().performOperation(session -> {
            return session
                    .createQuery("FROM Material")
                    .list();
        });

        log.info("'getAllActivities' returned with materials: {}", materials);
        return materials;
    }

}
