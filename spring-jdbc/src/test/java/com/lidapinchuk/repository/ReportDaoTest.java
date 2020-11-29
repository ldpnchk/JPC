package com.lidapinchuk.repository;

import com.lidapinchuk.config.ApplicationConfig;
import com.lidapinchuk.repository.impl.JdbcReportRepository;
import com.lidapinchuk.repository.impl.JdbcUserRepository;
import com.lidapinchuk.model.Report;
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
import java.sql.Date;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { ApplicationConfig.class })
public class ReportDaoTest {

    @Autowired
    private JdbcUserRepository jdbcUserRepository;
    @Autowired
    private JdbcReportRepository jdbcReportRepository;

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
        Report report = Report.builder()
                .reportName("Report")
                .price(new BigDecimal(10).setScale(2))
                .orderDate(Date.valueOf("2020-11-01"))
                .user(jdbcUserRepository.getUserById(1L))
                .build();

        int initialReportsSize = jdbcReportRepository.getAllReports().size();

        jdbcReportRepository.createReport(report);
        Collection<Report> reports = jdbcReportRepository.getAllReports();

        assertNotNull(report.getInstId());
        assertTrue(reports.contains(report));
        assertEquals(initialReportsSize + 1, reports.size());

        jdbcReportRepository.deleteReportById(report.getInstId());
    }

    @Test
    void testUpdate() {
        Report report = Report.builder()
                .reportName("Report")
                .price(new BigDecimal(10).setScale(2))
                .orderDate(Date.valueOf("2020-11-01"))
                .user(jdbcUserRepository.getUserById(1L))
                .build();

        jdbcReportRepository.createReport(report);
        int initialReportsSize = jdbcReportRepository.getAllReports().size();

        report.setReportName("report")
                .setPrice(new BigDecimal(20).setScale(2))
                .setOrderDate(Date.valueOf("2020-11-02"))
                .setUser(jdbcUserRepository.getUserById(2L));

        jdbcReportRepository.updateReport(report);

        Collection<Report> reports = jdbcReportRepository.getAllReports();
        Report updatedReport = jdbcReportRepository.getReportById(report.getInstId());

        assertEquals(report, updatedReport);
        assertEquals(initialReportsSize, reports.size());
        assertEquals(report.getUser().getInstId(), updatedReport.getUser().getInstId());

        jdbcReportRepository.deleteReportById(report.getInstId());
    }

    @Test
    void testDeleteById() {
        Report report = Report.builder()
                .reportName("Report")
                .price(new BigDecimal(10).setScale(2))
                .orderDate(Date.valueOf("2020-11-01"))
                .user(jdbcUserRepository.getUserById(1L))
                .build();

        jdbcReportRepository.createReport(report);
        int initialReportsSize = jdbcReportRepository.getAllReports().size();

        jdbcReportRepository.deleteReportById(report.getInstId());
        Collection<Report> reports = jdbcReportRepository.getAllReports();

        assertFalse(reports.contains(report));
        assertEquals(initialReportsSize - 1, reports.size());
    }

    @Test
    void testGetById() {
        Report report = Report.builder()
                .reportName("Report")
                .price(new BigDecimal(10).setScale(2))
                .orderDate(Date.valueOf("2020-11-01"))
                .user(jdbcUserRepository.getUserById(1L))
                .build();

        jdbcReportRepository.createReport(report);

        Report anotherReport = jdbcReportRepository.getReportById(report.getInstId());

        assertEquals(report, anotherReport);

        jdbcReportRepository.deleteReportById(report.getInstId());
    }

    @Test
    void testGetAll() {

        Collection<Report> oldReports = jdbcReportRepository.getAllReports();

        Report report = Report.builder()
                .reportName("Report")
                .price(new BigDecimal(10).setScale(2))
                .orderDate(Date.valueOf("2020-11-01"))
                .user(jdbcUserRepository.getUserById(1L))
                .build();

        jdbcReportRepository.createReport(report);

        Collection<Report> reports = jdbcReportRepository.getAllReports();

        assertEquals(oldReports.size() + 1, reports.size());
        assertTrue(reports.containsAll(oldReports));
        assertTrue(reports.contains(report));

        jdbcReportRepository.deleteReportById(report.getInstId());
    }

    @Test
    void testGetAllByUserId() {

        Collection<Report> reports = jdbcReportRepository.getAllReportsByUserId(1L);

        assertEquals(3, reports.size());

        Object[] reportsArr = reports.toArray();
        assertEquals(1, ((Report) reportsArr[0]).getInstId());
        assertEquals(2, ((Report) reportsArr[1]).getInstId());
        assertEquals(3, ((Report) reportsArr[2]).getInstId());
    }

    @Test
    void testGetTotalActivitiesPriceById() {

        assertEquals(new BigDecimal(9150).setScale(2), jdbcReportRepository.getTotalActivitiesPriceByReportId(1L));
        assertEquals(new BigDecimal(1680).setScale(2), jdbcReportRepository.getTotalActivitiesPriceByReportId(2L));
        assertEquals(new BigDecimal(1856).setScale(2), jdbcReportRepository.getTotalActivitiesPriceByReportId(3L));
        assertEquals(new BigDecimal(160).setScale(2), jdbcReportRepository.getTotalActivitiesPriceByReportId(4L));
        assertEquals(new BigDecimal(500).setScale(2), jdbcReportRepository.getTotalActivitiesPriceByReportId(5L));
    }

}
