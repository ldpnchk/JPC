package com.lidapinchuk.repository;

import com.lidapinchuk.model.Report;

import java.math.BigDecimal;
import java.util.Collection;

public interface ReportDao {

    Report createReport(Report report);

    Report updateReport(Report newReport);

    void deleteReportById(Long reportId);

    Report getReportById(Long reportId);

    Collection<Report> getAllReports();

    Collection<Report> getAllReportsByUserId(Long userId);

    BigDecimal getTotalActivitiesPriceByReportId(Long reportId);

}
