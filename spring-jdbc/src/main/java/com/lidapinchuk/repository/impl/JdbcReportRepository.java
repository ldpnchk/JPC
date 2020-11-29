package com.lidapinchuk.repository.impl;

import com.lidapinchuk.model.Activity;
import com.lidapinchuk.model.Report;
import com.lidapinchuk.repository.JpaReportRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.Collection;

@Repository
public class JdbcReportRepository extends JpaReportRepository {

    public Collection<Report> getAllReportsByUserId(Long userId) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Report> criteriaQuery = criteriaBuilder.createQuery(Report.class);
        Root<Report> root = criteriaQuery.from(Report.class);

        criteriaQuery.where(criteriaBuilder.equal(root.get("user").get("instId"), userId));

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    public BigDecimal getTotalActivitiesPriceByReportId(Long reportId) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<BigDecimal> criteriaQuery = criteriaBuilder.createQuery(BigDecimal.class);
        Root<Activity> root = criteriaQuery.from(Activity.class);

        criteriaQuery.select(criteriaBuilder.sum(criteriaBuilder.prod(root.get("price"), root.get("amount"))))
                .where(criteriaBuilder.equal(root.get("building").get("report").get("instId"), reportId));

        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

}
