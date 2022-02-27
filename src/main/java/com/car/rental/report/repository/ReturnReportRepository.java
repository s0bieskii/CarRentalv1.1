package com.car.rental.report.repository;

import com.car.rental.report.ReturnReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReturnReportRepository extends JpaRepository<ReturnReport, Long>, ReturnReportSearchRepository {
}
