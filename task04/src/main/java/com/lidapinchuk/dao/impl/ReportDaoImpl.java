package com.lidapinchuk.dao.impl;

import com.lidapinchuk.dao.ReportDao;
import com.lidapinchuk.model.Activity;
import com.lidapinchuk.model.Building;
import com.lidapinchuk.model.Report;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.type.BigDecimalType;
import org.hibernate.type.Type;

import java.math.BigDecimal;
import java.util.Collection;

@Slf4j
public class ReportDaoImpl implements ReportDao {

    private static class ReportDaoImplHolder {
        public static final ReportDaoImpl instance = new ReportDaoImpl();
    }

    public static ReportDaoImpl getInstance()  {
        return ReportDaoImplHolder.instance;
    }

    private ReportDaoImpl() {

    }

    @Override
    public Report createReport(Report report) {
        log.info("'createActivity' invoked with report: {}", report);

        Report createdReport = GeneralDao.getInstance().performOperation(session -> {
            Long newId = (Long) session.save(report);
            report.setInstId(newId);
            return report;
        });

        log.info("'createActivity' returned with createdReport: {}", createdReport);
        return createdReport;
    }

    @Override
    public Report updateReport(Report newReport) {
        log.info("'updateActivity' invoked with newReport: {}", newReport);

        Report updatedReport = GeneralDao.getInstance().performOperation(session -> {
            Report report = session.get(Report.class, newReport.getInstId());
            report.setReportName(newReport.getReportName())
                    .setPrice(newReport.getPrice())
                    .setOrderDate(newReport.getOrderDate())
                    .setUser(newReport.getUser());
            session.update(report);
            return report;
        });

        log.info("'updateActivity' returned with createdReport: {}", updatedReport);
        return updatedReport;
    }

    @Override
    public void deleteReportById(Long reportId) {
        log.info("'deleteActivityById' invoked with reportId: {}", reportId);

        GeneralDao.getInstance().performOperation(session -> {
            Report report = session.get(Report.class, reportId);
            session.delete(report);
        });
    }

    @Override
    public Report getReportById(Long reportId) {
        log.info("'getActivityById' invoked with reportId: {}", reportId);

        Report report = GeneralDao.getInstance().performOperation(session -> {
            return session
                    .createQuery("FROM Report r WHERE r.instId = :reportId", Report.class)
                    .setParameter("reportId", reportId)
                    .getSingleResult();
        });

        log.info("'getActivityById' returned with report: {}", report);
        return report;
    }

    @Override
    public Collection<Report> getAllReports() {
        log.info("'getAllActivities' invoked");

        Collection<Report> reports = GeneralDao.getInstance().performOperation(session -> {
            return session
                    .createQuery("FROM Report")
                    .list();
        });

        log.info("'getAllActivities' returned with reports: {}", reports);
        return reports;
    }

    @Override
    public Collection<Report> getAllReportsByUserId(Long userId) {
        log.info("'getAllReportsByUserId' invoked with userId: {}", userId);

        Collection<Report> reports = GeneralDao.getInstance().performOperation(session -> {
            return session
                    .createQuery("FROM Report r WHERE r.user.id = :userId")
                    .setParameter("userId", userId)
                    .list();
        });

        log.info("'getAllReportsByUserId' returned with reports: {}", reports);
        return reports;
    }

    @Override
    public BigDecimal getTotalActivitiesPriceByReportId(Long reportId) {
        log.info("'getTotalActivitiesPriceByBuildingId' invoked with reportId: {}", reportId);

        BigDecimal totalActivitiesPrice = GeneralDao.getInstance().performOperation(session -> {

            DetachedCriteria subQuery = DetachedCriteria.forClass(Building.class)
                    .setProjection(Projections.property("instId"))
                    .add(Restrictions.eq("report.instId", reportId));

            Criteria query = session.createCriteria(Activity.class)
                    .setProjection(Projections.sqlProjection(
                            "sum(price * amount)", new String[] {"sum"}, ( new Type[] {new BigDecimalType()})))
                    .add(Subqueries.propertyIn("building.instId", subQuery));

            return (BigDecimal) query.uniqueResult();
        });

        log.info("'getTotalActivitiesPriceByBuildingId' returned with totalActivitiesPrice: {}", totalActivitiesPrice);
        return totalActivitiesPrice;
    }
}
