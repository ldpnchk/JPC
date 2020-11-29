package com.lidapinchuk.repository;

import com.lidapinchuk.repository.impl.ReportDaoImpl;
import com.lidapinchuk.repository.impl.UserDaoImpl;
import com.lidapinchuk.model.Report;
import com.lidapinchuk.util.DatabaseUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class ReportDaoTest {

    private static UserDao userDao;
    private static ReportDao reportDao;

    @BeforeAll
    public static void initDatabase() {

        DatabaseUtil.initializeDatabase();

        userDao = UserDaoImpl.getInstance();
        reportDao = ReportDaoImpl.getInstance();
    }

    @BeforeEach
    public void initDatabaseContent() {

        DatabaseUtil.fillDatabase();
    }

    @Test
    void testCreate() {
        Report report = Report.builder()
                .reportName("Report")
                .price(10D)
                .orderDate(Date.valueOf("2020-11-01"))
                .user(userDao.getUserById(1L))
                .build();

        int initialReportsSize = reportDao.getAllReports().size();

        reportDao.createReport(report);
        Collection<Report> reports = reportDao.getAllReports();

        assertNotNull(report.getInstId());
        assertTrue(reports.contains(report));
        assertEquals(initialReportsSize + 1, reports.size());

        reportDao.deleteReportById(report.getInstId());
    }

    @Test
    void testUpdate() {
        Report report = Report.builder()
                .reportName("Report")
                .price(10D)
                .orderDate(Date.valueOf("2020-11-01"))
                .user(userDao.getUserById(1L))
                .build();

        reportDao.createReport(report);
        int initialReportsSize = reportDao.getAllReports().size();

        report.setReportName("report")
                .setPrice(20D)
                .setOrderDate(Date.valueOf("2020-11-02"))
                .setUser(userDao.getUserById(2L));

        reportDao.updateReport(report);

        Collection<Report> reports = reportDao.getAllReports();
        Report updatedReport = reportDao.getReportById(report.getInstId());

        assertEquals(report, updatedReport);
        assertEquals(initialReportsSize, reports.size());
        assertEquals(report.getUser().getInstId(), updatedReport.getUser().getInstId());

        reportDao.deleteReportById(report.getInstId());
    }

    @Test
    void testDeleteById() {
        Report report = Report.builder()
                .reportName("Report")
                .price(10D)
                .orderDate(Date.valueOf("2020-11-01"))
                .user(userDao.getUserById(1L))
                .build();

        reportDao.createReport(report);
        int initialReportsSize = reportDao.getAllReports().size();

        reportDao.deleteReportById(report.getInstId());
        Collection<Report> reports = reportDao.getAllReports();

        assertFalse(reports.contains(report));
        assertEquals(initialReportsSize - 1, reports.size());
    }

    @Test
    void testGetById() {
        Report report = Report.builder()
                .reportName("Report")
                .price(10D)
                .orderDate(Date.valueOf("2020-11-01"))
                .user(userDao.getUserById(1L))
                .build();

        reportDao.createReport(report);

        Report anotherReport = reportDao.getReportById(report.getInstId());

        assertEquals(report, anotherReport);

        reportDao.deleteReportById(report.getInstId());
    }

    @Test
    void testGetAll() {

        Collection<Report> oldReports = reportDao.getAllReports();

        Report report = Report.builder()
                .reportName("Report")
                .price(10D)
                .orderDate(Date.valueOf("2020-11-01"))
                .user(userDao.getUserById(1L))
                .build();

        reportDao.createReport(report);

        Collection<Report> reports = reportDao.getAllReports();

        assertEquals(oldReports.size() + 1, reports.size());
        assertTrue(reports.containsAll(oldReports));
        assertTrue(reports.contains(report));

        reportDao.deleteReportById(report.getInstId());
    }

    @Test
    void testGetAllByUserId() {

        Collection<Report> reports = reportDao.getAllReportsByUserId(1L);

        assertEquals(3, reports.size());

        Object[] reportsArr = reports.toArray();
        assertEquals(1, ((Report) reportsArr[0]).getInstId());
        assertEquals(2, ((Report) reportsArr[1]).getInstId());
        assertEquals(3, ((Report) reportsArr[2]).getInstId());
    }

    @Test
    void testGetTotalActivitiesPriceById() {

        assertEquals(new BigDecimal(9150).setScale(2), reportDao.getTotalActivitiesPriceByReportId(1L));
        assertEquals(new BigDecimal(1680).setScale(2), reportDao.getTotalActivitiesPriceByReportId(2L));
        assertEquals(new BigDecimal(1856).setScale(2), reportDao.getTotalActivitiesPriceByReportId(3L));
        assertEquals(new BigDecimal(160).setScale(2), reportDao.getTotalActivitiesPriceByReportId(4L));
        assertEquals(new BigDecimal(500).setScale(2), reportDao.getTotalActivitiesPriceByReportId(5L));
    }

}
