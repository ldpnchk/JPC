package com.lidapinchuk.repository;

import com.lidapinchuk.model.Material;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Collection;

public class JpaMaterialRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Material createMaterial(Material material) {

        entityManager.persist(material);
        return material;
    }

    @Transactional
    public Material updateMaterial(Material material) {

        entityManager.merge(material);
        return material;
    }

    @Transactional
    public void deleteMaterialById(Long materialId) {

        Material material = entityManager.find(Material.class, materialId);
        entityManager.remove(material);
    }

    @Transactional
    public Material getMaterialById(Long materialId) {

        return entityManager.find(Material.class, materialId);
    }

    @Transactional
    public Collection<Material> getAllMaterials() {

        return entityManager
                    .createQuery("FROM Material").getResultList();
    }

}
