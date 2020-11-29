package com.lidapinchuk.repository;

import com.lidapinchuk.model.Report;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Collection;

public class JpaReportRepository {

    @PersistenceContext
    protected EntityManager entityManager;

    @Transactional
    public Report createReport(Report report) {

        entityManager.persist(report);
        return report;
    }

    @Transactional
    public Report updateReport(Report report) {

        entityManager.merge(report);
        return report;
    }

    @Transactional
    public void deleteReportById(Long reportId) {

        Report report = entityManager.find(Report.class, reportId);
        entityManager.remove(report);
    }

    @Transactional
    public Report getReportById(Long reportId) {

        return entityManager.find(Report.class, reportId);
    }

    @Transactional
    public Collection<Report> getAllReports() {

        return entityManager
                    .createQuery("FROM Report").getResultList();
    }

}
