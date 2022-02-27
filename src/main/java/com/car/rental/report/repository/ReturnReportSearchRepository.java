package com.car.rental.report.repository;

import com.car.rental.report.ReturnReport;
import com.car.rental.report.dto.ReturnReportSearchDto;
import java.util.List;

public interface ReturnReportSearchRepository {

    List<ReturnReport> find(ReturnReportSearchDto reportSearchDto);
}
